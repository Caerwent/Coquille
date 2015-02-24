package bzh.caerwent.coquille.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by vincent on 13/01/2015.
 */
public class AppUtils {
    private static final String TAG = AppUtils.class.getName();


    private static MessageDigest sDigest;


    public synchronized static String encodeMD5(String stringToEncode) {
        if (sDigest == null) {
            try {
                sDigest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                Log.e(TAG, "Error : No md5 digest", e);
                return null;
            }
        }

        byte messageDigest[] = sDigest.digest(stringToEncode.getBytes());
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < messageDigest.length; i++) {
            hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
        }
        return hexString.toString();
    }

    // =============================================================================
    // Application infos
    // =============================================================================

    private static String mAppVersion;
    private static String mAppRevision;

    /**
     * Get application version code
     *
     * @param aContext
     */
    public static String getApplicationVersion(final Context aContext) {
        if (mAppVersion == null) {
            try {
                PackageInfo pi = aContext.getPackageManager().getPackageInfo(aContext.getPackageName(), 0);
                mAppVersion = pi.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                Log.v(TAG, "Error : No version name in manifest");
            }
        }
        return mAppVersion;
    }

    public static String getApplicationRevision(final Context aContext) {
        if (mAppRevision == null) {
            try {
                PackageInfo pi = aContext.getPackageManager().getPackageInfo(aContext.getPackageName(), 0);
                int codeVersion = pi.versionCode;
                mAppRevision = String.valueOf(codeVersion);
            } catch (PackageManager.NameNotFoundException e) {
                Log.v(TAG, "Error : No version name in manifest");
            }
        }
        return mAppRevision;
    }
}
