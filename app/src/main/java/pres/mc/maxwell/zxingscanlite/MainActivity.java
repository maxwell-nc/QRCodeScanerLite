package pres.mc.maxwell.zxingscanlite;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.activity.AbsCaptureActivity;

import pres.mc.maxwell.library.ZXingScaner;


public class MainActivity extends Activity {

    private TextView contentText;
    private Button defaultBtn;
    private Button customBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        defaultBtn = (Button) findViewById(R.id.btn_default);
        customBtn = (Button) findViewById(R.id.btn_custom);
        contentText = (TextView) findViewById(R.id.tv_content);

        //默认界面
        defaultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ZXingScaner.scanBuilder(MainActivity.this)
                        //.useExistConfig(true)//继承上次的配置，默认为false
                        .resultListener(new ZXingScaner.onGetResultContentListener() {
                            @Override
                            public void onGetResultContent(AbsCaptureActivity captureActivity, String result) {
                                contentText.setText(result);
                                captureActivity.finish();
                            }
                        })
                        .scan();

            }
        });

        //自定义
        customBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ZXingScaner.configBuilder()
                        .autoFocusInterval(1500L)//自动对焦间隔毫秒，默认1000L
                        .setCaptureClass(CaptureActivity.class)//不设置则使用默认界面
                        .scanArea(new Rect(0, 230, 720, 950))//这个是扫描区域，不是Overlay区域
                        .buildScanAfterConfig(MainActivity.this)
                        .scan();//回调写在自定义的Actiivty中的onGetResult

            }
        });


    }

}
