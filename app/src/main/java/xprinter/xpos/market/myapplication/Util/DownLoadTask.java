package xprinter.xpos.market.myapplication.Util;

import android.app.DownloadManager;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.InvalidationTracker;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import org.reactivestreams.Publisher;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import xprinter.xpos.market.myapplication.Base.model.BaseApk;
import xprinter.xpos.market.myapplication.CoolMarket.model.Apk;
import xprinter.xpos.market.myapplication.Data.AppDatabase;
import xprinter.xpos.market.myapplication.Data.DownloadApk;

/**
 * Created by xpos on 2017/3/13.
 * DownloadTask
 */

public class DownLoadTask {

    private final String TAG = "DownLoadTask";

    private Context mContext;
    private AppDatabase mDatabase;
    private DownloadManager mDownloadManager;

    private final Object lock = new Object();
    private boolean mDataSyncing = false;

    private CompositeDisposable mInstallDisposables = new CompositeDisposable();

    private List<BaseApk> mPendingList = new ArrayList<>();

    @Inject
    public DownLoadTask(@ContextType("application") Context context,AppDatabase appdatabase) {
        mContext = context;
        mDownloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        mDatabase = appdatabase;

        initData(); //从数据库读取数据

        IntentFilter i = new IntentFilter();
        i.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        mContext.registerReceiver(downloadReceiver, i);
    }

