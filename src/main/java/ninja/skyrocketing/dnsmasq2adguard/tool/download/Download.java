package ninja.skyrocketing.dnsmasq2adguard.tool.download;

import cn.hutool.http.HttpRequest;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-03 003 16:02:58
 * @Version 1.0
 */
public class Download {
    public static String DownloadPlaintext(String url) {
        System.out.print("开始下载 \"" + url + "\" ");
        String tmp = "";
        boolean tryAgain = false;

        try {
            while(tmp.equals("")) {
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
