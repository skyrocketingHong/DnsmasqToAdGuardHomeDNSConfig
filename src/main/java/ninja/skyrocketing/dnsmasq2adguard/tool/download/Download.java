package ninja.skyrocketing.dnsmasq2adguard.tool.download;

import cn.hutool.http.HttpRequest;

/**
 * @author skyrocketing Hong
 * @date 2020-11-03 003 16:02:58
 * @version 1.0
 */
public class Download {
    public static String downloadPlaintext(String url) {
        System.out.print("开始下载 \"" + url + "\" ");
        String tmp = "";
        boolean tryAgain = false;

        try {
            while("".equals(tmp)) {
                if (tryAgain) {
                    System.out.print("正在重试...");
                }

                tmp = HttpRequest.get(url).execute().body();
                tryAgain = true;
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        System.out.println("✔ 下载完成");
        return tmp;
    }
}
