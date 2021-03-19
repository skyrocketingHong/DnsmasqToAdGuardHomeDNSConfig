package ninja.skyrocketing.dnsmasq2adguard.tool.transform;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-03 003 16:05:39
 * @Version 1.0
 */
public class ToAdguardDns {
    public static String addDnsSingle(String domain, String dns) {
        return "[/" + domain + "/]" + dns;
    }

    public static String addDnsMulti(String domainList, String dns) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[/");
        for (String str : domainList.split("\\n")) {
            if (!str.startsWith("#")) {
                stringBuilder.append(Transform.Dnsmasq2Domain(str)).append("/");
            }
        }
        stringBuilder.append("]").append(dns);
        return stringBuilder.toString();
    }

    public static String domainListAddDns(String domainList, String dns) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : domainList.split("\\n")) {
            if (!str.startsWith("#")) {
                stringBuilder.append(addDnsSingle(Transform.Dnsmasq2Domain(str), dns));
            }
        }
        return stringBuilder.toString();
    }
}
