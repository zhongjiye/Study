package com.zjy.study.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.zjy.study.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SwitchFragment extends Fragment {

    private static final int redMain = Color.parseColor("#E53D3A");
    private static final int red = Color.parseColor("#CC0000");
    private static final int black = Color.parseColor("#363636");
    private String[] mTitles = {"标题1", "标题2"};

    @BindView(R.id.cus_indicator)
    FixedIndicatorView cusIndicator;
    @BindView(R.id.cus_viewpager)
    ViewPager cusViewpager;

    private IndicatorViewPager mIndicatorViewPager = null;
    private Fragment fragment1, fragment2;

    public SwitchFragment() {

    }

    public static SwitchFragment newInstance() {
        SwitchFragment fragment = new SwitchFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_switch, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        cusViewpager.setOffscreenPageLimit(2);
        cusIndicator.setScrollBar(new ColorBar(getActivity().getApplicationContext(), redMain, 4));
        cusIndicator.setOnTransitionListener(new OnTransitionTextListener().setColor(red, black).setSize(16, 16));

        mIndicatorViewPager = new IndicatorViewPager(cusIndicator, cusViewpager);
        mIndicatorViewPager.setPageMargin(0);
        mIndicatorViewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
        mIndicatorViewPager.setOnIndicatorPageChangeListener(new IndicatorViewPager.OnIndicatorPageChangeListener() {
            @Override
            public void onIndicatorPageChange(int preItem, int currentItem) {

            }
        });
    }

    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
        private LayoutInflater inflater;

        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            inflater = LayoutInflater.from(getActivity().getApplicationContext());
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_title, container, false);
            }
            TextView textView = (TextView) convertView;
            textView.setText(mTitles[position]);
            textView.setTextSize(14);
            return convertView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            if (position == 0) {
                return fragment1 == null ? fragment1 = ListFragment.newInstance() : fragment1;
            } else {
                return fragment2 == null ? fragment2 = ListRefreshFragment.newInstance() : fragment2;
            }
        }
    }
}
