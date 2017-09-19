package com.guluwa.yflowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 俊康 on 2017/9/16.
 */

public class YFlowLayout extends ViewGroup {

    private OnItemClickListener listener;

    public YFlowLayout(Context context) {
        this(context, null);
    }

    public YFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.YFlowLayout, defStyleAttr, 0);
        array.recycle();
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //获取父布局给flowlayout设置的宽高和测量模式
        int mParentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int mWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int mParentHeight = MeasureSpec.getSize(heightMeasureSpec);
        int mHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        System.out.println(mParentWidth + "," + mParentHeight);

        //当测量模式是wrap_content的时候的宽高
        int mMesureWidth = 0, mMeasureHeight = 0;

        //每一行当前的宽高
        int mLineWidth = 0, mLineHeight = 0;

        //子view数量
        int mChildrenSize = getChildCount();

        //开始遍历每一个子view
        for (int i = 0; i < mChildrenSize; i++) {
            View child = getChildAt(i);

            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
            //子view的宽高
            int mChildWidth = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            int mChildHeight = child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;

            //如果加入当前的这个child，判断这行是否能够放下
            if (mChildWidth + mLineWidth < mParentWidth - getPaddingLeft() - getPaddingRight()) {//能够放得下
                mLineWidth += mChildWidth;
                mLineHeight = Math.max(mLineHeight, mChildHeight);
            } else {//放不下
                mMesureWidth = Math.max(mMesureWidth, Math.max(mLineWidth, mChildWidth));//可能出现一行就比前面累计的所有的和还要大的情况
                mMeasureHeight += mLineHeight;
                mLineWidth = mChildWidth;
                mLineHeight = mChildHeight;
            }
            // 如果是最后一个，则将当前记录的最大宽度和当前lineWidth做比较
            if (i == mChildrenSize - 1) {
                mMesureWidth = Math.max(mMesureWidth, mLineWidth);
                mMeasureHeight += mLineHeight;
            }
        }

        setMeasuredDimension(
                mWidthMode == MeasureSpec.EXACTLY ? mParentWidth : mMesureWidth + getPaddingLeft() + getPaddingRight(),
                mHeightMode == MeasureSpec.EXACTLY ? mParentHeight : mMeasureHeight + getPaddingTop() + getPaddingBottom()
        );
    }

    /**
     * 存储所有的View，按行记录
     */
    private List<List<View>> mAllViews = new ArrayList<>();
    /**
     * 记录每一行的最大高度
     */
    private List<Integer> mLineMaxHeight = new ArrayList<>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        mAllViews.clear();
        mLineMaxHeight.clear();

        //flowlayout宽度
        int mFlowLayoutWidth = getWidth();

        System.out.println("width:" + getWidth() + ",height:" + getHeight());

        //每一行当前的宽高
        int mLineWidth = 0, mLineHeight = 0;

        //子view数量
        int mChildrenSize = getChildCount();

        // 存储每一行所有的childView
        List<View> mLineViews = new ArrayList<>();

        for (int i = 0; i < mChildrenSize; i++) {
            View child = getChildAt(i);

            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
            //子view的宽高
            int mChildWidth = child.getMeasuredWidth() +
                    layoutParams.leftMargin + layoutParams.rightMargin;
            int mChildHeight = child.getMeasuredHeight() +
                    layoutParams.topMargin + layoutParams.bottomMargin;

            //如果加入当前的这个child，判断这行是否能够放下
            if (mChildWidth + mLineWidth > mFlowLayoutWidth - getPaddingLeft() - getPaddingRight() ) {//放不下
                //记录上一行的最大高度
                mLineMaxHeight.add(mLineHeight);
                //记录上一行的所有view
                mAllViews.add(mLineViews);
                mLineWidth = 0;
                mLineViews = new ArrayList<>();
            }
            mLineViews.add(child);
            mLineWidth += mChildWidth;
            mLineHeight = Math.max(mLineHeight, mChildHeight);
        }
        // 记录最后一行
        mLineMaxHeight.add(mLineHeight);
        mAllViews.add(mLineViews);

        //子view的上边距，左边距
        int top = getPaddingTop(), left = getPaddingLeft();

        int lines = mAllViews.size();
        System.out.println(lines);

        for (int i = 0; i < lines; i++) {

            //当前行的所有子view
            mLineViews = mAllViews.get(i);
            //当前行的高度
            mLineHeight = mLineMaxHeight.get(i);

            System.out.println("第" + i + "行，" + mLineViews.size());

            int views = mLineViews.size();
            for (int j = 0; j < views; j++) {
                View child = mLineViews.get(j);
                //child不可见就不绘制
                if (child.getVisibility() == GONE)
                    continue;
                MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
                //子view的上下左右边距
                int lm, tm, rm, bm;
                lm = (int) (left + layoutParams.leftMargin);
                tm = top + layoutParams.topMargin;
                rm = lm + child.getMeasuredWidth();
                bm = tm + child.getMeasuredHeight();

                child.layout(lm, tm, rm, bm);

                left += child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;

                final int position = calculatePosition(i, j);

                child.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onClick(position, view);
                    }
                });
            }
            left = getPaddingLeft();
            top += mLineHeight;
        }
    }

    private int calculatePosition(int i, int j) {
        int ans = 0;
        for (int k = 0; k < i; k++) {
            ans += mAllViews.get(k).size();
        }
        ans += j;
        return ans;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static int dp2px(Context context, float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density + 0.5f);
    }
}
