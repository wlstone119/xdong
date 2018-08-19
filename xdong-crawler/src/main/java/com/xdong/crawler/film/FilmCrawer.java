package com.xdong.crawler.film;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import com.google.common.io.Files;
import com.xdong.crawler.common.AbstractCrawlerClass;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

public class FilmCrawer extends AbstractCrawlerClass {

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        return true;
    }

    @Override
    public void visit(Page page) {
        log.info(page.getWebURL() + "===" + page.getContentData());
        String timeStr = new Date().getTime() + "";
        String hashedName = UUID.randomUUID() + "_" + timeStr;
        String filename = storageFolder.getAbsolutePath() + "/" + hashedName + ".html";

        try {
            Files.write(page.getContentData(), new File(filename));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        String[] crawlDomains = { "http://www.522yw.mobi/index.html" };
        start(FilmCrawer.class, crawlDomains, null, null, null, null, null);
    }

}
