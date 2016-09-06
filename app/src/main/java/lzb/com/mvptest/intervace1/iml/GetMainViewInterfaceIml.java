package lzb.com.mvptest.intervace1.iml;

import android.content.Intent;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import lzb.com.mvptest.ShaUtils;
import lzb.com.mvptest.dagger.AppModel;
import lzb.com.mvptest.dagger.DaggerAppCommnet;
import lzb.com.mvptest.dagger.DownloadModel;
import lzb.com.mvptest.model.MyRequestBody;
import lzb.com.mvptest.model.MyResoneBody;
import lzb.com.mvptest.presenter.ImageUtil;
import lzb.com.mvptest.presenter.SystemActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lzb on 2016/8/16.
 */
public class GetMainViewInterfaceIml implements MainPreserterInterface, MyRequestBody.UploadProgressInterface, MyResoneBody.DownLoadFileProgressInterface {
    //    @Inject
    public GetMainViewInterface viewInterface;
    @Inject
    LoginServiceInterface loginServiceInterface;

    @Inject
    @Named("api")
    LoginServiceInterface loginServiceInterface1;

    @Inject
    DownloadFileInterface downloadFileInterface;
    Subscription subscription;

    public GetMainViewInterfaceIml(GetMainViewInterface viewInterface) {
        this.viewInterface = viewInterface;
        DaggerAppCommnet.builder().appModel(new AppModel()).downloadModel(new DownloadModel(this)).build().inject(this);
    }

    @Override
    public void login() {
        String username = viewInterface.getUsername().getText().toString();
        String password = viewInterface.getPassword().getText().toString();
        loginServiceInterface1.login(username, ShaUtils.SHA256Encrypt(password))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(o -> viewInterface.getTextview().setText(o.toString())
                        , e -> viewInterface.getTextview().setText(e.toString()));
    }

    @Override
    public void upload() {
        SystemActivity.FromPhoto(viewInterface.activity(), 1);
    }

    Call<ResponseBody> call;

    @Override
    public void download() {
        downloadFileInterface.loadFile().subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(responseBody -> {
                    return responseBody.byteStream();
                })
                .observeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(is -> {
                    viewInterface.getTextview().setText("--下载成功--");
//                    try {
//                            FileOutputStream fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory().getPath()));
//                            BufferedInputStream bos = new BufferedInputStream(is);
//                            fos.write(bos.read());
//                            fos.flush();
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                }, e -> viewInterface.getTextview().setText("--Throwable--" + e.toString()));


    }

    @Override
    public void cancel() {
        if (subscription != null)
            subscription.unsubscribe();
    }

    @Override
    public void activityResult(int requestCode, int resultCode, Intent data) {
        viewInterface.getTextview().setText(ImageUtil.UriToPath(viewInterface.activity(), data));
        String path = ImageUtil.UriToPath(viewInterface.activity(), data);
        File file = new File(path);
        MyRequestBody myRequestBody = new MyRequestBody(file, MediaType.parse("multipart/form-data"), this);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), myRequestBody);
        Map<String, String> map1 = new HashMap<>();
        map1.put("type", "picture");
        loginServiceInterface
                .uploadFile(part, map1)
                .enqueue(new Callback<String>() {

                             @Override
                             public void onResponse(Call<String> call, Response<String> response) {
                                 viewInterface.getTextview().setText("--onResponse--" + response.code() + response.body() + call.request().toString() + call.request().body().toString());

                             }

                             @Override
                             public void onFailure(Call<String> call, Throwable t) {
                                 viewInterface.getTextview().setText("--vonFailure--" + t.toString() + call.request().body() + call.toString());
                             }
                         }

                );
    }

    @Override
    public void total(int total, int progress, long total1, long progressTotal) {
        viewInterface.getSeekBar().setProgress(progress);
    }

    @Override
    public void progress(long current, long total, boolean isFinish) {
        viewInterface.getSeekBar().setProgress(
                (int) (((float) current / (float) total) * 100));
    }
}

