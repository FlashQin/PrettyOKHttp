package app.zf.okhttp.com.myokhttpdemo;

import android.app.Application;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.tsy.sdk.myokhttp.MyOkHttp;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * ( )Created by ${Ethan_Zeng} on 2017/11/6.
 */

public class BaseAppcalition extends Application{
    private static BaseAppcalition mInstance;
    private MyOkHttp mMyOkHttp;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;




        //持久化存储cookie
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(getApplicationContext()));

        //log拦截器
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        //自定义OkHttp
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .cookieJar(cookieJar)       //设置开启cookie
                .addInterceptor(logging)            //设置开启log
                .build();
        mMyOkHttp = new MyOkHttp(okHttpClient);

        //默认
//        mMyOkHttp = new MyOkHttp();


    }

    public static synchronized BaseAppcalition getInstance() {
        return mInstance;
    }


    public MyOkHttp getMyOkHttp() {
        return mMyOkHttp;
    }


}

