package xprinter.xpos.market.myapplication.Result;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import xprinter.xpos.market.myapplication.ApplicationComponent;
import xprinter.xpos.market.myapplication.Base.model.BaseApk;
import xprinter.xpos.market.myapplication.Base.model.BaseMarketApi;

/**
 * Created by Administrator on 2017-09-01.
 */

public class QueryViewModel extends ViewModel implements ApplicationComponent.injectable {

    @Inject
    BaseMarketApi mMarketApi;

    String mQuery;

    private int page = 1;

    private MutableLiveData<List<BaseApk>> mApkList = new MutableLiveData<>();
    private MutableLiveData<Integer> mStatus = new MutableLiveData<>(); //0-loadcomplete,1-refreshing,2-stop

    public MutableLiveData<List<BaseApk>> getApkList() {
        return mApkList;
    }

    public MutableLiveData<Integer> getStatus() {
        return mStatus;
    }

    public void InitPage() {
        page = 1;
    }

    public void setQuery(String query) {
        mQuery = query;
    }

    public void getQueryResult(String query) {
        if(mQuery == null || !mQuery.equals(query)) {
            page = 1;
        }
        mQuery = query;
        mStatus.setValue(1);
        Observable<List<BaseApk>> observable = mMarketApi.obtainQueryResult(query,page);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<BaseApk>>() {
                    @Override
                    public void accept(@NonNull List<BaseApk> apks) throws Exception {
                        if(page == 1) { //第一页，直接赋值
                            mApkList.setValue(apks);
                            page++;
                        } else {
                            if(apks == null || apks.isEmpty()) {
                                mStatus.setValue(0); //load complete
                                return;
                            }
                            List<BaseApk> tmp = mApkList.getValue();
                            tmp.addAll(apks);
                            mApkList.setValue(tmp);
                            page++;
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        mStatus.setValue(2);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        mStatus.setValue(2);
                    }
                });
    }

    public void getQueryResult() {
        if(mQuery == null) {
            return;
        }
        mStatus.setValue(1);
        Observable<List<BaseApk>> observable = mMarketApi.obtainQueryResult(mQuery,page);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<BaseApk>>() {
                    @Override
                    public void accept(@NonNull List<BaseApk> apks) throws Exception {
                        if(page == 1) { //第一页，直接赋值
                            mApkList.setValue(apks);
                            page++;
                        } else {
                            if(apks == null || apks.isEmpty()) {
                                mStatus.setValue(0); //load complete
                                return;
                            }
                            List<BaseApk> tmp = mApkList.getValue();
                            tmp.addAll(apks);
                            mApkList.setValue(tmp);
                            page++;
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        mStatus.setValue(2);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        mStatus.setValue(2);
                    }
                });
    }

    @Override
    public void inject(ApplicationComponent component) {
        component.inject(this);
    }
}
