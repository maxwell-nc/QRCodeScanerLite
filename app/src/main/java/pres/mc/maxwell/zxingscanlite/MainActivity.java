package pres.mc.maxwell.zxingscanlite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


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

        //Fragment
        defaultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,TestFragmentActivity.class));

            }
        });

        //Activity
        customBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(MainActivity.this,CaptureActivity.class));

            }
        });


    }

}
