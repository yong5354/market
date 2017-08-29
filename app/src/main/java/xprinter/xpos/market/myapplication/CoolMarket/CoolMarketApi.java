package xprinter.xpos.market.myapplication.CoolMarket;

import android.util.Log;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import xprinter.xpos.market.myapplication.Base.model.BaseApk;
import xprinter.xpos.market.myapplication.Base.model.BaseApkField;
import xprinter.xpos.market.myapplication.Base.model.BaseMarketApi;
import xprinter.xpos.market.myapplication.CoolMarket.model.Apk;
import xprinter.xpos.market.myapplication.CoolMarket.model.ApkField;
import xprinter.xpos.market.myapplication.CoolMarket.model.Constant;

/**
 * Created by Administrator on 2017-08-22.
 */

public class CoolMarketApi implements BaseMarketApi {

    private CoolMarketService mService;

    private static CoolMarketApi instance = null;

    public CoolMarketApi() {
        initService();
    }

    public static CoolMarketApi getInstance() {
        if(instance == null) {
            instance = new CoolMarketApi();
        }
        return instance;
    }

    private void initService() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(new ServiceInterceptor())
                .addInterceptor(logging)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constant.COOLAPK_PREURL)
                .build();

        mService = retrofit.create(CoolMarketService.class);
    }

    public class ServiceInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request r = chain.request();
            HttpUrl url = r.url().newBuilder().addQueryParameter("apikey", Constant.API_KEY).build();
            Request request = r.newBuilder().header("Cookie", "coolapk_did=" + Constant.COOLAPK_DID).url(url).build();
            return chain.proceed(request);
        }
    }


    public Observable<List<BaseApk>> obtainHomepageApkList(int page) {
        return mService.obtainHomepageApkList(page)
                .flatMap(new Function<List<Apk>, ObservableSource<List<BaseApk>>>() {
                    @Override
                    public ObservableSource<List<BaseApk>> apply(@NonNull List<Apk> apks) throws Exception {
                        return Observable.just((List<BaseApk>)(List)apks);
                    }
                });
    }

    public Observable<BaseApkField> obtainApkField(int id) {
        return mService.obtainApkField(id)
                .flatMap(new Function<ApkField, ObservableSource<BaseApkField>>() {
                    @Override
                    public ObservableSource<BaseApkField> apply(@NonNull ApkField apkField) throws Exception {
                        return Observable.just((BaseApkField) apkField);
                    }
                });
    }
}
