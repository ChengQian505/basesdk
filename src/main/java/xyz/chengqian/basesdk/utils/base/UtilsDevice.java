package xyz.chengqian.basesdk.utils.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.UUID;

import static android.content.Context.MODE_PRIVATE;

/**
 * @author 程前 created on 2019/3/26.
 * blog: https://blog.csdn.net/ch1406285246
 * content:
 * modifyNote:
 */
public class UtilsDevice {

    public static String getUUID(Context context) {
        SharedPreferences mShare = context.getSharedPreferences("uuid", MODE_PRIVATE);
        String uuid = null;
        if (mShare != null) {
            uuid = mShare.getString("uuid", "");
        }
        if (TextUtils.isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString();
            mShare.edit().putString("uuid", uuid).apply();
        }
        return uuid;
    }
}