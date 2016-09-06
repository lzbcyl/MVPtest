package lzb.com.mvptest.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by lzb on 2016/8/16.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface MyPerActivity {

}
