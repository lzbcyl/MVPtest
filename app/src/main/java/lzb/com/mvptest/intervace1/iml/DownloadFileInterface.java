package lzb.com.mvptest.intervace1.iml;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by lzb on 2016/8/17.
 */
public interface DownloadFileInterface {
    /**
     * 下载数据库、资源
     *
     * @return
     */
    @GET("/GitHubSetup.exe")
    Observable<ResponseBody> loadFile();
}