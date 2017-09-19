package com.guluwa.yflowlayout;

import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 俊康 on 2017/9/18.
 */

public abstract class YLabelAdapter<T> {

    private List<T> mLabelList;//数据源
    private OnDataUpdateListener listener;//数据更新回调接口

    public YLabelAdapter(List<T> mLabelList) {
        this.mLabelList = mLabelList;
    }

    public YLabelAdapter(T[] array) {
        this.mLabelList = new ArrayList<>(Arrays.asList(array));
    }

    public void setListener(OnDataUpdateListener listener) {
        this.listener = listener;
    }

    public int getLabelCount() {
        return mLabelList == null ? 0 : mLabelList.size();
    }

    public void notifyDataUpdated() {
        listener.onUpdate();
    }

    public T getItem(int position) {
        return mLabelList.get(position);
    }

    public abstract View getView(YFlowLayout parent, int position, T t);
}
