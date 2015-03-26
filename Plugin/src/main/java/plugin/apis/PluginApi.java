package plugin.apis;

import allinone.android.apis.IpApi;

public class PluginApi implements IPlugin{
    public String parseIP(String str) {
        return new IpApi().parseIP(str);
    }

    public boolean isValidIp(String str) {
        return new IpApi().isValidIP(str);
    }
}
