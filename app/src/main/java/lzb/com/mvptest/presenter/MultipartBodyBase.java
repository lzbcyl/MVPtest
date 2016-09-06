package lzb.com.mvptest.presenter;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.ByteString;
import okio.Okio;
import okio.Source;

/**
 * Created by lzb on 2016/8/17.
 */
public class MultipartBodyBase extends RequestBody {

    @Override
    public MediaType contentType() {
        return MediaType.parse(MediaType.parse("multipart/form-data") + "; boundary=" + "");
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        sink.buffer().size();
    }

}

