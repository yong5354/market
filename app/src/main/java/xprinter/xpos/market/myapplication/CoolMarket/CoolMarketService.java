package xprinter.xpos.market.myapplication.CoolMarket;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import xprinter.xpos.market.myapplication.CoolMarket.model.Apk;

/**
 * Created by Administrator on 2017-08-22.
 */

public interface CoolMarketService {
    @GET("api.php?method=getHomepageApkList&slm=1")
    Observable<List<Apk>> obtainHomepageApkList(@Query("p") int page);

    //@GET("api.php?method=getApkField&slm=1&includeMeta=0")
    //Observable<ApkField> obtainApkField(@Query("id") int Id);

    //@GET("api.php?method=getSearchApkList&slm=1")
    //Observable<List<Apk>> obtainSearchApkList(@Query(value = "q", encoded = true) String query, @Query("p") int page);

    //@GET("api.php?method=getCommentList&slm=1&tclass=apk&subrows=1&sublimit=10")
    //Observable<List<Comment>> obtainCommentList(@Query("tid") int tid, @Query("p") int page);

    //@Headers("Content-Type: application/x-www-form-urlencoded")
    //@POST("api.php?method=getUpgradeVersions")
    //Observable<List<Apk>> obtainUpgradeVersions(@Body String postStr);
}
