package pres.mc.maxwell.library.config;

import android.view.ViewGroup;

import pres.mc.maxwell.library.R;

import static pres.mc.maxwell.library.ZXingScaner.onErrorListener;

/**
 * 存放扫描的配置
 */
public class ScanConfig {

    public static onErrorListener errorListener;

    public static final int DEFAULT_SCAN_LAYOUT_RES = R.layout.activity_capture;

    public static ViewGroup scanLayout;
    public static int scanViewId = R.id.sv_scan;

    //实际扫描的区域，如果不设置幕宽度的居中正方形
    public static int scanLeft;
    public static int scanTop;
    public static int scanWidth;
    public static int scanHeight;

    //自动对焦毫秒
    public static long autoFocusIntervalMs = 1000L;

}
