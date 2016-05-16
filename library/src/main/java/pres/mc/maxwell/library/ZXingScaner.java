package pres.mc.maxwell.library;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;

import com.google.zxing.activity.DefaultCaptureActivity;
import com.google.zxing.activity.AbsCaptureActivity;

import pres.mc.maxwell.library.config.ScanConfig;

/**
 * Zxing扫描工具类
 */
public class ZXingScaner {

    private ScanBuilder mScanBuilder;
    private ResultBuilder mResultBuilder;
    private ConfigBuilder mConfigBuilder;

    //用于二维码扫描
    private static final int REQUEST_CODE_SCAN = 0x0000;
    private static final String DECODED_CONTENT_KEY = "codedContent";
    public static final int RESULT_OK = -1;

    private ZXingScaner(ScanBuilder scanBuilder) {
        this.mScanBuilder = scanBuilder;
    }

    public ZXingScaner(ResultBuilder resultBuilder) {
        this.mResultBuilder = resultBuilder;
    }

    public ZXingScaner(ConfigBuilder configBuilder) {
        this.mConfigBuilder = configBuilder;
    }

    /**
     * 获取一个扫描构建器
     */
    public static ScanBuilder scanBuilder(Activity activity) {
        return new ScanBuilder(activity);
    }


    /**
     * 获取一个结果构建器
     */
    public static ResultBuilder resultBuilder(int requestCode, int resultCode, Intent data) {
        return new ResultBuilder(requestCode, resultCode, data);
    }

    /**
     * 获取一个配置构建器
     */
    public static ConfigBuilder configBuilder() {
        return new ConfigBuilder();
    }

    /**
     * 执行扫描
     */
    public void executeScan() {
        if (mScanBuilder == null) {
            return;
        }

        Intent intent = new Intent(mScanBuilder.activity,
                ScanConfig.captureClass != null ?
                        ScanConfig.captureClass :
                        DefaultCaptureActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//防止多次启动
        mScanBuilder.activity.startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    /**
     * 配置
     */
    public void executeConfig() {
        if (mConfigBuilder == null) {
            return;
        }

        //对焦时间
        if (mConfigBuilder.focusInterval > 0) {
            ScanConfig.autoFocusIntervalMs = mConfigBuilder.focusInterval;
        }

        //自定义摄像界面
        if (mConfigBuilder.captureClass != null) {
            ScanConfig.captureClass = mConfigBuilder.captureClass;
        }

        //实际扫描区域设置
        if (mConfigBuilder.rect != null) {
            ScanConfig.scanLeft = mConfigBuilder.rect.left;
            ScanConfig.scanTop = mConfigBuilder.rect.top;
            ScanConfig.scanWidth = mConfigBuilder.rect.width();
            ScanConfig.scanHeight = mConfigBuilder.rect.height();
        }

    }

    /**
     * 执行结果处理
     */
    public void executeResult() {
        if (mResultBuilder == null || mResultBuilder.listener == null) {
            return;
        }

        if (mResultBuilder.requestCode == REQUEST_CODE_SCAN && mResultBuilder.resultCode == RESULT_OK) {
            if (mResultBuilder.data != null) {
                //解码结果
                String content = mResultBuilder.data.getStringExtra(DECODED_CONTENT_KEY);
                mResultBuilder.listener.onGetResultContent(content);
            }
        }
    }

    /**
     * 配置构建器
     */
    public static class ConfigBuilder {

        long focusInterval;
        Rect rect;
        Class<? extends AbsCaptureActivity> captureClass;

        /**
         * 设置摄像头对焦间隔毫秒
         */
        public ConfigBuilder autoFocusInterval(long interval) {
            this.focusInterval = interval;
            return this;
        }

        public ConfigBuilder setCaptureClass(Class<? extends AbsCaptureActivity> captureClass) {
            this.captureClass = captureClass;
            return this;
        }

        public ConfigBuilder scanArea(Rect rect) {
            this.rect = rect;
            return this;
        }

        public void config() {
            new ZXingScaner(this).executeConfig();
        }

        public ScanBuilder buildScanAfterConfig(Activity activity) {
            config();
            return new ScanBuilder(activity);
        }
    }

    /**
     * 扫描构建器
     */
    public static class ScanBuilder {

        Activity activity;

        /**
         * 设置依附的Activity
         */
        public ScanBuilder(Activity activity) {
            this.activity = activity;
        }

        /**
         * 开始扫描二维码
         */
        public void scan() {
            new ZXingScaner(this).executeScan();
        }
    }

    /**
     * 结果构建器
     */
    public static class ResultBuilder {

        int requestCode;
        int resultCode;
        Intent data;

        onGetResultContentListener listener;

        /**
         * 设置请求码，结果码，返回结果数据
         */
        public ResultBuilder(int requestCode, int resultCode, Intent data) {
            this.requestCode = requestCode;
            this.resultCode = resultCode;
            this.data = data;
        }

        /**
         * 结果监听器
         */
        public ResultBuilder resultListener(onGetResultContentListener listener) {
            this.listener = listener;
            return this;
        }

        /**
         * 处理二维码结果
         */
        public void result() {
            new ZXingScaner(this).executeResult();
        }

    }

    /**
     * 获取结果监听器
     */
    public interface onGetResultContentListener {
        /**
         * 获取结果时的操作
         *
         * @param result 二维码文本数据
         */
        void onGetResultContent(String result);
    }

    /**
     * 错误回调监听器
     */
    public interface onErrorListener {
        /**
         * 打开摄像头失败的时候
         */
        void onOpenCameraError();
    }
}
