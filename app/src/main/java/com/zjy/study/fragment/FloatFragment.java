package com.zjy.study.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.handmark.pulltorefresh.library.MyScrollView;
import com.handmark.pulltorefresh.library.OnScrollListener;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.zjy.study.R;
import com.zjy.study.banner.NetworkImageHolderView;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 使用两个相同浮动布局
 */
public class FloatFragment extends Fragment implements OnScrollListener {
    @BindView(R.id.scrollView)
    MyScrollView scrollView;
    @BindView(R.id.parent_layout)
    LinearLayout parentLayout;
    @BindView(R.id.convenientBanner)
    ConvenientBanner convenientBanner;

    private View mRootView;
    private LinearLayout mBuyLayout;
    private LinearLayout mTopBuyLayout;

    public FloatFragment() {
    }

    public static FloatFragment newInstance() {
        FloatFragment fragment = new FloatFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_float, container, false);
        ButterKnife.bind(this, mRootView);
        initFlot();
        initBanner();
        scrollView.fullScroll(ScrollView.FOCUS_UP);
        return mRootView;
    }


    private void initFlot() {
        mBuyLayout = (LinearLayout) mRootView.findViewById(R.id.buy);
        mTopBuyLayout = (LinearLayout) mRootView.findViewById(R.id.top_buy_layout);
        scrollView.setOnScrollListener(this);
        //当布局的状态或者控件的可见性发生改变回调的接口
        parentLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //这一步很重要，使得上面的购买布局和下面的购买布局重合
                onScroll(scrollView.getScrollY());
                System.out.println(scrollView.getScrollY());
            }
        });
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

    //初始化网络图片缓存库
    private void initImageLoader() {
        //网络图片例子,结合常用的图片缓存库UIL,你可以根据自己需求自己换其他网络图片库
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
//        convenientBanner.setPages(
//                new CBViewHolderCreator<LocalImageHolderView>() {
//                    @Override
//                    public LocalImageHolderView createHolder() {
//                        return new LocalImageHolderView();
//                    }
//                }, Arrays.asList(R.mipmap.pic_1, R.mipmap.pic_2));

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
//        convenientBanner.setPageTransformer(new AccordionTransformer());
//        convenientBanner.setPageTransformer(new BackgroundToForegroundTransformer());
//        convenientBanner.setPageTransformer(new CubeInTransformer());
//        convenientBanner.setPageTransformer(new CubeOutTransformer());
//        convenientBanner.setPageTransformer(new DepthPageTransformer());
//        convenientBanner.setPageTransformer(new FlipHorizontalTransformer());
//        convenientBanner.setPageTransformer(new FlipVerticalTransformer());
//        convenientBanner.setPageTransformer(new ForegroundToBackgroundTransformer());
//        convenientBanner.setPageTransformer(new RotateDownTransformer());
//        convenientBanner.setPageTransformer(new RotateUpTransformer());
//        convenientBanner.setPageTransformer(new StackTransformer());
//        convenientBanner.setPageTransformer(new ZoomInTransformer());
//        convenientBanner.setPageTransformer(new ZoomOutTranformer());
        convenientBanner.setManualPageable(true);//设置不能手动影响
    }

    @Override
    public void onScroll(int scrollY) {
        int mBuyLayout2ParentTop = Math.max(scrollY, mBuyLayout.getTop());
        mTopBuyLayout.layout(0, mBuyLayout2ParentTop, mTopBuyLayout.getWidth(), mBuyLayout2ParentTop + mTopBuyLayout.getHeight());
    }


}