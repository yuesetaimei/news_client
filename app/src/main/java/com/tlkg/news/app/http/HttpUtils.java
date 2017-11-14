package com.tlkg.news.app.http;

import android.content.Context;
import android.support.annotation.NonNull;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.tlkg.news.app.BuildConfig;
import com.tlkg.news.app.NewsClientApplication;
import com.tlkg.news.app.util.PhoneUtil;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wuxiaoqi on 2017/9/22.
 */

public class HttpUtils implements UrlConstant {
    private static HttpUtils instance;
    private Context mContext;
    private boolean mDebug;

    private HttpUtils() {
    }

    public static HttpUtils getInstance() {
        if (instance == null) {
            synchronized (HttpUtils.class) {
                if (instance == null) {
                    instance = new HttpUtils();
                }
            }
        }
        return instance;
    }


    public void init(Context context, boolean debug) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot is null!!!");
        }
        this.mContext = context;
        this.mDebug = debug;
    }

    private Object dongtingHttps;

    public <T> T getTingServer(Class<T> a) {
        if (dongtingHttps == null) {
            synchronized (HttpUtils.class) {
                if (dongtingHttps == null) {
                    dongtingHttps = getBuilder(API_TING).build().create(a);
                }
            }
        }
        return (T) dongtingHttps;
    }

    private Object adpicHttps;

    public <T> T getAdPicServer(Class<T> a) {
        if (adpicHttps == null) {
            synchronized (HttpUtils.class) {
                if (adpicHttps == null) {
                    adpicHttps = getBuilder(API_ADPIC).build().create(a);
                }
            }
        }
        return (T) adpicHttps;
    }

    private Object doubanHttps;

    public <T> T getDoubanServer(Class<T> a) {
        if (doubanHttps == null) {
            synchronized (HttpUtils.class) {
                if (doubanHttps == null) {
                    doubanHttps = getBuilder(API_DAOUBAN).build().create(a);
                }
            }
        }
        return (T) doubanHttps;
    }

    private Object gankHttps;

    public <T> T getGankServer(Class<T> a) {
        if (gankHttps == null) {
            synchronized (HttpUtils.class) {
                if (gankHttps == null) {
                    gankHttps = getBuilder(API_GANK).build().create(a);
                }
            }
        }
        return (T) gankHttps;
    }

    private Object toutiaoHttps;

    public <T> T getToutiaoServer(Class<T> a) {
        if (toutiaoHttps == null) {
            synchronized (HttpUtils.class) {
                if (toutiaoHttps == null) {
                    toutiaoHttps = getBuilder(API_TOUTIAO).build().create(a);
                }
            }
        }
        return (T) toutiaoHttps;
    }

    private volatile static Retrofit retrofit;

    @NonNull
    public static Retrofit getRetrofit() {
        synchronized (HttpUtils.class) {
            if (retrofit == null) {
                // 指定缓存路径,缓存大小 50Mb
                Cache cache = new Cache(new File(NewsClientApplication.getAppContext().getCacheDir(), "HttpCache"),
                        1024 * 1024 * 50);

                // Cookie 持久化
                ClearableCookieJar cookieJar =
                        new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(NewsClientApplication.getAppContext()));

                OkHttpClient.Builder builder = new OkHttpClient.Builder()
                        .cookieJar(cookieJar)
                        .cache(cache)
                        .addInterceptor(cacheControlInterceptor)
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(15, TimeUnit.SECONDS)
                        .writeTimeout(15, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true);

                // Log 拦截器
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    builder.addInterceptor(interceptor);
                }

                retrofit = new Retrofit.Builder()
                        .baseUrl(API_TOUTIAO)
                        .client(builder.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
            }
            return retrofit;
        }
    }

    /**
     * 缓存机制
     * 在响应请求之后在 data/data/<包名>/cache 下建立一个response 文件夹，保持缓存数据。
     * 这样我们就可以在请求的时候，如果判断到没有网络，自动读取缓存的数据。
     * 同样这也可以实现，在我们没有网络的情况下，重新打开App可以浏览的之前显示过的内容。
     * 也就是：判断网络，有网络，则从网络获取，并保存到缓存中，无网络，则从缓存中获取。
     * https://werb.github.io/2016/07/29/%E4%BD%BF%E7%94%A8Retrofit2+OkHttp3%E5%AE%9E%E7%8E%B0%E7%BC%93%E5%AD%98%E5%A4%84%E7%90%86/
     */
    private static final Interceptor cacheControlInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!PhoneUtil.isNetworkConnect(NewsClientApplication.getAppContext())) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }

            Response originalResponse = chain.proceed(request);
            if (PhoneUtil.isNetworkConnect(NewsClientApplication.getAppContext())) {
                // 有网络时 设置缓存为默认值
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma") // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .build();
            } else {
                // 无网络时 设置超时为1周
                int maxStale = 60 * 60 * 24 * 7;
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

    private Retrofit.Builder getBuilder(String requestUrl) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(getOkClient())
                .baseUrl(requestUrl)//设置请求URL
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        return builder;
    }

    public OkHttpClient getOkClient() {
        return getUnsafeOkHttpClient();
    }

    private OkHttpClient getUnsafeOkHttpClient() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[]{};
                }
            }};
            // Install the all-trusting trust manager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
            okBuilder.readTimeout(20, TimeUnit.SECONDS);
            okBuilder.connectTimeout(10, TimeUnit.SECONDS);
            okBuilder.writeTimeout(20, TimeUnit.SECONDS);
//            okBuilder.addInterceptor(new HttpHeadInterceptor());
            okBuilder.addInterceptor(getInterceptor());
            okBuilder.sslSocketFactory(sslSocketFactory);
            okBuilder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return okBuilder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private HttpLoggingInterceptor getInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (mDebug) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // 测试
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE); // 打包
        }
        return interceptor;
    }

}
