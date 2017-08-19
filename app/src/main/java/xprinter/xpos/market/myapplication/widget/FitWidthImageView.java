package xprinter.xpos.market.myapplication.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2017-08-19.
 */

public class FitWidthImageView extends android.support.v7.widget.AppCompatImageView {

    public FitWidthImageView(Context context) {
        super(context);
    }

    public FitWidthImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FitWidthImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable drawable = getDrawable();
        if(drawable != null) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = (int) ((float)width / (float)drawable.getIntrinsicWidth() * (float)drawable.getIntrinsicHeight());
            setMeasuredDimension(width,height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
