package pres.mc.maxwell.zxingscanlite;

import android.app.Activity;
import android.widget.Toast;

import pres.mc.maxwell.library.scanface.impl.BaseCapFragment;

/**
 * 自定义fragment
 */
public class CaptureFragment extends BaseCapFragment {

    @Override
    public void onGetCodeContentResult(String codeContent) {
        Toast.makeText(getActivity(), codeContent, Toast.LENGTH_LONG).show();
        getActivity().finish();
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
