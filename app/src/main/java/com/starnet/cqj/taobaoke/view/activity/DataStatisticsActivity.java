package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.view.fragment.DayMonthStatisticsFragment;
import com.starnet.cqj.taobaoke.view.fragment.QuantumStatisticsFragment;

import butterknife.BindView;

/**
 * 数据统计
 * Created by johnChen on 2018/01/10.
 */

public class DataStatisticsActivity extends BaseActivity {
    public static final int TAG_DAY_STATISTICS = 1;
    public static final int TAG_MONTH_STATISTICS = 2;
    public static final int TAG_TIME_STATISTICS = 3;


    @BindView(R.id.tabs)
    TabLayout mTabs;
    private DayMonthStatisticsFragment mDayFragment;
    private DayMonthStatisticsFragment mMonthFragment;
    private QuantumStatisticsFragment mQuantumStatisticsFragment;

    @Override
    protected int getContentView() {
        return R.layout.activity_data_statistics;
    }

    @Override
    protected void init() {
        setTitleName(R.string.data_statistics_title);
        TabLayout.Tab tab = mTabs.newTab();
        tab.setText("按日统计");
        tab.setTag(TAG_DAY_STATISTICS);
        mTabs.addTab(tab);
        TabLayout.Tab monthTab = mTabs.newTab();
        monthTab.setText("按月统计");
        monthTab.setTag(TAG_MONTH_STATISTICS);
        mTabs.addTab(monthTab);
        TabLayout.Tab timeTab = mTabs.newTab();
        timeTab.setText("时间段统计");
        timeTab.setTag(TAG_TIME_STATISTICS);
        mTabs.addTab(timeTab);
        mDayFragment = DayMonthStatisticsFragment.newInstance(TAG_DAY_STATISTICS);
        mMonthFragment = DayMonthStatisticsFragment.newInstance(TAG_MONTH_STATISTICS);
        mQuantumStatisticsFragment = QuantumStatisticsFragment.newInstance();
        getFragmentManager().beginTransaction()
                .replace(R.id.statistics_fragment_container, mDayFragment)
                .commit();
    }

    private TabLayout.OnTabSelectedListener mTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            int tag = (int) tab.getTag();
            switch (tag) {
                case TAG_DAY_STATISTICS:
                    getFragmentManager().beginTransaction()
                            .replace(R.id.statistics_fragment_container, mDayFragment)
                            .commit();
                    break;
                case TAG_MONTH_STATISTICS:
                    getFragmentManager().beginTransaction()
                            .replace(R.id.statistics_fragment_container, mMonthFragment)
                            .commit();
                    break;
                case TAG_TIME_STATISTICS:
                    getFragmentManager().beginTransaction()
                            .replace(R.id.statistics_fragment_container, mQuantumStatisticsFragment)
                            .commit();
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    @Override
    protected void initEvent() {
        super.initEvent();
        mTabs.addOnTabSelectedListener(mTabSelectedListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTabs.removeOnTabSelectedListener(mTabSelectedListener);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, DataStatisticsActivity.class);
        context.startActivity(starter);
    }
}
