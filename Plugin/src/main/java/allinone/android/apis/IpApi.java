package allinone.android.apis;

import android.text.TextUtils;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/3/26.
 */
public class IpApi {
    public String parseIP(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        Pattern pattern = Pattern.compile("\\d+\\.\\d+\\.\\d+\\.\\d+");
        Matcher matcher = pattern.matcher(str);
        int i = 0;
        while (matcher.find()) {
            String ip = matcher.group(0);

            Log.d("IpApi", "ip[" + i++ + "]" + ip);

            if (isValidIP(ip)) {
                return ip;
            }
        }

        return null;
    }

    public boolean isValidIP(String ip) {
        String ips[] = TextUtils.split(ip, "\\.");
        if (ips == null || ips.length != 4) {
            return false;
        }
        for (String s : ips) {
            if (!isValidIPNumber(s)) {
                return false;
            }
        }

        return true;
    }

    private boolean isValidIPNumber(String number) {
        if (number.length() > 3) {
            return false;
        }

        if (number.charAt(0) == '0' && number.length() != 1) {
            return false;
        }

        int n = Integer.valueOf(number);
        if (n >= 255 || n < 0) {
            return false;
        }

        return true;
    }
}
