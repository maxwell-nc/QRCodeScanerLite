package pres.mc.maxwell.zxingscanlite;

import android.os.Bundle;
import android.view.View;

import pres.mc.maxwell.library.ui.AbsCustomCaptureActivity;

/**
 * 摄像头界面
 */
public class CaptureActivity extends AbsCustomCaptureActivity {

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        findViewById(R.id.tv_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public int setContentId() {
        return R.layout.activity_scan;//注意这个是app模块的，自定义的
    }

    @Override
    public int setScanLayoutId() {
        return R.id.sv_scan;//注意这个是app模块的，自定义的
    }

}
