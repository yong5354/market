package xprinter.xpos.market.myapplication.Data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Administrator on 2017-08-29.
 */

@Dao
public interface ApkDao {
    @Query("SELECT * from DownloadApk")
    LiveData<List<DownloadApk>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DownloadApk dapk);

    @Delete
    void delete(DownloadApk dapk);
}
