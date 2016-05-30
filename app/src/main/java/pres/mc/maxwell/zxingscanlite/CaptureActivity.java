package pres.mc.maxwell.zxingscanlite;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

import pres.mc.maxwell.library.scanface.impl.BaseCapActivity;

/**
 * 摄像头界面
 */
public class CaptureActivity extends BaseCapActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Override
    public void onGetCodeContentResult(String codeContent) {
        ((TextView)findViewById(R.id.tv_bar)).setText(codeContent);
        scanAgain();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
