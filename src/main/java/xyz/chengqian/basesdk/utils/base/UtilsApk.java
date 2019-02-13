package xyz.chengqian.basesdk.utils.base;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * @author 程前 created on 2019/2/11.
 * blog: https://blog.csdn.net/ch1406285246
 * content:
 * modifyNote:
 */
public class UtilsApk {

    public static final String QQ_PACKAGENAME="com.tencent.mobileqq";
    public static final String WX_PACKAGENAME="com.tencent.mm";

    public static boolean isClientAvailable(Context context,String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
