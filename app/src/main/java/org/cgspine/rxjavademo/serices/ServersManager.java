package org.cgspine.rxjavademo.serices;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.cgspine.rxjavademo.Util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import retrofit2.http.Url;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgspine on 16/1/15.
 */
public class ServersManager {

    private final static int IO_BUFFER_SIZE =1024 * 8;
    private static final String BASE_URL = "http://gank.avosapps.com/api/data/";
    private static Retrofit mRetrofit;

    private GankService mGankService;

    private ServersManager(){
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mGankService = mRetrofit.create(GankService.class);
    }

    public static ServersManager getInstance(){
        return InstanceClass.mServersManager;
    }

    private static final class InstanceClass{
        static ServersManager mServersManager = new ServersManager();
    }

    public GankService getGankService(){
        return mGankService;
    }

    public void downloadFromHttpToOutput(String path,OutputStream outputStream){
        HttpURLConnection conn = null;
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try{
            URL url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(conn.getInputStream(),IO_BUFFER_SIZE);
            out = new BufferedOutputStream(outputStream,IO_BUFFER_SIZE);
            int b;
            while ((b=in.read())!=-1){
                out.write(b);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(conn!=null){
                conn.disconnect();
            }
            Util.close(in);
            Util.close(out);
        }
    }

    public Bitmap downLoadImageFromHttp(String path) throws IOException{
        HttpURLConnection conn = null;
        BufferedInputStream in = null;
        Bitmap bitmap = null;
        try{
            URL url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(conn.getInputStream(),IO_BUFFER_SIZE);
            bitmap = BitmapFactory.decodeStream(in);
        }catch (IOException e) {
            throw e;
        }finally {
            if(conn!=null){
                conn.disconnect();
            }
            Util.close(in);
        }
        return bitmap;
    }

    public Observable<Bitmap> downLoadImageObservable(final String url){
        return Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap bitmap = null;
                try {
                    bitmap = downLoadImageFromHttp(url);
                    subscriber.onNext(bitmap);
                } catch (IOException e) {
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        });
    }
}
