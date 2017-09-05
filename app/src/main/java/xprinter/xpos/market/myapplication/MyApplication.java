package xprinter.xpos.market.myapplication;

import android.app.Application;
import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import xprinter.xpos.market.myapplication.Base.model.BaseApkField;
import xprinter.xpos.market.myapplication.Base.model.BaseMarketApi;
import xprinter.xpos.market.myapplication.Util.Utils;

/**
 * Created by Administrator on 2017-08-18.
 */

public class MyApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Inject
    BaseMarketApi mMarketApi;
    @Inject
    LiveData<List<PackageInfo>> mInstalledApks;
    @Inject
    LiveData<List<BaseApkField>> mUpdatedApks;

    private Disposable dataDisposable;

    @Override
    public void onCreate() {
        super.onCreate();
        initInject();
        getData();
        registerBoardcast();
    }

    private void initInject() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        mApplicationComponent.inject(this);
    }

    /**
     * 获取已安装程序，并检测是否有可用更新
     */
    private void getData() {
        if(dataDisposable != null && !dataDisposable.isDisposed())
            dataDisposable.dispose();
        dataDisposable = Observable.fromCallable(new Callable<List<PackageInfo>>() {
                    @Override
                    public List<PackageInfo> call() throws Exception {
                        return getPackageManager().getInstalledPackages(PackageManager.GET_SIGNATURES);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .flatMap(new Function<List<PackageInfo>, ObservableSource<List<BaseApkField>>>() {
                    @Override
                    public ObservableSource<List<BaseApkField>> apply(@NonNull List<PackageInfo> packageInfos) throws Exception {
                        List<PackageInfo> mApks = new ArrayList<PackageInfo>();
                        StringBuilder sb = new StringBuilder();
                        boolean findApk = false;
                        sb.append("{");
                        for(PackageInfo p : packageInfos) {
                            if((p.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                                Log.e("FANGUOYONG", "packagename:" + p.packageName);
                                if(findApk)
                                    sb.append(",");
                                sb.append("\"").append(p.packageName).append("\":");
                                sb.append("{\"versionCode\":\"").append(p.versionCode).append("\"");
                                sb.append(", \"signMD5\":\"").append(Utils.encryptionMD5(p.signatures[0].toByteArray())).append("\"}");
                                findApk = true;
                                mApks.add(p);
                            }
                        }
                        sb.append("}");
                        //String param = "{\"bjzhou.coolapk.app\":{\"versionCode\":\"2\", \"signMD5\":\"100dfd20942047cf120a6e77dc701d43\"},\"com.fastclean\":{\"versionCode\":\"223\", \"signMD5\":\"a20e43315dc0f43071a37f3a0b1798ae\"},\"za.co.riggaroo.datecountdown\":{\"versionCode\":\"1\", \"signMD5\":\"620b04543809fc394b694fe94678dc9f\"},\"com.wandoujia.phoenix2\":{\"versionCode\":\"12064\", \"signMD5\":\"bf2aae1159354601d9100b052ad0d498\"},\"xprinter.xpos.printtest\":{\"versionCode\":\"1\", \"signMD5\":\"d5d91a9a96552e407bab583eaa842058\"},\"xprinter.xpos.market.myapplication\":{\"versionCode\":\"1\", \"signMD5\":\"620b04543809fc394b694fe94678dc9f\"},\"com.release_ua.appstore\":{\"versionCode\":\"3214\", \"signMD5\":\"ce53356b6d0fb01253e603928f747878\"},\"com.yulong.android.coolmart\":{\"versionCode\":\"4690068\", \"signMD5\":\"dbdb3bed3472b1b3c4ca59becd339f44\"},\"com.mxtech.videoplayer.ad\":{\"versionCode\":\"700000066\", \"signMD5\":\"a1ddc90a68ef0db1b416b297831e0788\"},\"com.wandoujia.phoenix2.usbproxy\":{\"versionCode\":\"6215\", \"signMD5\":\"bf2aae1159354601d9100b052ad0d498\"},\"com.sohu.inputmethod.sogou\":{\"versionCode\":\"660\", \"signMD5\":\"15cd0088e2697091f33a2d97da2ea956\"},\"cn.lt.appstore\":{\"versionCode\":\"40007\", \"signMD5\":\"815299243393c2fd84e771db05d057ef\"},\"xprinter.xpos.printtest_im\":{\"versionCode\":\"1\", \"signMD5\":\"620b04543809fc394b694fe94678dc9f\"},\"com.yingyonghui.market\":{\"versionCode\":\"30060872\", \"signMD5\":\"f45a780b1f1cd64534d04a08b1e7cafc\"},\"com.tencent.qqmusic\":{\"versionCode\":\"679\", \"signMD5\":\"cbd27cd7c861227d013a25b2d10f0799\"},\"com.aspire.mm\":{\"versionCode\":\"10630\", \"signMD5\":\"0a3a6e3aa9360319ed4530a41f5bedff\"},\"cn.goapk.market\":{\"versionCode\":\"6430\", \"signMD5\":\"9657983a526e79d2a28f969b18c993f6\"},\"com.ss.android.article.news\":{\"versionCode\":\"633\", \"signMD5\":\"aea615ab910015038f73c47e45d21466\"},\"com.baidu.appsearch\":{\"versionCode\":\"16793738\", \"signMD5\":\"c2b0b497d0389e6de1505e7fd8f4d539\"},\"com.tencent.android.qqdownloader\":{\"versionCode\":\"7092130\", \"signMD5\":\"a095641b30785f28642708f481603e0b\"}}";
                        ((MutableLiveData)mInstalledApks).postValue(mApks); //更新已安装程序
                        return mMarketApi.obtainUpdateList(sb.toString());
                    }
                })
                .subscribe(new Consumer<List<BaseApkField>>() {
                    @Override
                    public void accept(@NonNull List<BaseApkField> baseApkFields) throws Exception {
                        Log.e("FANGUOYONG", "getupdate package done");
                        ((MutableLiveData)mUpdatedApks).postValue(baseApkFields);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    /**
     * 当有应用安装时，删除数据库中的download数据
     * 当有应用删除时，重新执行getData
     */
    private void registerBoardcast() {
        IntentFilter i = new IntentFilter();
        i.addAction(Intent.ACTION_PACKAGE_ADDED);
        i.addAction(Intent.ACTION_PACKAGE_REMOVED);
        i.addAction(Intent.ACTION_PACKAGE_REPLACED);
        i.addDataScheme("package");
        registerReceiver(mReceiver,i);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(Intent.ACTION_PACKAGE_ADDED) || action.equals(Intent.ACTION_PACKAGE_REPLACED)) {
                //TODO 更新数据库
            }
            getData();
        }
    };

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