    public void deinit() {
        try {
            mContext.unregisterReceiver(downloadReceiver);
        }catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        mInstallDisposables.dispose();
        mContext = null;
    }

    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                long dID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1);
                if(getDownloadId(dID) != null) {
                    //TODO 更新数据库filepath
                    CheckInstall(dID,true);
                }
            }
        }
    };

    private void initData() {
        mDataSyncing = true;
        Observable.fromCallable(new Callable<List<DownloadApk>>() {
                    @Override
                    public List<DownloadApk> call() throws Exception {
                        return mDatabase.apkDao().getAll();
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<DownloadApk>>() {
                    @Override
                    public void accept(@NonNull List<DownloadApk> downloadApks) throws Exception {
                        Log.e("FANGUOYONG", "load database : " + downloadApks.size());
                        for (DownloadApk apk : downloadApks) {
                            Apk bapk = new Apk(); //TODO 抽象BaseApk
                            bapk.setId(apk.id);
                            bapk.setApkname(apk.apkname);
                            bapk.setLogo(apk.iconUrl);
                            bapk.setApkversioncode(apk.versioncode + "");
                            bapk.downloadId = apk.downloadid;
                            if (getApk(bapk) == null) {
                                synchronized (lock) {
                                    mPendingList.add(bapk);
                                }
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.e("FANGUOYONG", throwable.getMessage());
                        mDataSyncing = false;
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        mDataSyncing = false;
                        Log.e("FANGUOYONG", "load database done");
                    }
                });


    }

    public long addDownload(BaseApk apk, String url) throws Exception {
        if(!checkDownloadService()) {
            throw new Exception("DownloadService is disable");
        }
        if(mDataSyncing) {
            Toast.makeText(mContext,"正在加载数据，稍后再下载",Toast.LENGTH_SHORT).show();//TODO 未加载完数据时如何处理
            return -1;
        }
        BaseApk tmp = getApk(apk);
        if(tmp != null) {
            Log.e("FANGUOYONG","check Install");
            CheckInstall(tmp.downloadId,false);
            return tmp.downloadId;
        }
        //add to pending
        tmp = apk;
        final DownloadApk data = new DownloadApk();
        data.id = apk.getId();
        data.apkname = apk.getApkName();
        data.iconUrl = apk.getLogo();
        data.versioncode = apk.getVersionCode();
        data.title = apk.getTitle();
        data.versionname = apk.getVersionName();
        data.filepath = "";
        tmp.downloadId = data.downloadid = startDownload(url,tmp.getApkName(),null);
        mPendingList.add(tmp);
        Completable.fromAction(new Action() {
                @Override
                public void run() throws Exception {
                    Log.e("FANGUOYONG","insert data");
                    mDatabase.apkDao().insert(data);
                }
            }).subscribeOn(Schedulers.io())
            .subscribe();
        return tmp.downloadId;
    }

    public void delDownload(Apk apk) {
        mDownloadManager.remove(apk.downloadId);
    }

    /*
     * 开始下载，返回下载id号
     */
    private long startDownload(String uri, String title, String description) {

        DownloadManager.Request req = new DownloadManager.Request(Uri.parse(uri));

        req.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        req.setAllowedOverRoaming(false);
        req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        req.setDestinationInExternalFilesDir(mContext, Environment.DIRECTORY_DOWNLOADS, title);

        // 设置一些基本显示信息
        req.setTitle(title);
        req.setDescription(description);

        return mDownloadManager.enqueue(req);
    }

    /*
    * 根据downloadId查询local_url
    */
    public String getDownloadFileUri(long downloadId) {
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor c = mDownloadManager.query(query);
        if (c != null) {
            try {
                if (c.moveToFirst()) {
                    return c.getString(c.getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_URI));
                }
            } finally {
                c.close();
            }
        }
        return null;
    }

    /*
     * 根据downloadId查询url
     */
    private String getDownloadUrl(long downloadId) {
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor c = mDownloadManager.query(query);
        if (c != null) {
            try {
                if (c.moveToFirst()) {
                    return c.getString(c.getColumnIndexOrThrow(DownloadManager.COLUMN_URI));
                }
            } finally {
                c.close();
            }
        }
        return null;
    }

    /*
     * 根据downloadId查询status
     */
    public int getDownloadStatus(long downloadId) {
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor c = mDownloadManager.query(query);
        if (c != null) {
            try {
                if (c.moveToFirst()) {
                    return c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS));
                }
            } finally {
                c.close();
            }
        }
        return -1;
    }

    /*
     * 根据downloadId查询description,MD5记录在此
     */
    public String getDownloadDescription(long downloadId) {
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor c = mDownloadManager.query(query);
        if (c != null) {
            try {
                if (c.moveToFirst()) {
                    return c.getString(c.getColumnIndexOrThrow(DownloadManager.COLUMN_DESCRIPTION));
                }
            } finally {
                c.close();
            }
        }
        return null;
    }

    /*
     * 检测系统DownloadManager是否可用
     */
    private boolean checkDownloadService() {
        try {
            int state = mContext.getPackageManager().getApplicationEnabledSetting("com.android.providers.downloads");
            if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /*
     * 跳转到DownloadService界面
     */
    public void showDownloadSetting() {
        String packageName = "com.android.providers.downloads";
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + packageName));
        mContext.startActivity(intent);
    }

    private BaseApk getApk(BaseApk apk) {
        for(BaseApk a:mPendingList) {
            if(a.getApkName().equals(apk.getApkName()) && a.getVersionCode() == apk.getVersionCode()) //TODO 可以根据packageid唯一确定
                return a;
        }
        return null;
    }

    private BaseApk getDownloadId(long id) {
        for(BaseApk a:mPendingList) {
            if(a.downloadId == id)
                return a;
        }
        return null;
    }

    public interface OnDownloadStatus {
        void onDownloadStatusChange(String apkname,int progress);
    }

    public Observable<Integer> observerDownloadStatus(final BaseApk apk) {
        return Observable.interval(1, TimeUnit.SECONDS)
                .flatMap(new Function<Long, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(@NonNull Long aLong) throws Exception {
                        return Observable.just(getDownloadStatus(apk.downloadId));
                    }
                })
                .takeUntil(new Predicate<Integer>() {
                    @Override
                    public boolean test(@NonNull Integer integer) throws Exception {
                        if(integer == DownloadManager.STATUS_FAILED || integer == DownloadManager.STATUS_SUCCESSFUL)
                            return true;
                        return false;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void CheckInstall(final long downloadId, final boolean updatedatabase) {
        mInstallDisposables.add(Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                int status = getDownloadStatus(downloadId);
                if(status == DownloadManager.STATUS_SUCCESSFUL) {
                    String filepath = getDownloadFileUri(downloadId);
                    if(updatedatabase) {
                        if (filepath == null)
                            mDatabase.apkDao().updateFilepath(downloadId, "");
                        else {
                            Log.e("FANGUOYONG", "update filepath " + filepath + ",database:" + mDatabase + ",dao:" + mDatabase.apkDao());
                            mDatabase.apkDao().updateFilepath(downloadId, filepath);
                        }
                    }
                    startInstall(getDownloadFileUri(downloadId));
                }
            }
        })
        .subscribeOn(Schedulers.io())
        .subscribe());
    }

    public void startInstall(String file) {
        Utils.InstallApk(mContext,file);
    }

    public static class DownloadStatus {
        private int status; //状态
        private int percent;//下载进度

        public DownloadStatus() {
            status = DownloadManager.STATUS_PENDING;
            percent = 0;
        }
    }
}
