package xprinter.xpos.market.myapplication.Base.model;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017-08-29.
 */

public interface BaseMarketApi {
    Observable<List<BaseApk>> obtainHomepageApkList(int page);
    Observable<BaseApkField> obtainApkField(int id);
    Observable<List<BaseTag>> obtainTag();
    Observable<List<BaseApk>> obtainQueryResult(String query,int page);
    Observable<List<BaseApkField>> obtainUpdateList(String param);
}
