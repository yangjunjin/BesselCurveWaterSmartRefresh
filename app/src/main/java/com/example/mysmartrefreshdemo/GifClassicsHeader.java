package com.example.mysmartrefreshdemo;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

/**
 * @author yjj
 * @date 2020/4/9
 */
public class GifClassicsHeader extends ClassicsHeader {
    private ProgressBar mProgressBar;
    private DragBallView mDragBallView;//粘性

    private static long lastclicktime = 0;
    private static float startPercent = 0.4f;
    private final static int INTERVAL_TIME = 40;

    public GifClassicsHeader(Context context) {
        this(context, null);
    }

    public GifClassicsHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDragBallView = new DragBallView(context);
        mProgressBar = new ProgressBar(context);

        LayoutParams params = new LayoutParams(SizeUtils.dp2px(100), ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_IN_PARENT);
        mDragBallView.setLayoutParams(params);

        params = new LayoutParams(SizeUtils.dp2px(30), SizeUtils.dp2px(30));
        params.addRule(CENTER_IN_PARENT);
        mProgressBar.setLayoutParams(params);
        mProgressBar.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress_bar_gradient));

        mTitleText.setVisibility(View.GONE);
        addView(mDragBallView);
        addView(mProgressBar);
        reset();
    }


    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
        super.onMoving(isDragging, percent, offset, height, maxDragHeight);//percent = 2.5最大
        float offsetY =  percent*90f/1.1f;
        mDragBallView.setPercent(offsetY);

        LogUtils.e("onMoving==isDragging="+isDragging+",percent="+percent+",offset="+offset+",height="+height+",maxDragHeight="+maxDragHeight);
        LogUtils.e("GifClassicsHeader中的onMoving:1");
        if (!isDragging) {
            LogUtils.e("GifClassicsHeader中的onMoving:2");
            return;
        }

        if (System.currentTimeMillis() - lastclicktime > INTERVAL_TIME) {
            LogUtils.e("GifClassicsHeader中的onMoving:3");
            lastclicktime = System.currentTimeMillis();
            if (percent < startPercent) {
                LogUtils.e("GifClassicsHeader中的onMoving:4");
                return;
            }
        }
    }

    public void reset() {
        mProgressBar.setVisibility(INVISIBLE);
        mDragBallView.setVisibility(INVISIBLE);
    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        super.onStartAnimator(refreshLayout, height, maxDragHeight);
        LogUtils.e("GifClassicsHeader中的onStartAnimator==height="+height+",maxDragHeight="+maxDragHeight);
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        super.onStateChanged(refreshLayout, oldState, newState);
        switch (newState) {
            case None:
            case PullDownToRefresh:
                if (mProgressBar.getVisibility() == VISIBLE) {
                    mProgressBar.setVisibility(View.GONE);
                }
                mDragBallView.setVisibility(VISIBLE);
                LogUtils.e("GifClassicsHeader中的onStateChanged:1");
                break;
            case RefreshReleased:
                mDragBallView.reset();
                if (mDragBallView.getVisibility() == VISIBLE) {
                    mDragBallView.setVisibility(GONE);
                }
                LogUtils.e("GifClassicsHeader中的onStateChanged:2");
                mProgressBar.setVisibility(View.VISIBLE);
                break;
            case RefreshFinish:
                mDragBallView.reset();
                LogUtils.e("GifClassicsHeader中的onStateChanged:3");
                break;
            case ReleaseToTwoLevel:
                LogUtils.e("GifClassicsHeader中的onStateChanged:4");
                break;
            case Loading:
                LogUtils.e("GifClassicsHeader中的onStateChanged:5");
                break;
        }
    }
}
