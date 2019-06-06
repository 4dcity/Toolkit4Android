package com.aigestudio.toolkit.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import java.util.HashMap;
import java.util.Map;

/**
 * 设备相关的工具类
 * Utils for devices
 */
public final class DevUtil {
    private static final Map<Float, Integer> DPS = new HashMap<>();
    private static final Map<Float, Integer> PXS = new HashMap<>();

    private static int[] sScreenSize;

    /**
     * 获取屏幕宽度
     * Get width of screen
     *
     * @param context ...
     * @return 屏幕宽度，单位：像素 Width of screen, unit: px
     * @see #getScreenSize(Context)
     */
    public static int getScreenWidth(final Context context) {
        return getScreenSize(context)[0];
    }

    /**
     * 获取屏幕高度
     * Get height of screen
     *
     * @param context ...
     * @return 屏幕高度，单位：像素 Height of screen, unit: px
     */
    public static int getScreenHeight(final Context context) {
        return getScreenSize(context)[0];
    }

    /**
     * 获取屏幕分辨率
     * 屏幕尺寸会装进一个仅包含两个元素的一维数组中返回。第一个元素即下标为0的元素表示宽度；第二个元素即下标为1的元素表示高度。宽高尺寸的
     * 单位均为像素。如果获取不到对应的屏幕尺寸则对应的尺寸为-1。
     * Util to get screen size in px
     * Screen size return with int array contain two elements represents width and height. The first element which index
     * is 0 is width, the other is height. Unit of sizes are px. The size will be set to -1 if get with fail.
     *
     * @param context ...
     * @return 屏幕尺寸数组 Array of screen size
     */
    public static int[] getScreenSize(final Context context) {
        if (null != sScreenSize)
            return sScreenSize;
        sScreenSize = new int[2];
        final WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (null == wm) return new int[]{-1, -1};
        final Display display = wm.getDefaultDisplay();
        if (null == display) return new int[]{-1, -1};
        final Point size = new Point();
        display.getSize(size);
        sScreenSize[0] = size.x;
        sScreenSize[1] = size.y;
        return sScreenSize;
    }

    /**
     * 根据DP计算PX
     * Calculate px by dp
     *
     * @param context ...
     * @param dp      DP值 Value of dp
     * @return PX值 Value of px
     */
    public static int dp2px(final Context context, final float dp) {
        final Integer integer = PXS.get(dp);
        int result = null == integer ? 0 : integer;
        if (result == 0) {
            final float scale = context.getResources().getDisplayMetrics().density;
            result = (int) (dp * scale + 0.5f);
            PXS.put(dp, result);
        }
        return result;
    }

    /**
     * 根据PX计算DP
     * Calculate dp by px
     *
     * @param context ...
     * @param px      PX值 Value of px
     * @return DP值 Value of dp
     */
    public static int px2dp(Context context, float px) {
        final Integer integer = DPS.get(px);
        int result = null == integer ? 0 : integer;
        if (result == 0) {
            final float scale = context.getResources().getDisplayMetrics().density;
            result = (int) (px / scale + 0.5f);
            DPS.put(px, result);
        }
        return result;
    }
}