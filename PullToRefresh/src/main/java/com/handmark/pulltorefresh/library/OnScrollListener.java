package com.handmark.pulltorefresh.library;


/**
 * 滚动的回调接口
 */
public interface OnScrollListener {
    /**
     * 回调方法， 返回MyScrollView滑动的Y方向距离
     */
    public void onScroll(int scrollY);
}
