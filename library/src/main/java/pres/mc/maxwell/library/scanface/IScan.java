package pres.mc.maxwell.library.scanface;

import android.content.Context;
import android.os.Handler;
import android.view.SurfaceHolder;

import com.google.zxing.camera.CameraManager;

/**
 * 扫描接口
 */
public interface IScan extends SurfaceHolder.Callback {

    void onGetCodeContentResult(String codeContent);

    CameraManager getCameraManager();

    Handler getHandler();

    Context getContext();

}
