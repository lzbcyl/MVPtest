package lzb.com.mvptest.model;

import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * Created by lzb on 2016/8/17.
 */
public class MyRequestBody extends RequestBody {
    private File file;
    private MediaType mediaType;
    private UploadProgressInterface progressInterface;

    /**
     * @param file
     * @param mediaType multipart/form-data
     */
    public MyRequestBody(File file, MediaType mediaType, UploadProgressInterface progressInterface) {
        this.file = file;
        this.mediaType = mediaType;
        this.progressInterface = progressInterface;
    }

    @Override
    public MediaType contentType() {
        return mediaType;
    }

    @Override
    public long contentLength() throws IOException {
        return file.length();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        long uploadLength = file.length();
        FileInputStream fis = new FileInputStream(file);
        byte[] b = new byte[2 * 1024];
        int length = -1;
        int uploadSize = 0;
        Handler handler = new Handler(Looper.getMainLooper());

        try {
            while ((length = fis.read(b)) != -1) {
                sink.write(b, 0, length);
                uploadSize += length;
                handler.post(new MyRunable(100
                        , (int) (((float) uploadSize / (float) uploadLength) * 100)
                        , uploadLength
                        , uploadSize
                        , progressInterface));
            }
            sink.flush();
        } finally {
            fis.close();
        }
    }

    public interface UploadProgressInterface {
        /**
         * @param total         100
         * @param progress      当前进度百分比
         * @param total1        总共大小
         * @param progressTotal 已上传大小
         */
        public void total(int total, int progress, long total1, long progressTotal);
    }
}


class MyRunable implements Runnable {
    int total;
    int progress;
    long total1;
    long progressTotal;
    MyRequestBody.UploadProgressInterface progressInterface;

    public MyRunable(int total, int progress, long total1, long progressTotal, MyRequestBody.UploadProgressInterface progressInterface) {
        this.total = total;
        this.progress = progress;
        this.total1 = total1;
        this.progressTotal = progressTotal;
        this.progressInterface = progressInterface;
    }

    @Override
    public void run() {
        if (null != progressInterface) {
            progressInterface.total(total, progress, total1, progressTotal);
        }
    }
}