package lzb.com.mvptest;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import lzb.com.mvptest.dagger.AppModel;
import lzb.com.mvptest.dagger.DaggerAppCommnet;
import lzb.com.mvptest.dagger.DownloadModel;

/**
 * Created by lzb on 2016/8/16.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initContentView());
        DaggerAppCommnet.builder().appModel(new AppModel()).downloadModel(new DownloadModel(null)).build().inject(this);
        ButterKnife.bind(this);
        init();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initonActivityResult(requestCode, resultCode, data);
    }

    public abstract void init();

    public abstract void initonActivityResult(int requestCode, int resultCode, Intent data);


    public abstract void initComnent();

    public abstract int initContentView();

}
