package ninja.skyrocketing.dnsmasq2adguard;

import cn.hutool.core.io.file.FileWriter;
import ninja.skyrocketing.dnsmasq2adguard.tool.download.Download;
import ninja.skyrocketing.dnsmasq2adguard.tool.generate.Generater;
import ninja.skyrocketing.dnsmasq2adguard.tool.transform.ToAdguardDns;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-03 003 16:01:44
 * @Version 1.0
 */
public class Run {
    public static void main(String[] args) {
        System.out.println("开始生成Adguard规则");

        //指定GFW的DNS和中国域名的DNS
        String gfwDns = "https://dns.twnic.tw/dns-query";
        String chinaDns = "223.5.5.5";

        //中国域名的dnsmasq文件下载链接
        String[] chinaListUrl = new String[] {
                "https://raw.githubusercontent.com/felixonmars/dnsmasq-china-list/master/accelerated-domains.china.conf",
                "https://raw.githubusercontent.com/felixonmars/dnsmasq-china-list/master/apple.china.conf"
        };
        StringBuilder stringBuilder = new StringBuilder();
        for (String url : chinaListUrl) {
            stringBuilder.append(Download.DownloadPlaintext(url));
        }
        String chinaList = stringBuilder.toString();
        String gfwList = Download.DownloadPlaintext("https://cokebar.github.io/gfwlist2dnsmasq/dnsmasq_gfwlist.conf");

        System.out.println("开始转换规则");
        String gfwConfig = ToAdguardDns.AddDnsMulti(gfwList, gfwDns);
        String chinaConfig = ToAdguardDns.AddDnsMulti(chinaList, chinaDns);

        System.out.println("开始写入文件");
        FileWriter writer = new FileWriter("specify.txt");
        //指定默认DNS
        String defaultDns =
                """
                223.5.5.5
                223.6.6.6
                https://dns.twnic.tw/dns-query
                """;
        writer.write(Generater.GenerateConfig("#Default DNS", defaultDns));
        writer.append(Generater.GenerateConfig("#GFW List", gfwConfig));
        writer.append(Generater.GenerateConfig("#China List", chinaConfig));

        System.out.println("生成完成，文件路径为 \"" + writer.getFile().getAbsolutePath() + "\"");
    }
}
