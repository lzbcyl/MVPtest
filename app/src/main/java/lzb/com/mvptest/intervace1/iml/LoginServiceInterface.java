package lzb.com.mvptest.intervace1.iml;


import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lzb on 2016/8/16.
 */
public interface LoginServiceInterface {
    @GET("/rest/user/login")
    Observable<Object> login(@Query("account") String account, @Query("password") String password);

    @POST("/upload")
    @Multipart
    Call<String> uploadFile(@Part MultipartBody.Part file, @PartMap Map<String, String> map);

}
