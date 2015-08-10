package cn.gavinliu.android.lib.scale.helper;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import cn.gavinliu.android.lib.scale.R;

/**
 * Created by GavinLiu on 2015-08-10
 */
public class ScaleLayoutHelper {

    private static final String TAG = "ScaleLayoutHelper";
    private final ViewGroup mHost;

    private float designWidth;
    private float designHeight;
    private float designDensity;

    public interface ScaleLayoutParams {
        ScaleLayoutInfo getScaleLayoutInfo();
    }

    private ScaleLayoutHelper(ViewGroup mHost, float designWidth, float designHeight, float designDensity) {
        this.mHost = mHost;
        this.designWidth = designWidth;
        this.designHeight = designHeight;
        this.designDensity = designDensity;
    }

    public static ScaleLayoutHelper create(ViewGroup view, AttributeSet attrs) {
        TypedArray array = view.getContext().obtainStyledAttributes(attrs, R.styleable.ScaleLayout);
        int width = 0, height = 0, density = 0;

        int value = array.getDimensionPixelSize(R.styleable.ScaleLayout_layout_design_width, 0);
        if (value != 0) {
            width = value;
        }
        value = array.getDimensionPixelSize(R.styleable.ScaleLayout_layout_design_height, 0);
        if (value != 0) {
            height = value;
        }
        value = array.getInteger(R.styleable.ScaleLayout_layout_design_density, 0);
        if (value != 0) {
            density = value;
        }
        array.recycle();
        return new ScaleLayoutHelper(view, width, height, density);
    }

    public void adjustChildren(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "adjustChildren: " + this.mHost + " widthMeasureSpec: " + View.MeasureSpec.toString(widthMeasureSpec) + " heightMeasureSpec: " + View.MeasureSpec.toString(heightMeasureSpec));

