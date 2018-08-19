package com.xdong.crawler.common;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * @description
 * @author stone
 * @date 2017年8月22日
 */
public class CrawlerUtil {

    public static Document connectUrl(String url) throws IOException {
        return Jsoup.connect(url).userAgent("Mozilla").get();
    }

    public static void main(String[] args) throws IOException {
        String url = "https://music.163.com/discover/playlist?cat=安静&order=hot&limit=35&offset=1295";
        Document dom = connectUrl(url);
        Element songSheets = dom.getElementById("m-pl-container");
        System.out.println(songSheets);

    }

}
