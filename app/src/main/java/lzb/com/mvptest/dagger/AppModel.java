package lzb.com.mvptest.dagger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lzb.com.mvptest.intervace1.iml.GetMainViewInterface;
import lzb.com.mvptest.intervace1.iml.GetMainViewInterfaceIml;
import lzb.com.mvptest.intervace1.iml.LoginServiceInterface;
import lzb.com.mvptest.model.MyResoneBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by lzb on 2016/8/16.
 */
@Module
public class AppModel {

    @Provides
    public OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(30 * 1000, TimeUnit.SECONDS);
        httpClient.writeTimeout(30 * 1000, TimeUnit.SECONDS);
        httpClient.connectTimeout(30 * 1000, TimeUnit.SECONDS);
        return httpClient.build();
    }


    @Provides
    public Retrofit getRetrofit(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl("http://192.168.2.185")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        return retrofit;
    }

    @Provides
    @Named("api")
    public Retrofit getRetrofitGson(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.2.185")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;

    }

    @Provides
    @Named("api")
    public LoginServiceInterface getLoginServiceInterface1(@Named("api") Retrofit retrofit) {
        return retrofit.create(LoginServiceInterface.class);
    }


    @Provides
    public LoginServiceInterface getLoginServiceInterface(Retrofit retrofit) {
        return retrofit.create(LoginServiceInterface.class);
    }
}
