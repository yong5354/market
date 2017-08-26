package xprinter.xpos.market.myapplication.Detail;

import java.util.Observable;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import xprinter.xpos.market.myapplication.CoolMarket.CoolMarketApi;
import xprinter.xpos.market.myapplication.CoolMarket.model.ApkField;

/**
 * Created by Administrator on 2017-08-26.
 */

public class DetailPresenter implements DetailFragmentContract.DetailPresenter {

    @Inject
    CoolMarketApi mMarketApi;

    private DetailFragmentContract.DetailView mView;

    @Inject
    public DetailPresenter(DetailFragmentContract.DetailView v) {
        mView = v;
        mMarketApi = CoolMarketApi.getInstance();
    }

    @Override
    public void start(Object arg) {
        int id = (int) arg;
        io.reactivex.Observable<ApkField> obervable = mMarketApi.obtainApkField(id);
        obervable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<ApkField>() {
                               @Override
                               public void accept(@NonNull ApkField apkField) throws Exception {
                                   mView.updateContent(apkField);
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                mView.updateContent(null);
                            }
                        });
    }

    @Override
    public void stop() {

    }
}
