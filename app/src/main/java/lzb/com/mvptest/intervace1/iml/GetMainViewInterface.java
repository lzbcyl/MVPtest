package lzb.com.mvptest.intervace1.iml;

import android.app.Activity;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by lzb on 2016/8/16.
 */
public interface GetMainViewInterface {
    public EditText getPassword();

    public EditText getUsername();

    public TextView getTextview();

    public SeekBar getSeekBar();

    public Activity activity();
}
