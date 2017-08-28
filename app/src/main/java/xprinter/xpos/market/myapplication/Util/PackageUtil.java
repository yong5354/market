package xprinter.xpos.market.myapplication.Util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Administrator on 2017-08-28.
 */

public class PackageUtil {

    public static int getInstalledVersion(Context context, String packagename) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(packagename,0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

}
