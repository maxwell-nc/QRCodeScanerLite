package pres.mc.maxwell.library.scanface.impl;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.android.CaptureActivityHandler;
import com.google.zxing.camera.CameraManager;

import java.util.Collection;
import java.util.Map;

import pres.mc.maxwell.library.R;
import pres.mc.maxwell.library.scanface.IScan;
import pres.mc.maxwell.library.ui.ScanLayout;

/**
 * 基本的摄像头界面
 */
public abstract class BaseCapFragment extends Fragment implements IScan {

    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private boolean hasSurface;
    private Collection<BarcodeFormat> decodeFormats;
    private Map<DecodeHintType, ?> decodeHints;
    private String characterSet;
    private SurfaceView surfaceView;

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(setContentId(), container, false);
        ScanLayout scanLayout = (ScanLayout) view.findViewById(setScanLayoutId());
        surfaceView = scanLayout.getSurfaceView();

        // 保持Activity处于唤醒状态
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        hasSurface = false;

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // CameraManager必须在这里初始化，而不是在onCreate()中。
        // 这是必须的，因为当我们第一次进入时需要显示帮助页，我们并不想打开Camera,测量屏幕大小
        // 当扫描框的尺寸不正确时会出现bug
        cameraManager = new CameraManager(getActivity().getApplication());

        handler = null;

        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            // activity在paused时但不会stopped,因此surface仍旧存在；
            // surfaceCreated()不会调用，因此在这里初始化camera
            initCamera(surfaceHolder);
        } else {
            // 重置callback，等待surfaceCreated()来初始化camera
            surfaceHolder.addCallback(this);
        }

        decodeFormats = null;
        characterSet = null;
    }

    @Override
    public void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        cameraManager.closeDriver();
        if (!hasSurface) {
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            surfaceHolder.removeCallback(this);
        }
        super.onPause();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    /**
     * 再次扫描
     * 扫描成功后会停止扫描，请调用此方法再次扫描
     */
    public void scanAgain() {
        if (handler != null) {
            Message message = Message.obtain(handler, R.id.restart_preview);
            message.sendToTarget();
        }
    }

    /**
     * 初始化Camera
     */
    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            return;
        }
        try {
            // 打开Camera硬件设备
            cameraManager.openDriver(surfaceHolder);
            // 创建一个handler来打开预览，并抛出一个运行时异常
            if (handler == null) {
                handler = new CaptureActivityHandler(this, decodeFormats,
                        decodeHints, characterSet, cameraManager);
            }
        } catch (Exception e) {
            onFailedToOpenCamera();
        }
    }

    public void onFailedToOpenCamera() {
        //显示底层错误信息并退出应用
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示");
        builder.setMessage("摄像头设备异常，请允许拍摄权限或重启设备！");
        builder.setPositiveButton("确定", new FinishListener(getActivity()));
        builder.setOnCancelListener(new FinishListener(getActivity()));
        builder.show();
    }

    public int setContentId() {
        return R.layout.activity_capture;
    }

    public int setScanLayoutId() {
        return R.id.sv_scan;
    }

    /**
     * 用于在少数情况下退出App的监听
     *
     * @author Sean Owen
     */
    private static final class FinishListener implements DialogInterface.OnClickListener, DialogInterface.OnCancelListener {

        private final Activity activityToFinish;

        public FinishListener(Activity activityToFinish) {
            this.activityToFinish = activityToFinish;
        }

        @Override
        public void onCancel(DialogInterface dialogInterface) {
            run();
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            run();
        }

        private void run() {
            activityToFinish.finish();
        }

    }
}
