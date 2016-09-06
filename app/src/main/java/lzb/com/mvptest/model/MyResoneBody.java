package lzb.com.mvptest.model;

import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by lzb on 2016/8/17.
 */
public class MyResoneBody extends ResponseBody {
    ResponseBody responseBody;
    private BufferedSource bufferedSource;
    private long total;
    private DownLoadFileProgressInterface downLoadFileProgressInterface;

    public MyResoneBody(ResponseBody responseBody, DownLoadFileProgressInterface downLoadFileProgressInterface) {
        this.responseBody = responseBody;
        this.downLoadFileProgressInterface = downLoadFileProgressInterface;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (null == bufferedSource) {
            bufferedSource = Okio.buffer(getSource(responseBody.source()));
        }
        return bufferedSource;
    }

    public Source getSource(Source source) {
        return new ForwardingSource(source) {
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                total += bytesRead != -1 ? bytesRead : 0;
                if (downLoadFileProgressInterface != null)
                    downLoadFileProgressInterface.progress(total, contentLength(), bytesRead == -1);

                Log.e("TAG", "--->>" + total);
                Log.e("TAG", "--->>" + contentLength());

                return bytesRead;
            }
        };
    }

    public interface DownLoadFileProgressInterface {
        public void progress(long current, long total, boolean isFinish);
    }
}
