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
        //开启代理
        System.setProperty("http.proxySet", "true");
        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "1080");

        System.out.println("开始生成Adguard规则");

        //指定GFW的DNS和中国域名的DNS
        //使用Google DNS
        String gfwDns = "tls://dns.google";
        //使用默认DNS
        String chinaDns = "#";

        //中国域名的dnsmasq文件下载链接
        String[] chinaListUrl = new String[] {
                "https://raw.githubusercontent.com/felixonmars/dnsmasq-china-list/master/accelerated-domains.china.conf",
                "https://raw.githubusercontent.com/felixonmars/dnsmasq-china-list/master/apple.china.conf",
                "https://raw.githubusercontent.com/felixonmars/dnsmasq-china-list/master/google.china.conf"
        };
        StringBuilder stringBuilder = new StringBuilder();
        for (String url : chinaListUrl) {
            stringBuilder.append(Download.downloadPlaintext(url));
        }
        String chinaList = stringBuilder.toString();
        String gfwList = Download.downloadPlaintext("https://cokebar.github.io/gfwlist2dnsmasq/dnsmasq_gfwlist.conf");

        System.out.println("开始转换规则");
        String gfwConfig = ToAdguardDns.addDnsMulti(gfwList, gfwDns);
        String chinaConfig = ToAdguardDns.addDnsMulti(chinaList, chinaDns);

        System.out.println("开始写入文件");
        FileWriter writer = new FileWriter("specify.txt");
        //指定默认DNS
        String defaultDns =
                """
                tls://dot.pub
                tls://dns.alidns.com
                tls://dns.google
                tls://cloudflare-dns.com
                https://dns.233py.com/dns-query
                """;
        writer.write(Generater.generateConfig("#Default DNS", defaultDns));
        writer.append(Generater.generateConfig("#GFW List", gfwConfig));
        writer.append(Generater.generateConfig("#China List", chinaConfig));

        System.out.println("生成完成，文件路径为 \"" + writer.getFile().getAbsolutePath() + "\"");
    }
}
