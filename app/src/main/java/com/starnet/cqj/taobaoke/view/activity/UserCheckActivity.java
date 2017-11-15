package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.view.widget.SignCalendar;

import butterknife.BindView;

/**
 * Created by mini on 17/11/15.
 */

public class UserCheckActivity extends BaseActivity {


    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.sign_calendar)
    SignCalendar mSignCalendar;

    @Override
    protected int getContentView() {
        return R.layout.activity_user_check;
    }

    @Override
    protected void init() {
        mSignCalendar.addMark("2017-11-15");

    }

    public static void start(Context context) {
        Intent starter = new Intent(context, UserCheckActivity.class);
        context.startActivity(starter);
    }

}
