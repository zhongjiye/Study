package com.zjy.study.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.nineoldandroids.view.ViewHelper;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.zjy.study.R;
import com.zjy.study.banner.NetworkImageHolderView;
import com.zjy.study.cusview.scrolltab.ScrollTabHolder;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TabFragment extends Fragment implements ScrollTabHolder, ViewPager.OnPageChangeListener {
    private static final int redMain = Color.parseColor("#E53D3A");
    private static final int red = Color.parseColor("#CC0000");
    private static final int black = Color.parseColor("#363636");
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.indicator)
    FixedIndicatorView indicator;
    @BindView(R.id.header)
    LinearLayout header;
    @BindView(R.id.hed)
    TextView hed;
    @BindView(R.id.convenientBanner)
    ConvenientBanner convenientBanner;

    private int mMinHeaderHeight = 0;
    private float result = 0f;
    private int mHeaderHeight = 0;
    private int mMinHeaderTranslation = 0;
    private int scrollY;
    private int headerScrollSize;

    private SparseArrayCompat<ScrollTabHolder> mScrollTabHolders;

    private PagerAdapter pagerAdapter;
    private ScrollTabHolder currentHolder = null;
    private IndicatorViewPager mIndicatorViewPager = null;

    private TabListFragment fragment1;
    private TabListFragment fragment2;


    public TabFragment() {
    }

    public static TabFragment newInstance() {
        TabFragment fragment = new TabFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        initBanner();
        initHeader();
    }

    /****************************************************/


    private void initHeader() {
        mMinHeaderHeight = getResources().getDimensionPixelSize(R.dimen.scroll_height);
        result = 1f / mMinHeaderHeight;

        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        mMinHeaderTranslation = -mMinHeaderHeight;

        indicator.setScrollBar(new ColorBar(getActivity().getApplicationContext(), redMain, 4));
        indicator.setOnTransitionListener(new OnTransitionTextListener().setColor(red, black).setSize(16, 16));


        pager.setOffscreenPageLimit(2);

        mIndicatorViewPager = new IndicatorViewPager(indicator, pager);
        mIndicatorViewPager.setPageMargin(0);

        pagerAdapter = new PagerAdapter(getChildFragmentManager());
        mIndicatorViewPager.setAdapter(pagerAdapter);
        pagerAdapter.setTabHolderScrollingContent(this);
        pagerAdapter.notifyDataSetChanged();
        mIndicatorViewPager.notifyDataSetChanged();

        mIndicatorViewPager.setOnIndicatorPageChangeListener(new IndicatorViewPager.OnIndicatorPageChangeListener() {
            @Override
            public void onIndicatorPageChange(int preItem, int currentItem) {
                currentHolder = pagerAdapter.getScrollTabHolders().valueAt(currentItem);
                if (currentHolder != null) {
                    currentHolder.adjustScroll((int) (header.getHeight() + header.getTranslationY() - 1));
                }
            }
        });


        if (hed != null) {
            hed.setAlpha(0);
            hed.setVisibility(View.GONE);
            hed.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentHolder = pagerAdapter.getScrollTabHolders().valueAt(position);
        if (currentHolder != null) {
            currentHolder.adjustScroll((int) (header.getHeight() + header.getTranslationY() - 1));
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void adjustScroll(int scrollHeight) {

    }



    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition) {
        if (pager.getCurrentItem() == pagePosition) {
            scrollY = getScrollY(view);// 751 1440
            if (hed != null) {
                hed.setAlpha(result * scrollY);
                if (scrollY == 0) {
                    hed.setVisibility(View.GONE);
                } else {
                    hed.setVisibility(View.VISIBLE);
                }
            }
            int scrolly=Math.max(-scrollY, mMinHeaderTranslation);
            ViewHelper.setTranslationY(header,scrolly);
        }
    }

    @Override
    public void onHeaderScroll(boolean isRefreashing, int value, int pagePosition) {
        if (pager.getCurrentItem() != pagePosition) {
            return;
        }
        headerScrollSize = value;
        ViewHelper.setTranslationY(header, -value);
    }


    public int getScrollY(AbsListView view) {
        View c = view.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = view.getFirstVisiblePosition();
        int top = c.getTop();
        int headerHeight = 0;
        if (firstVisiblePosition == 0) {
            return -top + headerScrollSize;
        } else if (firstVisiblePosition == 1) {
            return -top;
        } else {
            return -top + (firstVisiblePosition - 2) * c.getHeight() + headerHeight;
        }
    }



    public class PagerAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
        private LayoutInflater inflater;

        private ScrollTabHolder mListener;
        private String[] TITLES = {"标题1", "标题2"};

        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            inflater = LayoutInflater.from(getActivity());
            mScrollTabHolders = new SparseArrayCompat<ScrollTabHolder>();
        }

        public void setTabHolderScrollingContent(ScrollTabHolder listener) {
            mListener = listener;
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_title, container, false);
            }
            TextView textView = (TextView) convertView;
            textView.setText(TITLES[position]);
            textView.setTextSize(14);
            textView.setTextColor(Color.parseColor("#3b3b3b"));
            return convertView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            switch (position) {
                case 0:
                    if(fragment1==null){
                        fragment1 = TabListFragment.newInstance();
                        fragment1.setFragmentId(position);
                    }
                    fragment1.setScrollTabHolder(mListener);
                    mScrollTabHolders.put(position, fragment1);
                    return fragment1;
                case 1:
                    if(fragment2==null){
                        fragment2 = TabListFragment.newInstance();
                        fragment2.setFragmentId(position);
                    }
                    fragment2.setScrollTabHolder(mListener);
                    mScrollTabHolders.put(position, fragment2);
                    return fragment2;
            }
            return null;
        }

        public SparseArrayCompat<ScrollTabHolder> getScrollTabHolders() {
            return mScrollTabHolders;
        }
    }


    private void initImageLoader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
                showImageForEmptyUri(R.mipmap.ic_launcher)
                .cacheInMemory(true).cacheOnDisk(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getActivity().getApplicationContext()).defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }

    private void initBanner() {
        initImageLoader();
        convenientBanner.setPages(
                new CBViewHolderCreator<NetworkImageHolderView>() {
                    @Override
                    public NetworkImageHolderView createHolder() {
                        return new NetworkImageHolderView();
                    }
                }, Arrays.asList("http://img2.3lian.com/2014/f2/37/d/40.jpg",
                        "http://img2.3lian.com/2014/f2/37/d/39.jpg",
                        "http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg",
                        "http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg",
                        "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg"));
        convenientBanner.setPageIndicator(new int[]{R.mipmap.point_empty, R.mipmap.point_red});
        convenientBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        convenientBanner.setPageTransformer(new DefaultTransformer());
        convenientBanner.setManualPageable(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        convenientBanner.startTurning(2000);
    }

    @Override
    public void onStop() {
        super.onStop();
        convenientBanner.stopTurning();
    }
}
