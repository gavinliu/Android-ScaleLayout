package cn.gavinliu.android.lib.scale.config;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by Gavin on 16-9-20.
 */
public class ScaleConfig {

    private int mDesignWidth = 1080;
    private int mDesignHeight = 1920;

    private int mScreenWidth;
    private int mScreenHeight;

    private int mScreenWidthLand;
    private int mScreenHeightLand;

    private static ScaleConfig mInstance;

    public static void create(Context ctx) {
        if (mInstance == null) {
            mInstance = new ScaleConfig(ctx);
        }
    }

    public static ScaleConfig getInstance() {
        return mInstance;
    }

    private ScaleConfig(Context ctx) {
        final int width = ctx.getResources().getDisplayMetrics().widthPixels;
        final int height = ctx.getResources().getDisplayMetrics().heightPixels;

        if (ctx.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mScreenWidth = width;
            mScreenHeight = height;

            mScreenWidthLand = height;
            mScreenHeightLand = width;
        } else {
            mScreenWidth = height;
            mScreenHeight = width;

            mScreenWidthLand = width;
            mScreenHeightLand = height;
        }

    }

    public int getDesignWidth() {
        return mDesignWidth;
    }

    public int getDesignHeight() {
        return mDesignHeight;
    }

    public int getScreenWidth() {
        return mScreenWidth;
    }

    public int getScreenHeight() {
        return mScreenHeight;
    }
}
