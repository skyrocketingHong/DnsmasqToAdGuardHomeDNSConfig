package ninja.skyrocketing.dnsmasq2adguard.tool.transform;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-03 003 16:05:12
 * @Version 1.0
 */
public class Transform {
    public static String RemoveFront(String str) {
        return str.replaceFirst("^server=/", "");
    }

    public static String RemoveEnd(String str) {
        return str.replaceFirst("/(\\d{1,3}\\.){3}\\d{1,3}(#\\d{1,5})?$", "");
    }

    public static String Dnsmasq2Domain(String str) {
        return RemoveEnd(RemoveFront(str));
    }
}
