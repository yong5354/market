package xprinter.xpos.market.myapplication.Util;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.functions.Action;
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

    private List<BaseApk> mPendingList = new ArrayList<>();

    public DownLoadTask(Context context,AppDatabase appdatabase) {
        mContext = context;
        mDownloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        mDatabase = appdatabase;

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
        mContext = null;
    }

    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                long dID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1);
                if(getDownloadId(dID) != null) {
                    //TODO install here
                }
            }
        }
    };

    public long addDownload(BaseApk apk, String url) throws Exception {
        if(!checkDownloadService()) {
            throw new Exception("DownloadService is disable");
        }
        BaseApk tmp = getApk(apk);
        if(tmp != null)
            return tmp.downloadId;
        //add to pending
        tmp = apk;
        final DownloadApk data = new DownloadApk();
        data.id = apk.getId();
        data.apkname = apk.getApkName();
        data.iconUrl = apk.getLogo();
        tmp.downloadId = data.downloadid = startDownload(url,tmp.getApkName(),null);
        mPendingList.add(tmp);
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
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
            if(a.getApkName().equals(apk.getApkName()) && a.getVersionName().equals(apk.getVersionName()))
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
}
