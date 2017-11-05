package com.starnet.cqj.taobaoke.view.activity;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RadioGroup;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.view.fragment.ActionFragment;
import com.starnet.cqj.taobaoke.view.fragment.HomePageFragment;
import com.starnet.cqj.taobaoke.view.fragment.HotFragment;
import com.starnet.cqj.taobaoke.view.fragment.MineFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.rg)
    RadioGroup mRg;
    private HomePageFragment mHomePageFragment;
    private ActionFragment mActionFragment;
    private HotFragment mHotFragment;
    private MineFragment mMineFragment;

    @Override
    protected void init() {
        mHomePageFragment = HomePageFragment.newInstance();
        mActionFragment = ActionFragment.newInstance();
        mHotFragment = HotFragment.newInstance();
        mMineFragment = MineFragment.newInstance();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.homepage_fragment_container, mHomePageFragment)
                .commit();
    }

    @Override
    protected void initEvent() {
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                FragmentManager fragmentManager = getFragmentManager();
                if (checkedId == R.id.rb_home) {
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.homepage_fragment_container, mHomePageFragment)
                            .commit();
                } else if (checkedId == R.id.rb_action) {
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.homepage_fragment_container, mActionFragment)
                            .commit();
                } else if (checkedId == R.id.rb_hot) {
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.homepage_fragment_container, mHotFragment)
                            .commit();
                } else if (checkedId == R.id.rb_mine) {
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.homepage_fragment_container, mMineFragment)
                            .commit();
                }
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }


    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }
}
