package pres.mc.maxwell.zxingscanlite;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import pres.mc.maxwell.library.ZXingScaner;


public class MainActivity extends Activity {

    private TextView contentText;
    private Button scanBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanBtn = (Button) findViewById(R.id.btn_scan);
        contentText = (TextView) findViewById(R.id.tv_content);

        final ViewGroup scanLayout = (ViewGroup) View.inflate(this, R.layout.activity_scan, null);
        scanLayout.findViewById(R.id.tv_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();//do not use finish();
            }
        });
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ZXingScaner.configBuilder()
                        .setLayout(scanLayout, R.id.sv_scan)
                        .scanArea(new Rect(0, 230, 720, 950))
                        .buildScanAfterConfig(MainActivity.this)
                        .scan();

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ZXingScaner.resultBuilder(requestCode, resultCode, data)
                .resultListener(new ZXingScaner.onGetResultContentListener() {
                    @Override
                    public void onGetResultContent(String result) {
                        contentText.setText(result);
                    }
                })
                .result();

    }
}
