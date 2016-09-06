package lzb.com.mvptest;

import android.app.Activity;
import android.content.Intent;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import lzb.com.mvptest.intervace1.iml.GetMainViewInterface;
import lzb.com.mvptest.intervace1.iml.GetMainViewInterfaceIml;

public class MainActivity extends BaseActivity implements GetMainViewInterface {
    @Bind(R.id.username_et)
    EditText username_et;
    @Bind(R.id.password_et)
    EditText password_et;
    @Bind(R.id.tv)
    TextView tv;
    @Bind(R.id.seekBar)
    SeekBar seekBar;

    //    @Inject
    GetMainViewInterfaceIml mainViewInterfaceIml;

    @OnClick(R.id.login_btn)
    public void loginClick() {
        mainViewInterfaceIml.login();
    }

    @OnClick(R.id.upload_btn)
    public void uploadFile() {
        mainViewInterfaceIml.upload();
    }

    @OnClick(R.id.down_btn)
    public void down() {
        mainViewInterfaceIml.download();
    }

    @OnClick(R.id.down_btn)
    public void cancel() {
        mainViewInterfaceIml.cancel();
    }


    @Override
    public void init() {
        mainViewInterfaceIml = new GetMainViewInterfaceIml(this);
    }

    @Override
    public void initonActivityResult(int requestCode, int resultCode, Intent data) {
        mainViewInterfaceIml.activityResult(requestCode, resultCode, data);
    }

    @Override
    public void initComnent() {

    }

    @Override
    public int initContentView() {
        return R.layout.layout;
    }

    @Override
    public EditText getPassword() {
        return password_et;
    }

    @Override
    public EditText getUsername() {
        return username_et;
    }

    @Override
    public TextView getTextview() {
        return tv;
    }

    @Override
    public SeekBar getSeekBar() {
        return seekBar;
    }

    @Override
    public Activity activity() {
        return this;
    }
}
