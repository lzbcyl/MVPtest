package lzb.com.mvptest.dagger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import lzb.com.mvptest.intervace1.iml.DownloadFileInterface;
import lzb.com.mvptest.intervace1.iml.GetMainViewInterfaceIml;
import lzb.com.mvptest.model.MyResoneBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by lzb on 2016/8/17.
 */
@Module
public class DownloadModel {
    MyResoneBody.DownLoadFileProgressInterface downLoadFileProgressInterface;

    public DownloadModel(MyResoneBody.DownLoadFileProgressInterface downLoadFileProgressInterface) {
        this.downLoadFileProgressInterface = downLoadFileProgressInterface;
    }

    @Provides
    @Named("api")
    public OkHttpClient getOkHttpClicktDown() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(30 * 1000, TimeUnit.SECONDS);
        httpClient.writeTimeout(30 * 1000, TimeUnit.SECONDS);
        httpClient.connectTimeout(30 * 1000, TimeUnit.SECONDS);
//        添加拦截器，自定义ResponseBody，添加下载进度
//        httpClient.networkInterceptors().add(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                okhttp3.Response response = chain.proceed(chain.request());
//                return response.newBuilder().body(new MyResoneBody(response.body(), downLoadFileProgressInterface)).build();
//            }
//        });
        /**
         * lambda表达式
         */
        httpClient.networkInterceptors().add(chain -> {
            okhttp3.Response response = chain.proceed(chain.request());
            return response.newBuilder().body(new MyResoneBody(response.body(), downLoadFileProgressInterface)).build();
        });

        return httpClient.build();
    }

    @Provides
    @Named("api1")
    public Retrofit getRetrofit2(@Named("api") OkHttpClient httpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://github-windows.s3.amazonaws.com")
                .client(httpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }

    @Provides
    public DownloadFileInterface getDownloadFileInterface(@Named("api1") Retrofit retrofit) {
        return retrofit.create(DownloadFileInterface.class);
    }
}
