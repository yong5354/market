package xprinter.xpos.market.myapplication.CoolpadMarket;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import xprinter.xpos.market.myapplication.CoolpadMarket.model.CApk;
import xprinter.xpos.market.myapplication.CoolpadMarket.model.CApkField;
import xprinter.xpos.market.myapplication.CoolpadMarket.model.CTag;

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

    @GET("general/rank")
    Observable<List<CApk>> obtainClassifySection(@Query("key") String tag,@Query("page") int page);
}
