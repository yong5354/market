package xprinter.xpos.market.myapplication.Base.model;

/**
 * Created by Administrator on 2017-09-07.
 */

public class BaseApkImpl extends BaseApk {

    public int id;
    public String title;
    public String desc;
    public String icon;
    public String apkname;
    public String apksize;
    public String versionname;
    public int versioncode;
    public String updateflag;
    public long downloadcount;
    public float score;
    public String downloadurl;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return desc;
    }

    @Override
    public String getLogo() {
        return icon;
    }

    @Override
    public String getApkName() {
        return apkname;
    }

    @Override
    public String getApkSize() {
        return apksize;
    }

    @Override
    public String getVersionName() {
        return versionname;
    }

    @Override
    public int getVersionCode() {
        return versioncode;
    }

    @Override
    public String getUpdateFlag() {
        return updateflag;
    }

    @Override
    public long getDownloadCount() {
        return downloadcount;
    }

    @Override
    public float getScoreStar() {
        return score;
    }

    @Override
    public String getDownloadUrl() {
        return downloadurl;
    }
}
