package com.zjy.study.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.viewpager.SViewPager;
import com.zjy.study.R;
import com.zjy.study.fragment.FloatFragment;
import com.zjy.study.fragment.RefreshFloatFragment;
import com.zjy.study.fragment.StickyFragment;
import com.zjy.study.fragment.SwitchFragment;
import com.zjy.study.fragment.TabFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends FragmentActivity {


    @BindView(R.id.cus_viewpager)
    SViewPager cusViewpager;
    @BindView(R.id.cus_indicator)
    FixedIndicatorView cusIndicator;

    private IndicatorViewPager indicatorViewPager;

    private Fragment fragment0,fragment1,fragment2,fragment3,fragment4;
    private int mTabIndex = 0, mSelectedTabIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        cusViewpager.setCanScroll(false);
        cusViewpager.setOffscreenPageLimit(5);
        indicatorViewPager = new IndicatorViewPager(cusIndicator, cusViewpager);
        indicatorViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
    }

    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
        private String[] tabNames = new String[]{"主页1", "主页2", "主页3", "主页4","主页5"};

        private LayoutInflater inflater;

        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            inflater = LayoutInflater.from(HomeActivity.this);
        }

        @Override
        public int getCount() {
            return tabNames.length;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_indicator_tab, container, false);
                convertView.setId(position);
            }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cusViewpager.setCurrentItem(view.getId(), false);
                    indicatorViewPager.setCurrentItem(view.getId(), false);
                    mSelectedTabIndex = view.getId();
                }
            });
            ImageView mImgView = (ImageView) convertView.findViewById(R.id.tabview_icon);
            int resId = -1;
            switch (position) {
                case 0:
                    resId = R.drawable.pressed_tab_a;
                    break;
                case 1:
                    resId = R.drawable.preesed_tab_b;
                    break;
                case 2:
                    resId = R.drawable.preesed_tab_c;
                    break;
                case 3:
                    resId = R.drawable.pressed_tab_d;
                    break;
                case 4:
                    resId = R.drawable.pressed_tab_d;
                    break;
            }
            if (resId != -1) {
                mImgView.setBackgroundResource(resId);
            }
            ((TextView) convertView.findViewById(R.id.tabtxtview1)).setText(tabNames[position]);
            return convertView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            switch (position) {
                case 0:
                    return fragment0 == null ? fragment0 = TabFragment.newInstance() : fragment0;
                case 1:
                    return fragment1 == null ? fragment1 = FloatFragment.newInstance() : fragment1;
                case 2:
                    return fragment2 == null ? fragment2 = StickyFragment.newInstance() : fragment2;
                case 3:
                    return fragment3 == null ? fragment3 = RefreshFloatFragment.newInstance() : fragment3;
                case 4:
                    return fragment4 == null ? fragment4 = SwitchFragment.newInstance() : fragment4;
                default:
                    return null;
            }
        }
    }
}
