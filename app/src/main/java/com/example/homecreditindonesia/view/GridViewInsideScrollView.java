package com.example.homecreditindonesia.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class GridViewInsideScrollView extends GridView {

    public GridViewInsideScrollView(Context context) {
        super(context);
    }

    public GridViewInsideScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridViewInsideScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(MEASURED_SIZE_MASK, MeasureSpec.AT_MOST));
        getLayoutParams().height = getMeasuredHeight();
    }
}
