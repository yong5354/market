package xprinter.xpos.market.myapplication;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.pm.PackageInfo;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import xprinter.xpos.market.myapplication.Base.model.BaseApk;
import xprinter.xpos.market.myapplication.Base.model.BaseApkField;
import xprinter.xpos.market.myapplication.Base.model.BaseMarketApi;
import xprinter.xpos.market.myapplication.Data.AppDatabase;
import xprinter.xpos.market.myapplication.Data.DownloadApk;

/**
 * Created by Administrator on 2017-09-04.
 */

public class DownloadViewModel extends ViewModel implements ApplicationComponent.injectable {

    @Inject
    BaseMarketApi mMarketApi;
    @Inject
    AppDatabase mDatabase;
    @Inject
    LiveData<List<PackageInfo>> installedapks;
    @Inject
    LiveData<List<BaseApkField>> updatedapks;

    public LiveData<List<DownloadApk>> mDownloadList = new MutableLiveData<>(); //正在下载列表
    public LiveData<List<PackageInfo>> mInstalledList = new MutableLiveData<>(); //已安装应用列表
    public LiveData<List<BaseApkField>> mUpdateList = new MutableLiveData<>(); //可更新列表

    public DownloadViewModel() {
    }

    @Override
    public void inject(ApplicationComponent component) {
        component.inject(this);
        mDownloadList = mDatabase.apkDao().getAllLive();
        mInstalledList = installedapks;
        mUpdateList = updatedapks;
    }
}
