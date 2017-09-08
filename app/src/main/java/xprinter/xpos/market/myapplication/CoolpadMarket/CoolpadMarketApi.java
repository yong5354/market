package xprinter.xpos.market.myapplication.CoolpadMarket;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import xprinter.xpos.market.myapplication.Base.model.BaseApk;
import xprinter.xpos.market.myapplication.Base.model.BaseApkField;
import xprinter.xpos.market.myapplication.Base.model.BaseMarketApi;
import xprinter.xpos.market.myapplication.Base.model.BaseTag;
import xprinter.xpos.market.myapplication.CoolpadMarket.model.CApk;
import xprinter.xpos.market.myapplication.CoolpadMarket.model.CApkField;
import xprinter.xpos.market.myapplication.CoolpadMarket.model.CTag;
import xprinter.xpos.market.myapplication.CoolpadMarket.model.Constant;
import xprinter.xpos.market.myapplication.CoolpadMarket.model.QueryResult;

/**
 * Created by Administrator on 2017-08-30.
 */

public class CoolpadMarketApi implements BaseMarketApi{

    private CoolpadMarketService mService;

    private static CoolpadMarketApi instance = null;

    public CoolpadMarketApi() {
        initService();
    }

    public static CoolpadMarketApi getInstance() {
        if(instance == null) {
            instance = new CoolpadMarketApi();
        }
        return instance;
    }

    private void initService() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        //logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                //.addInterceptor(logging)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constant.COOLPAD_URL)
                .build();

        mService = retrofit.create(CoolpadMarketService.class);
    }


    @Override
    public Observable<List<BaseApk>> obtainHomepageApkList(int page) {
        return mService.obtainHomepageApkList(page)
                .flatMap(new Function<CApk, ObservableSource<List<BaseApk>>>() {
                    @Override
                    public ObservableSource<List<BaseApk>> apply(@NonNull CApk cApk) throws Exception {
                        List<BaseApk> baseapks = (List<BaseApk>)((List)cApk.getContent().getApps().getList());
                        return Observable.just(baseapks);
                    }
                });
    }

    @Override
    public Observable<BaseApkField> obtainApkField(int id) {
        return mService.obtainApkField(id)
                .flatMap(new Function<CApkField, ObservableSource<BaseApkField>>() {
                    @Override
                    public ObservableSource<BaseApkField> apply(@NonNull CApkField cApkField) throws Exception {
                        BaseApkField bf = cApkField.getContent().get(0);
                        return Observable.just(bf);
                    }
                });
    }

    @Override
    public Observable<List<BaseTag>> obtainTag() {
        return mService.obtainClassify()
                .flatMap(new Function<CTag, ObservableSource<List<BaseTag>>>() {
                    @Override
                    public ObservableSource<List<BaseTag>> apply(@NonNull CTag cTag) throws Exception {
                        List<CTag.ContentBean> tag = cTag.getContent();
                        return Observable.just((List<BaseTag>)((List)tag));
                    }
                });
    }

    @Override
    public Observable<List<BaseApk>> obtainQueryResult(String query,int page) {
        return mService.obtainClassifySection(query,page)
                .flatMap(new Function<QueryResult, ObservableSource<List<BaseApk>>>() {
                    @Override
                    public ObservableSource<List<BaseApk>> apply(@NonNull QueryResult queryResult) throws Exception {
                        List<BaseApk> baseapks = (List<BaseApk>)((List)queryResult.getContent().getList());
                        return Observable.just(baseapks);
                    }
                });
    }

    @Override
    public Observable<List<BaseApkField>> obtainUpdateList(String param) {
        return mService.obtainUpdataApkList(param)
                .flatMap(new Function<CApkField, ObservableSource<List<BaseApkField>>>() {
                    @Override
                    public ObservableSource<List<BaseApkField>> apply(@NonNull CApkField cApkField) throws Exception {
                        List<BaseApkField> bf = (List<BaseApkField>)((List)cApkField.getContent());
                        return Observable.just(bf);
                    }
                });
    }
}
