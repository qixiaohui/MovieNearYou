package com.movienearyou.xiaohui.movienearyou.Views;

/**
 * Created by TQi on 7/24/16.
 */
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class FadingScrollView extends ScrollView {

    private OnScrollViewListener mOnScrollViewListener;

    public FadingScrollView(Context context) {
        super(context);
    }
    public FadingScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public FadingScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public interface OnScrollViewListener {
        void onScrollChanged( FadingScrollView v, int l, int t, int oldl, int oldt );
    }

    public void setOnScrollViewListener(OnScrollViewListener l) {
        this.mOnScrollViewListener = l;
    }

    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        mOnScrollViewListener.onScrollChanged( this, l, t, oldl, oldt );
        super.onScrollChanged( l, t, oldl, oldt );
    }
}