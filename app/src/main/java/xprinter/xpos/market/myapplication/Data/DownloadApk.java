package xprinter.xpos.market.myapplication.Data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Administrator on 2017-08-29.
 */

@Entity
public class DownloadApk {
    @PrimaryKey
    public int id;
    @ColumnInfo(name = "name")
    public String apkname;
    @ColumnInfo(name = "versioncode")
    public int versioncode;
    @ColumnInfo(name = "icon")
    public String iconUrl;
    @ColumnInfo(name = "downloadid")
    public long downloadid;

    //for display
    @ColumnInfo(name = "title")
    public String title;
    @ColumnInfo(name = "versionName")
    public String versionname;
}
