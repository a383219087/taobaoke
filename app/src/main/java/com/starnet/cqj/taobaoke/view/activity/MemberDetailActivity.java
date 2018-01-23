package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.Member;
import com.starnet.cqj.taobaoke.model.MemberDetailItem;
import com.starnet.cqj.taobaoke.model.ResultWrapper;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.view.BaseApplication;
import com.starnet.cqj.taobaoke.view.adapter.LinearLayoutManagerWrapper;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerBaseAdapter;
import com.starnet.cqj.taobaoke.view.adapter.viewholder.MemberDetailItemHolder;
import com.starnet.cqj.taobaoke.view.widget.DateTimePopupWindow;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/01/14.
 */

public class MemberDetailActivity extends BaseActivity {
    public static final String KEY_UID = "uid";
    @BindView(R.id.iv_avatar)
    ImageView mIvAvatar;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_member_id)
    TextView mTvMemberId;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_score_total)
    TextView mTvScoreTotal;
    @BindView(R.id.tv_start_date)
    TextView mTvStartDate;
    @BindView(R.id.tv_end_date)
    TextView mTvEndDate;
    @BindView(R.id.rv_get_detail)
    RecyclerView mRvGetDetail;
    @BindView(R.id.btn_his_member)
    TextView mBtnHisMember;


    private Calendar mCalendar;
    private SimpleDateFormat mDateFormat;
    private DateTimePopupWindow mPopupWindow;
    private TextView mCurrentChooseView;
    private String mUid;
    private RecyclerBaseAdapter<MemberDetailItem, MemberDetailItemHolder> mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_member_detail;
    }

    @Override
    protected void init() {
        setTitleName(R.string.member_detail_title);
        mUid = getIntent().getStringExtra(KEY_UID);
        mCalendar = Calendar.getInstance();
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        mTvStartDate.setText(mDateFormat.format(new Date()));
        mTvEndDate.setText(mDateFormat.format(new Date()));
        mPopupWindow = new DateTimePopupWindow(this);
        mRvGetDetail.setLayoutManager(new LinearLayoutManagerWrapper(this));
        mAdapter = new RecyclerBaseAdapter<>(R.layout.item_member_detail, MemberDetailItemHolder.class);
        mRvGetDetail.setAdapter(mAdapter);
        getData();
    }

    private void getDetail() {
        RemoteDataSourceBase.INSTANCE.getMemberService()
                .record(((BaseApplication) getApplication()).getToken(), mUid)
                .compose(this.<JsonCommon<ResultWrapper<MemberDetailItem>>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<JsonCommon<ResultWrapper<MemberDetailItem>>>() {
                    @Override
                    public void accept(JsonCommon<ResultWrapper<MemberDetailItem>> resultWrapperJsonCommon) throws Exception {
                        if ("200".equals(resultWrapperJsonCommon.getCode())) {
                            mAdapter.setAll(resultWrapperJsonCommon.getData().getList());
                        } else {
                            toast(resultWrapperJsonCommon.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    private void getData() {
        RemoteDataSourceBase.INSTANCE.getMemberService()
                .detail(((BaseApplication) getApplication()).getToken(), mUid)
                .compose(this.<JsonCommon<Member>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<JsonCommon<Member>>() {
                    @Override
                    public void accept(JsonCommon<Member> memberJsonCommon) throws Exception {
                        if ("200".equals(memberJsonCommon.getCode())) {
                            Member member = memberJsonCommon.getData();
                            Glide.with(MemberDetailActivity.this)
                                    .load(member.getAvatar())
                                    .into(mIvAvatar);
                            mTvName.setText(member.getNickName());
                            mTvMemberId.setText(getResources().getString(R.string.member_id_text, member.getMobile()));
                            mTvTime.setText(getResources().getString(R.string.member_time_text, member.getTime()));
                            mTvScoreTotal.setText(getString(R.string.score_total_text, member.getCredit()));
                            mBtnHisMember.setText(getString(R.string.members_sub_btn,member.getCount()));
                        } else {
                            toast(memberJsonCommon.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        toast(R.string.net_error);
                    }
                });
    }

    @OnClick({R.id.btn_his_member, R.id.tv_start_date, R.id.tv_end_date})
    public void onViewClicked(View view) {
        String endDate = mTvEndDate.getText().toString();
        String startDate = mTvStartDate.getText().toString();
        switch (view.getId()) {
            case R.id.btn_his_member:
                MemberListActivity.start(this, mUid, mTvName.getText().toString());
                break;
            case R.id.tv_start_date:
                DateTimePopupWindow dateTimePopupWindow1 = showDatePicker(mTvStartDate, startDate);
                if (dateTimePopupWindow1 != null) {
                    try {
                        Date maxDate = mDateFormat.parse(endDate);
                        dateTimePopupWindow1.setMaxDate(maxDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.tv_end_date:
                DateTimePopupWindow dateTimePopupWindow = showDatePicker(mTvEndDate, endDate);
                if (dateTimePopupWindow != null) {
                    try {
                        Date minDate = mDateFormat.parse(startDate);
                        dateTimePopupWindow.setMinDate(minDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private DateTimePopupWindow showDatePicker(final TextView view, String startDate) {
        DateTimePopupWindow mPopupWindow = new DateTimePopupWindow(this);
        try {
            Date date = mDateFormat.parse(startDate);
            mCalendar.setTime(date);
            mPopupWindow.setDefaultDate(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mPopupWindow.dateObservable()
                .compose(this.<Date>bindToLifecycle())
                .subscribe(new Consumer<Date>() {
                    @Override
                    public void accept(Date date) throws Exception {
                        view.setText(mDateFormat.format(date));
                        if (!TextUtils.isEmpty(mTvStartDate.getText().toString())
                                && !TextUtils.isEmpty(mTvEndDate.getText().toString())) {
                            getDetail();
                        }
                    }
                });
        mPopupWindow.showAsDropDown(view);
        return mPopupWindow;
    }

    public static void start(Context context, String uid) {
        Intent starter = new Intent(context, MemberDetailActivity.class);
        starter.putExtra(KEY_UID, uid);
        context.startActivity(starter);
    }
}
