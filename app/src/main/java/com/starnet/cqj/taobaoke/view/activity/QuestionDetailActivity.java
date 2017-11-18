package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.HelpDetail;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.remote.Constant;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class QuestionDetailActivity extends BaseActivity {

    public static final String KEY_ID = "id";
    @BindView(R.id.title_rightimage)
    ImageButton mTitleRightimage;
    @BindView(R.id.tv_question_title)
    TextView mTvQuestionTitle;
    @BindView(R.id.iv_question_icon)
    ImageView mIvQuestionIcon;
    @BindView(R.id.tv_question_content)
    TextView mTvQuestionContent;

    @Override
    protected int getContentView() {
        return R.layout.activity_question_detail;
    }

    @Override
    protected void init() {
        setTitleName(R.string.question_detail_title);
        getData();
    }

    @OnClick(R.id.title_rightimage)
    public void onViewClicked() {
    }

    private void getData() {
        String id = getIntent().getStringExtra(KEY_ID);
        if (TextUtils.isEmpty(id)) {
            toast("数据错误，请退出重试");
            return;
        }
        RemoteDataSourceBase.INSTANCE.getCommonService()
                .getHelpContent(id)
                .compose(this.<JsonCommon<HelpDetail>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonCommon<HelpDetail>>() {
                    @Override
                    public void accept(JsonCommon<HelpDetail> jsonCommonJsonCommon) throws Exception {
                        if ("200".equals(jsonCommonJsonCommon.getCode())) {
                            HelpDetail helpDetail = jsonCommonJsonCommon.getData();
                            mTvQuestionTitle.setText(helpDetail.getTitle());
                            mTvQuestionContent.setMovementMethod(ScrollingMovementMethod.getInstance());
                            mTvQuestionContent.setText(Html.fromHtml(helpDetail.getContent(), new Html.ImageGetter() {
                                @Override
                                public Drawable getDrawable(String s) {
                                    String url = Constant.IMAGE_URL + s;
                                    Drawable load = null;
//                                    try {
//                                        load = Glide.with(QuestionDetailActivity.this)
//                                                .asDrawable()
//                                                .load(url)
//                                                .into(500, 300)
//                                                .get();
                                    load = getResources().getDrawable(R.drawable.default_avatar);
                                    load.setBounds(0, 0, load.getIntrinsicWidth(), load.getIntrinsicHeight());

//                                    } catch (InterruptedException e) {
//                                        e.printStackTrace();
//                                    } catch (ExecutionException e) {
//                                        e.printStackTrace();
//                                    }
                                    return load;
                                }
                            }, null));
                        } else {
                            toast(jsonCommonJsonCommon.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });

    }


    public static void start(Context context, String id) {
        Intent starter = new Intent(context, QuestionDetailActivity.class);
        starter.putExtra(KEY_ID, id);
        context.startActivity(starter);
    }
}
