package pres.mc.maxwell.zxingscanlite;

import android.app.Activity;
import android.os.Bundle;

/**
 * 依附Fragment的Activity
 */
public class TestFragmentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_fragment);

        CaptureFragment captureFragment = new CaptureFragment();
        captureFragment.setActivity(this);
        getFragmentManager().beginTransaction().add(R.id.fl_container, captureFragment).commit();

    }
}
