package cn.gavinliu.android.lib.scale.config;

import android.content.Context;

/**
 * Created by Gavin on 16-9-20.
 */
public class ScaleConfig {

    private int mDesignWidth;
    private int mDesignHeight;
    private float mDesignDensity;

    private int mScreenWidth;
    private int mScreenHeight;
    private float mScreenDensity;

    private int mDimensionUnit;

    public static final int DIMENS_UNIT_DP = 0;
    public static final int DIMENS_UNIT_PIX = 1;

    private boolean mDebug;

    private static ScaleConfig mInstance;

    public static ScaleConfig create(Context ctx, int designWidth, int designHeight, int designDensity, int dimensionUnit) {
        if (mInstance == null) {
            mInstance = new ScaleConfig(ctx, designWidth, designHeight, designDensity, dimensionUnit);
        }

        return mInstance;
    }

    public static ScaleConfig getInstance() {
        if (mInstance == null)
            throw new IllegalArgumentException("You must call ScaleConfig.create first");

        return mInstance;
    }

    private ScaleConfig(Context ctx) {
        final int width = ctx.getResources().getDisplayMetrics().widthPixels;
        final int height = ctx.getResources().getDisplayMetrics().heightPixels;
        final float density = ctx.getResources().getDisplayMetrics().density;

        mScreenWidth = width;
        mScreenHeight = height;
        mScreenDensity = density;
    }

    private ScaleConfig(Context ctx, int designWidth, int designHeight, int designDensity, int dimensionUnit) {
        this(ctx);
        mDesignWidth = designWidth;
        mDesignHeight = designHeight;
        mDesignDensity = designDensity;
        mDimensionUnit = dimensionUnit;
    }

    public boolean isDebug() {
        return mDebug;
    }

    public void setDebug(boolean debug) {
        mDebug = debug;
    }

    public boolean isDimensUnitByDp() {
        return mDimensionUnit == DIMENS_UNIT_DP;
    }

    public int getDesignWidth() {
        return mDesignWidth;
    }

    public int getDesignHeight() {
        return mDesignHeight;
    }

    public float getDesignDensity() {
        return mDesignDensity;
    }

    public float getScreenDensity() {
        return mScreenDensity;
    }

    public int getScreenWidth() {
        return mScreenWidth;
    }

    public int getScreenHeight() {
        return mScreenHeight;
    }
}
