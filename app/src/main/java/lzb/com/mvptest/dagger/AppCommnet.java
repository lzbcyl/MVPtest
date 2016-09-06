package lzb.com.mvptest.dagger;

import dagger.Component;
import lzb.com.mvptest.BaseActivity;
import lzb.com.mvptest.intervace1.iml.GetMainViewInterfaceIml;

/**
 * Created by lzb on 2016/8/16.
 */
@Component(modules = {AppModel.class, DownloadModel.class})
public interface AppCommnet {
    void inject(BaseActivity activity);

    void inject(GetMainViewInterfaceIml activity);

}
