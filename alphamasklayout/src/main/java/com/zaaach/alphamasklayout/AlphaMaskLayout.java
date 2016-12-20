package com.zaaach.alphamasklayout;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import java.util.ArrayList;

/**
 * AlphaMaskFrameLayout
 * Author：Bro0cL on 2016/12/13.
 */
public class AlphaMaskLayout extends FrameLayout {
    private static int DEFAULT_ALPHA_FROM   = 0;
    private static int DEFAULT_ALPHA_TO     = 0;
    private static int DEFAULT_DURATION     = 0;
    /**default foreground color*/
    private static String DEFAULT_COLOR     = "#1f1f1f";

    /** start alpha */
    private int alphaFrom;
    /** end alpha */
    private int alphaTo;
    /** animation duration */
    private int duration;
    /** alpha listener */
    private OnAlphaFinishedListener onAlphaFinishedListener;

    /** anim list */
    private ArrayList<ValueAnimator> animList = new ArrayList<>();

    public AlphaMaskLayout(Context context) {
        this(context, null);
    }

    public AlphaMaskLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlphaMaskLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
        getAttrs(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AlphaMaskLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        getAttrs(context, attrs);
    }

    private void getAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AlphaMaskLayout);
        alphaFrom = a.getInt(R.styleable.AlphaMaskLayout_aml_alpha_from, DEFAULT_ALPHA_FROM);
        alphaTo = a.getInt(R.styleable.AlphaMaskLayout_aml_alpha_to, DEFAULT_ALPHA_TO);
        duration = a.getInt(R.styleable.AlphaMaskLayout_aml_duration, DEFAULT_DURATION);
        a.recycle();

        //init foreground alpha
        if (getForeground() == null){
            setForeground(new ColorDrawable(Color.parseColor(DEFAULT_COLOR)));
        }
        getForeground().setAlpha(0);

        checkAttrsValues();
    }

    private void checkAttrsValues() {
        if (alphaFrom < 0 || alphaFrom >255)
            throw new RuntimeException("hey man: the value of alpha_from must be 0~255.");
        if (alphaTo < 0 || alphaTo > 255)
            throw new RuntimeException("hey man: the value of alpha_to must be 0~255.");
        if (duration < 0)
            throw new RuntimeException("hey man: the value of duration must be >0");
    }

    private void setForgroundAlpha(int alpha){
        this.getForeground().setAlpha(alpha);
    }

    private void executeAlphaAnimation(final int from, final int to, final int duration) {
        checkAttrsValues();

        final ValueAnimator valueAnimator = ValueAnimator.ofInt(from, to);
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int newAlpha = (int) animation.getAnimatedValue();
                setForgroundAlpha(newAlpha);
                //anim finished
                if (newAlpha == to){
                    valueAnimator.cancel();
                    animList.clear();
                    if (onAlphaFinishedListener != null){
                        if (from > to){
                            onAlphaFinishedListener.onHideFinished();
                        }else {
                            onAlphaFinishedListener.onShowFinished();
                        }
                    }
                }
            }
        });
        valueAnimator.start();
        animList.add(valueAnimator);
    }

    /**
     * get alpha when animation finished or intercepted
     * @return
     */
    private int getLastFinishedAlpha(boolean isShow){
        if(animList.size() == 0){
            return isShow ? alphaFrom : alphaTo;
        }else{
            ValueAnimator animator = animList.get(animList.size() - 1);
            animator.cancel();
            int alpha = (int) animator.getAnimatedValue();
            animList.clear();
            return alpha;
        }
    }

    /**
     * set start alpha 0~255
     * @param from
     */
    public void setAlphaFrom(int from){
        this.alphaFrom = from;
    }

    /**
     * set end alpha 0~255
     * @param to
     */
    public void setAlphaTo(int to){
        this.alphaTo = to;
    }

    public void setDuration(int duration){
        this.duration = duration;
    }

    public void showMask(){
        int lastAlpha = getLastFinishedAlpha(true);
        if (lastAlpha >= alphaTo) return;
        executeAlphaAnimation(lastAlpha, alphaTo, duration);
    }

    public void hideMask(){
        int lastAlpha = getLastFinishedAlpha(false);
        if (lastAlpha <= alphaFrom) return;
        executeAlphaAnimation(lastAlpha, alphaFrom, duration);
    }

    public void setOnAlphaFinishedListener(OnAlphaFinishedListener onAlphaFinishedListener) {
        this.onAlphaFinishedListener = onAlphaFinishedListener;
    }

    public interface OnAlphaFinishedListener{
        void onShowFinished();
        void onHideFinished();
    }
}
