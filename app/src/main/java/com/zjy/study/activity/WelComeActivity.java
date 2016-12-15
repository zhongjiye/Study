package com.zjy.study.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.zjy.study.R;
import com.zjy.study.adapter.ImageViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelComeActivity extends Activity implements ViewPager.OnPageChangeListener {


    @BindView(R.id.vp_guide)
    ViewPager vpGuide;
    @BindView(R.id.ll_point)
    LinearLayout llPoint;
    @BindView(R.id.ll_skip)
    LinearLayout llSkip;

    private int[] mResIds = {R.mipmap.yd_1, R.mipmap.yd_2, R.mipmap.yd_3, R.mipmap.yd_4};
    private int mEmptyState = R.mipmap.point_empty, mSelectState = R.mipmap.point_red;
    private List<ImageView> mPoints = new ArrayList<>(), mPics = new ArrayList<>();
    private int index;
    private boolean misScrolled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        initGuide();
    }

    private void initGuide() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        ImageView guideImg = null;
        ImageView pointImg = null;
        for (int i = 0; i < mResIds.length; i++) {
            guideImg = new ImageView(this);
            guideImg.setScaleType(ImageView.ScaleType.FIT_XY);
            guideImg.setImageResource(mResIds[i]);
            mPics.add(guideImg);

            pointImg = new ImageView(this);
            pointImg.setPadding(8, 0, 8, 0);
            pointImg.setImageResource(mEmptyState);
            pointImg.setLayoutParams(params);
            pointImg.setTag(i);
            mPoints.add(pointImg);
            llPoint.addView(pointImg);
        }
        vpGuide.setAdapter(new ImageViewAdapter(mPics));
        vpGuide.addOnPageChangeListener(this);
        index = 0;
        mPoints.get(index).setImageResource(mSelectState);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position < 0 || position > mResIds.length - 1
                || index == position) {
            return;
        }
        for (int i = 0; i < mResIds.length; i++) {
            mPoints.get(index).setImageResource(mEmptyState);
        }
        mPoints.get(position).setImageResource(mSelectState);
        index = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                misScrolled = false;
                break;
            case ViewPager.SCROLL_STATE_SETTLING:
                misScrolled = true;
                break;
            case ViewPager.SCROLL_STATE_IDLE:
                if (vpGuide.getCurrentItem() == vpGuide.getAdapter().getCount() - 1 && !misScrolled) {
                    startActivity(new Intent(WelComeActivity.this, HomeActivity.class));
                    finish();
                }
                misScrolled = true;
                break;
        }
    }

    @OnClick(R.id.ll_skip)
    public void onClick() {
        startActivity(new Intent(WelComeActivity.this, HomeActivity.class));
        finish();
    }

}
