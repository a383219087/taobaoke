package com.starnet.cqj.taobaoke.view.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
    private Fragment mCurrentFragment;

    @Override
    protected void init() {
        mHomePageFragment = HomePageFragment.newInstance();
        mActionFragment = ActionFragment.newInstance();
        mHotFragment = HotFragment.newInstance();
        mMineFragment = MineFragment.newInstance();
        startFragmentAdd(mHomePageFragment);
    }

    @Override
    protected void initEvent() {
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.rb_home) {
                    startFragmentAdd(mHomePageFragment);
                } else if (checkedId == R.id.rb_action) {
                    startFragmentAdd(mActionFragment);
                } else if (checkedId == R.id.rb_hot) {
                    startFragmentAdd(mHotFragment);
                } else if (checkedId == R.id.rb_mine) {
                    startFragmentAdd(mMineFragment);
                }
            }
        });
    }

    // fragment的切换
    private void startFragmentAdd(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        if (mCurrentFragment == null) {
            fragmentTransaction.add(R.id.homepage_fragment_container, fragment).commit();
            mCurrentFragment = fragment;
        }
        if (mCurrentFragment != fragment) {
            // 先判断是否被add过
            if (!fragment.isAdded()) {
                // 隐藏当前的fragment，add下一个到Activity中
                fragmentTransaction.hide(mCurrentFragment)
                        .add(R.id.homepage_fragment_container, fragment).commit();
            } else {
                // 隐藏当前的fragment，显示下一个
                fragmentTransaction.hide(mCurrentFragment).show(fragment)
                        .commit();
            }
            mCurrentFragment = fragment;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }


    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    public static void startTop(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        starter.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(starter);
    }
}
