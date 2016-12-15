package com.zjy.study.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
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


public class StickyFragment extends Fragment {


    @BindView(R.id.convenientBanner)
    ConvenientBanner convenientBanner;

    public StickyFragment() {
    }


    public static StickyFragment newInstance() {
        StickyFragment fragment = new StickyFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sticky, container, false);
        ButterKnife.bind(this, view);
        initBanner();
        return view;
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
        convenientBanner.setManualPageable(true);//设置不能手动影响
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
