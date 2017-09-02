package xprinter.xpos.market.myapplication.CoolpadMarket;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import xprinter.xpos.market.myapplication.CoolpadMarket.model.CApk;
import xprinter.xpos.market.myapplication.CoolpadMarket.model.CApkField;
import xprinter.xpos.market.myapplication.CoolpadMarket.model.CTag;
import xprinter.xpos.market.myapplication.CoolpadMarket.model.QueryResult;

/**
 * Created by Administrator on 2017-08-30.
 */

public interface CoolpadMarketService {
    @GET("home/list?count=20")
    Observable<CApk> obtainHomepageApkList(@Query("page") int page);

    @GET("general/detail")
    Observable<CApkField> obtainApkField(@Query("pid") int Id);

    @GET("app/classify/content")
    Observable<CTag> obtainClassify();

    @GET("general/rank?count=20")
    Observable<QueryResult> obtainClassifySection(@Query("key") String tag, @Query("page") int page);

    @FormUrlEncoded
    @POST("admin/update?versionCode=4690068")
    Observable<CApkField> obtainUpdataApkList(@Field("apps") String param);
}
