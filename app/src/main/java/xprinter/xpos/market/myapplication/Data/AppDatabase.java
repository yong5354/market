package xprinter.xpos.market.myapplication.Data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by Administrator on 2017-08-29.
 */

@Database(entities = {DownloadApk.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ApkDao apkDao();
}
