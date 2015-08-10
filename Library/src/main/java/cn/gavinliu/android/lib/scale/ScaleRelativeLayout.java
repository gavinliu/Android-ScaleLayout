package cn.gavinliu.android.lib.scale;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import cn.gavinliu.android.lib.scale.helper.ScaleLayoutHelper;

/**
 * Created by GavinLiu on 2015-08-10
 */
public class ScaleRelativeLayout extends RelativeLayout {

    private ScaleLayoutHelper mHelper;

    public ScaleRelativeLayout(Context context) {
        this(context, null);
    }

    public ScaleRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        createHelper(context, attrs);
    }

    private void createHelper(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ScaleLayout_Layout);
        int width = 0, height = 0, density = 0;

        int value = array.getDimensionPixelSize(R.styleable.ScaleLayout_Layout_layout_design_width, 0);
        if (value != 0) {
            width = value;
        }
        value = array.getDimensionPixelSize(R.styleable.ScaleLayout_Layout_layout_design_height, 0);
        if (value != 0) {
            height = value;
        }
        value = array.getInteger(R.styleable.ScaleLayout_Layout_layout_design_density, 0);
        if (value != 0) {
            density = value;
        }
        array.recycle();

        mHelper = new ScaleLayoutHelper(this, width, height, density);
    }

    @Override
    public ScaleRelativeLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new ScaleRelativeLayout.LayoutParams(this.getContext(), attrs);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.mHelper.adjustChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.mHelper.handleMeasuredStateTooSmall()) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.mHelper.restoreOriginalParams();
    }

    public static class LayoutParams extends android.widget.RelativeLayout.LayoutParams implements ScaleLayoutHelper.ScaleLayoutParams {
        private ScaleLayoutHelper.ScaleLayoutInfo mPercentLayoutInfo;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            this.mPercentLayoutInfo = ScaleLayoutHelper.getScaleLayoutInfo(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        @Override
        public ScaleLayoutHelper.ScaleLayoutInfo getScaleLayoutInfo() {
            return this.mPercentLayoutInfo;
        }

        protected void setBaseAttributes(TypedArray a, int widthAttr, int heightAttr) {
            ScaleLayoutHelper.fetchWidthAndHeight(this, a, widthAttr, heightAttr);
        }

    }
}
