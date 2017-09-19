package com.guluwa.yflowlayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;

/**
 * Created by 俊康 on 2017/9/18.
 */

public class YLabelItemView extends FrameLayout implements Checkable {

    private boolean isChecked;
    private static final int[] CHECK_STATE = new int[]{android.R.attr.state_checked};

    public YLabelItemView(@NonNull Context context) {
        super(context);
    }

    public View getLabelItemView() {
        return getChildAt(0);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        int[] states = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(states, CHECK_STATE);
        }
        return states;
    }

    @Override
    public void setChecked(boolean checked) {
        if (this.isChecked != checked) {
            this.isChecked = checked;
            refreshDrawableState();
        }
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        setChecked(!isChecked);
    }
}
