package org.cgspine.rxjavademo.serices;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

/**
 * Created by cgspine on 16/1/15.
 */
public class ServersManager {

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
}
