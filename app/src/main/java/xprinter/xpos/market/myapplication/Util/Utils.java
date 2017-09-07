package xprinter.xpos.market.myapplication.Util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2017-09-04.
 */

public class Utils {

    /**
     * MD5加密
     * @param byteStr 需要加密的内容
     * @return 返回 byteStr的md5值
     */
    public static String encryptionMD5(byte[] byteStr) {
        MessageDigest messageDigest = null;
        StringBuffer md5StrBuff = new StringBuffer();
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(byteStr);
            byte[] byteArray = messageDigest.digest();
            for (int i = 0; i < byteArray.length; i++) {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
                } else {
                    md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5StrBuff.toString();
    }

    public static void InstallApk(Context applicationContext, String fileUrl) {
        try {
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= 24) { //判读版本是否在7.0以上
                //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
                Uri apkUri = FileProvider.getUriForFile(applicationContext, "xprinter.xpos.market.fileprovider", new File(URI.create(fileUrl)));
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                install.setDataAndType(apkUri, "application/vnd.android.package-archive");
            } else {
                Uri uri = Uri.parse(fileUrl);
                install.setDataAndType(uri, "application/vnd.android.package-archive");
            }
            applicationContext.startActivity(install);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void UninstallApk(Context context, String packagename) {
        Uri uri = Uri.parse("package:" + packagename);//获取删除包名的URI
        Intent i = new Intent();
        i.setAction(Intent.ACTION_DELETE);//设置我们要执行的卸载动作
        i.setData(uri);//设置获取到的URI
        context.startActivity(i);
    }
}