        int i = 0;
        for (int N = this.mHost.getChildCount(); i < N; ++i) {
            View view = this.mHost.getChildAt(i);
            ViewGroup.LayoutParams params = view.getLayoutParams();
            Log.d(TAG, "should adjust " + view + " " + params);

            if (params instanceof ScaleLayoutParams) {
                ScaleLayoutInfo info = ((ScaleLayoutParams) params).getScaleLayoutInfo();
                Log.d(TAG, "using " + info);
                if (info != null) {
                    info.setDesignSize(designWidth, designHeight, designDensity);

                    if (params instanceof ViewGroup.MarginLayoutParams) {
                        info.fillMarginLayoutParams((ViewGroup.MarginLayoutParams) params);
                    } else {
                        info.fillLayoutParams(params);
                    }
                }
            }
        }
    }

    public void restoreOriginalParams() {
        int i = 0;

        for (int N = this.mHost.getChildCount(); i < N; ++i) {
            View view = this.mHost.getChildAt(i);
            ViewGroup.LayoutParams params = view.getLayoutParams();
            Log.d(TAG, "should restore " + view + " " + params);

            if (params instanceof ScaleLayoutParams) {
                ScaleLayoutInfo info = ((ScaleLayoutParams) params).getScaleLayoutInfo();
                Log.d(TAG, "using " + info);

                if (info != null) {
                    if (params instanceof ViewGroup.MarginLayoutParams) {
                        info.restoreMarginLayoutParams((ViewGroup.MarginLayoutParams) params);
                    } else {
                        info.restoreLayoutParams(params);
                    }
                }
            }
        }

    }

    public static void fetchWidthAndHeight(ViewGroup.LayoutParams params, TypedArray array, int widthAttr, int heightAttr) {
        params.width = array.getLayoutDimension(widthAttr, 0);
        params.height = array.getLayoutDimension(heightAttr, 0);
    }

    public static ScaleLayoutInfo getScaleLayoutInfo(Context context, AttributeSet attrs) {
        ScaleLayoutInfo info = null;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ScaleLayout);

        int value = array.getDimensionPixelSize(R.styleable.ScaleLayout_layout_width, 0);
        if (value != 0) {
            info = new ScaleLayoutInfo(context);
            info.width = value;
        }

        value = array.getDimensionPixelSize(R.styleable.ScaleLayout_layout_height, 0);
        if (value != 0) {
            info = info != null ? info : new ScaleLayoutInfo(context);
            info.height = value;
        }

        value = array.getInt(R.styleable.ScaleLayout_layout_scale_by, 0);
        if (value != 0) {
            info = info != null ? info : new ScaleLayoutInfo(context);
            info.scaleBy = value;
        }

        array.recycle();

        Log.d(TAG, "constructed: " + info);
        return info;
    }

    public boolean handleMeasuredStateTooSmall() {
        boolean needsSecondMeasure = false;
        int i = 0;

        for (int N = this.mHost.getChildCount(); i < N; ++i) {
            View view = this.mHost.getChildAt(i);
            ViewGroup.LayoutParams params = view.getLayoutParams();
            Log.d(TAG, "should handle measured state too small " + view + " " + params);

            if (params instanceof ScaleLayoutParams) {
                ScaleLayoutInfo info = ((ScaleLayoutParams) params).getScaleLayoutInfo();
                if (info != null) {
                    if (shouldHandleMeasuredWidthTooSmall(view, info)) {
                        needsSecondMeasure = true;
                        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    }

                    if (shouldHandleMeasuredHeightTooSmall(view, info)) {
                        needsSecondMeasure = true;
                        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    }
                }
            }
        }

        Log.d(TAG, "should trigger second measure pass: " + needsSecondMeasure);

        return needsSecondMeasure;
    }

    private static boolean shouldHandleMeasuredWidthTooSmall(View view, ScaleLayoutInfo info) {
        int state = ViewCompat.getMeasuredWidthAndState(view) & ViewCompat.MEASURED_STATE_MASK;
        return state == ViewCompat.MEASURED_STATE_TOO_SMALL && info.width >= 0.0F && info.mPreservedParams.width == ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    private static boolean shouldHandleMeasuredHeightTooSmall(View view, ScaleLayoutInfo info) {
        int state = ViewCompat.getMeasuredHeightAndState(view) & ViewCompat.MEASURED_STATE_MASK;
        return state == ViewCompat.MEASURED_STATE_TOO_SMALL && info.height >= 0.0F && info.mPreservedParams.height == ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    public static class ScaleLayoutInfo {
        private float designWidth;
        private float designHeight;
        private float designDensity;

        private float density;
        private float screenW;
        private float screenH;

        public float width = 0;
        public float height = 0;

        public int scaleBy = 0;

        private static final int ScaleByWidth = 0;
        private static final int ScaleByHeight = 1;

        final ViewGroup.MarginLayoutParams mPreservedParams = new ViewGroup.MarginLayoutParams(0, 0);

        public ScaleLayoutInfo(Context ctx) {
            density = ctx.getResources().getDisplayMetrics().density;
            screenW = ctx.getResources().getDisplayMetrics().widthPixels;
            screenH = ctx.getResources().getDisplayMetrics().heightPixels;
        }

        public void fillLayoutParams(ViewGroup.LayoutParams params) {
            this.mPreservedParams.width = params.width;
            this.mPreservedParams.height = params.height;

            if (this.width > 0) {
                params.width = getRealPixelSize(this.width);
            }

            if (this.height > 0) {
                params.height = getRealPixelSize(this.height);
            }

            Log.d(TAG, "after fillLayoutParams: (" + params.width + ", " + params.height + ")");
        }

        public void fillMarginLayoutParams(ViewGroup.MarginLayoutParams params) {
            this.fillLayoutParams(params);
            this.mPreservedParams.leftMargin = params.leftMargin;
            this.mPreservedParams.topMargin = params.topMargin;
            this.mPreservedParams.rightMargin = params.rightMargin;
            this.mPreservedParams.bottomMargin = params.bottomMargin;
            MarginLayoutParamsCompat.setMarginStart(this.mPreservedParams, MarginLayoutParamsCompat.getMarginStart(params));
            MarginLayoutParamsCompat.setMarginEnd(this.mPreservedParams, MarginLayoutParamsCompat.getMarginEnd(params));


            Log.d(TAG, "after fillMarginLayoutParams: (" + params.width + ", " + params.height + ")");
        }

        public void restoreMarginLayoutParams(ViewGroup.MarginLayoutParams params) {
            this.restoreLayoutParams(params);
            params.leftMargin = this.mPreservedParams.leftMargin;
            params.topMargin = this.mPreservedParams.topMargin;
            params.rightMargin = this.mPreservedParams.rightMargin;
            params.bottomMargin = this.mPreservedParams.bottomMargin;
            MarginLayoutParamsCompat.setMarginStart(params, MarginLayoutParamsCompat.getMarginStart(this.mPreservedParams));
            MarginLayoutParamsCompat.setMarginEnd(params, MarginLayoutParamsCompat.getMarginEnd(this.mPreservedParams));
        }

        public void restoreLayoutParams(ViewGroup.LayoutParams params) {
            params.width = this.mPreservedParams.width;
            params.height = this.mPreservedParams.height;
        }

        private int getRealPixelSize(float pix) {
            float screen, design;
            switch (scaleBy) {
                case ScaleByHeight:
                    screen = screenH;
                    design = designHeight;
                    break;
                default:
                    screen = screenW;
                    design = designWidth;
                    break;
            }
            return getRealPixelSize(pix, screen, design);
        }

        private int getRealPixelSize(float pix, float screen, float designPixel) {
            float newPix = (screen * designDensity * pix) / (designPixel * density);
            int result;
            if (newPix > 1) {
                result = (int) Math.rint((double) newPix);
            } else {
                result = (int) Math.ceil((double) newPix);
            }
            Log.i(TAG, "pix:" + pix + ",newPix:" + newPix + ",result:" + result);
            return result;
        }

        public void setDesignSize(float width, float height, float density) {
            designWidth = width;
            designHeight = height;
            designDensity = density;
        }
    }

}
