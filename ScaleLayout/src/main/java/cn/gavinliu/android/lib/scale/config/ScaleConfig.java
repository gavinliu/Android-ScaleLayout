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

    private float mDesignFontScale;
    private float mScreenFontScale;

    private int mDimensionUnit;

    public static final int DIMENS_UNIT_DP = 0;
    public static final int DIMENS_UNIT_PIX = 1;

    private boolean mDebug;

    private static ScaleConfig mInstance;

    /**
     * @param ctx             Context
     * @param designWidth     Design Width (Pixel)
     * @param designHeight    Design Height (Pixel)
     * @param designDensity   Design Density
     * @param designFontScale Design FontScale (getDisplayMetrics().scaledDensity)
     * @param dimensionUnit   DIMENS_UNIT_DP or DIMENS_UNIT_PIX
     * @return Single instance
     */
    public static ScaleConfig create(Context ctx, int designWidth, int designHeight, float designDensity, float designFontScale, int dimensionUnit) {
        if (mInstance == null) {
            mInstance = new ScaleConfig(ctx, designWidth, designHeight, designDensity, designFontScale, dimensionUnit);
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
        final float fontScale = ctx.getResources().getDisplayMetrics().scaledDensity;

        mScreenWidth = width;
        mScreenHeight = height;
        mScreenDensity = density;
        mScreenFontScale = fontScale;
    }

    private ScaleConfig(Context ctx, int designWidth, int designHeight, float designDensity, float designFontScale, int dimensionUnit) {
        this(ctx);
        mDesignWidth = designWidth;
        mDesignHeight = designHeight;
        mDesignDensity = designDensity;
        mDesignFontScale = designFontScale;
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

    public float getDesignFontScale() {
        return mDesignFontScale;
    }

    public float getScreenFontScale() {
        return mScreenFontScale;
    }

    public int getScreenWidth() {
        return mScreenWidth;
    }

    public int getScreenHeight() {
        return mScreenHeight;
    }
}
