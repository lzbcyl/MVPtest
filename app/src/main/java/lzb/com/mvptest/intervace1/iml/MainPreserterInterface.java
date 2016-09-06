package lzb.com.mvptest.intervace1.iml;

import android.content.Intent;

/**
 * Created by lzb on 2016/8/16.
 */
public interface MainPreserterInterface {
    public void login();

    public void upload();

    public void download();

    public void cancel();

    public void activityResult(int requestCode, int resultCode, Intent data);
}
