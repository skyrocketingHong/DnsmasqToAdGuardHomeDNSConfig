package ninja.skyrocketing.dnsmasq2adguard.tool.generate;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-03 003 16:04:06
 * @Version 1.0
 */
public class Generater {
    public static String generateConfig(String comment, String adguardRule) {
        return comment + "\n" + adguardRule + "\n";
    }
}
