package com.zjy.study.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.zjy.study.R;
import com.zjy.study.cusview.scrolltab.ScrollTabHolderFragment;

import java.util.Arrays;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TabListFragment extends ScrollTabHolderFragment {
    private static final String ARG_PARAM1 = "param1";
    private View placeHolderView;

    @BindView(R.id.lv_listview)
    PullToRefreshListView lvListview;

    private QuickAdapter<String> adapter;
    private int mMinHeaderHeight = 0;

    public TabListFragment() {
    }


    public static TabListFragment newInstance() {
        TabListFragment fragment = new TabListFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_refresh, container, false);
        placeHolderView = inflater.inflate(R.layout.view_header_placeholder, null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            setFragmentId(getArguments().getInt(ARG_PARAM1));
        }
    }

    private void init() {
        mMinHeaderHeight = getResources().getDimensionPixelSize(R.dimen.scroll_height);
        setListViewListener();
        lvListview.getRefreshableView().addHeaderView(placeHolderView);
        setDatas();
    }

    private void setListViewListener() {
        lvListview.setMode(PullToRefreshBase.Mode.BOTH);
        lvListview.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                scrollTabHolder.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount, getFragmentId());
            }
        });
        lvListview.setOnHeaderScrollListener(new PullToRefreshListView.OnHeaderScrollListener() {

            @Override
            public void onHeaderScroll(boolean isRefreashing, boolean istop, int value) {
                if (istop) {
                    scrollTabHolder.onHeaderScroll(isRefreashing, value, getFragmentId());
                }
            }
        });
    }

    @Override
    public void scrollListViewFirstChildPosition(int y) {
        super.scrollListViewFirstChildPosition(y);
//        if (lvListview != null && adapter != null && adapter.getCount() > 0 && Math.abs(y) <= mMinHeaderHeight) {
//            if (lvListview.getChildAt(0) != null) {
//                View view = lvListview.getChildAt(0);
//                if (view != null) {
//                    view.setTop(y);
//                }
//                view = lvListview.getChildAt(1);
//                if (view != null) {
//                    view.setTop(y);
//                }
//                adapter.notifyDataSetChanged();
//            }
//        }
    }

    private void setDatas() {
        String[] mDatas = {"斯柯达福克斯的积分", "s接收到回复可接受的", "欧洲剑偶家我", "室带肥厚", "撒地方快乐环岛是", "史蒂芬凯勒", "圣诞节回复", "收到回复", "斯柯达福克斯的积分", "s接收到回复可接受的", "欧洲剑偶家我", "室带肥厚", "撒地方快乐环岛是", "史蒂芬凯勒", "圣诞节回复", "收到回复",
                "斯柯达福克斯的积分", "s接收到回复可接受的", "欧洲剑偶家我", "室带肥厚", "撒地方快乐环岛是", "史蒂芬凯勒", "圣诞节回复", "收到回复", "斯柯达福克斯的积分", "s接收到回复可接受的", "欧洲剑偶家我", "室带肥厚", "撒地方快乐环岛是", "史蒂芬凯勒", "圣诞节回复", "收到回复"};
        adapter = new QuickAdapter<String>(getActivity(), R.layout.item_fragment_list, Arrays.asList(mDatas)) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {
                helper.setText(R.id.id, new Random().nextInt() + "");
                helper.setText(R.id.content, item);
            }
        };
        lvListview.setAdapter(adapter);
    }

    @Override
    public void adjustScroll(int scrollHeight) {
        if (scrollHeight == 0 && lvListview.getRefreshableView().getFirstVisiblePosition() >= 2) {
            return;
        }
        lvListview.getRefreshableView().setSelectionFromTop(2, scrollHeight);
    }
}
