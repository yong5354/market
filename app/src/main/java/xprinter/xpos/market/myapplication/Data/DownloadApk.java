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
    public long id;
    @ColumnInfo(name = "name")
    public String apkname;
    @ColumnInfo(name = "icon")
    public String iconUrl;
    @ColumnInfo(name = "downloadid")
    public long downloadid;
}
