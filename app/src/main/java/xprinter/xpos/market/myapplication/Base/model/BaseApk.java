package xprinter.xpos.market.myapplication.Base.model;

/**
 * Created by Administrator on 2017-08-29.
 */

public abstract class BaseApk {
    public abstract int getId();
    public abstract String getTitle();
    public abstract String getDescription();
    public abstract String getLogo();
    public abstract String getApkName();
    public abstract String getApkSize();
    public abstract String getVersionName();
    public abstract int getVersionCode();
    public abstract String getUpdateFlag();
    public abstract long getDownloadCount();
    public abstract float getScoreStar();
    public abstract String getDownloadUrl();
    public long downloadId = -1;
}
