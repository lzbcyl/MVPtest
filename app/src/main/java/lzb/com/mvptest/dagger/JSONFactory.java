package lzb.com.mvptest.dagger;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by lzb on 2016/8/16.
 */
public class JSONFactory extends Converter.Factory {
}

final class JSONfactoryBydy<T> implements Converter<RequestBody, T> {
    JSONfactoryBydy() {

    }

    @Override
    public T convert(RequestBody value) throws IOException {
        return (T) value;
    }

}
