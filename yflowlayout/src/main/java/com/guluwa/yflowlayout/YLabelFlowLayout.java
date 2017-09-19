package com.guluwa.yflowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 俊康 on 2017/9/19.
 */

public class YLabelFlowLayout extends YFlowLayout implements OnDataUpdateListener{

    private YLabelAdapter mAdapter;

    public YLabelFlowLayout(Context context) {
        this(context, null);
    }

    public YLabelFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YLabelFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setAdapter(YLabelAdapter mAdapter) {
        this.mAdapter = mAdapter;
        mAdapter.setListener(this);
        showView();
    }

    private void showView() {
        removeAllViews();
        YLabelAdapter mLabelAdapter = mAdapter;
        YLabelItemView mLabelContainer = null;

        for (int i = 0; i < mLabelAdapter.getLabelCount(); i++) {
            View mLabelView = mLabelAdapter.getView(this, i, mLabelAdapter.getItem(i));
            mLabelView.setDuplicateParentStateEnabled(true);
            mLabelContainer.setLayoutParams(mLabelView.getLayoutParams());
            mLabelContainer.addView(mLabelView);
            addView(mLabelContainer);
        }
    }

    @Override
    public void onUpdate() {
        showView();
    }
}
