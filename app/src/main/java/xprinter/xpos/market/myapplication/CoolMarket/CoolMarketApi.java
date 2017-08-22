package xprinter.xpos.market.myapplication.CoolMarket;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xprinter.xpos.market.myapplication.CoolMarket.model.Constant;

/**
 * Created by Administrator on 2017-08-22.
 */

public class CoolMarketApi {

    private CoolMarketService mService;

    private CoolMarketApi instance = null;

    public CoolMarketApi getInstance() {
        if(instance == null) {
            instance = new CoolMarketApi();
        }
        return instance;
    }

    private CoolMarketApi() {
        initService();
    }

    private void initService() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor(){

                })
        }.build()
        val retrofit = Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.COOLAPK_PREURL)
                .build()

        mService = retrofit.create(CoolapkService::class.java)
    }

    public class ServiceInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            return null;
        }
    }
}
