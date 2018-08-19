package com.xdong.crawler.music;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xdong.common.utils.DateUtil;
import com.xdong.crawler.common.Constant;
import com.xdong.crawler.common.CrawlerUtil;
import com.xdong.crawler.common.ParamVo;
import com.xdong.crawler.strategy.CrawlerStrategyInterface;
import com.xdong.model.entity.crawler.RpSongsDo;
import com.xdong.spi.crawler.IRpSongsService;

/**
 * @description 爬取网易云音乐
 * @author stone
 * @date 2017年4月12日
 */
@Service
public class WyCloudMusicCrawler implements CrawlerStrategyInterface {

    private static Logger   logger    = Logger.getLogger(WyCloudMusicCrawler.class);

    private static String   domainUrl = "";

    @Autowired
    private IRpSongsService rpSongsServiceImpl;

    @Override
    public Object execute(ParamVo paramVo) {
        List<String> resultList = new ArrayList<String>();

        String url = paramVo.getUrl();
        domainUrl = paramVo.getDomainUrl();

        int begin = paramVo.getBegin() <= 0 ? 0 : paramVo.getBegin();
        int end = paramVo.getEnd() <= 0 ? 1 : paramVo.getEnd();
        ExecutorService service = Executors.newCachedThreadPool();

        ArrayList<FutureTask<String>> list = new ArrayList<FutureTask<String>>();
        for (int i = begin; i < end; i++) {
            FutureTask<String> task = (FutureTask<String>) service.submit(new ExecuteTaskCallable(url, (i * 35) + ""));
            list.add(task);
        }

        for (FutureTask<String> task : list) {
            try {
                resultList.add(task.get());
            } catch (InterruptedException | ExecutionException e) {
                logger.error("线程任务执行异常", e);
            }
        }

        return resultList;
    }

    private class ExecuteTaskCallable implements Callable<String> {

        private String url;

        public ExecuteTaskCallable(String url, String offset){
            this.url = modifyWangyiUrl(url, offset);
        }

        private String modifyWangyiUrl(String url, String offset) {
            return (url.contains("offset")) ? url.replaceAll("offset", offset) : url + "&offset=" + offset;
        }

        @Override
        public String call() {
            return getWyMusicByCat(url);
        }

    }

    /**
     * 爬取网易云音乐歌曲：类目 url：http://music.163.com/discover/playlist/?cat=民谣&order=hot&limit=35
     * 
     * @param url
     */
    private String getWyMusicByCat(String url) {
        try {
            Document dom = CrawlerUtil.connectUrl(url);
            Element songSheets = dom.getElementById("m-pl-container");
            if (songSheets != null) {
                for (Element songSheet : songSheets.children()) {
                    loopSongSheet(songSheet);
                }
            }
        } catch (IOException e) {
            logger.error("io exception:", e);
        } catch (Exception e) {
            logger.error(String.format("爬取音乐榜单时出现异常：url %s", url), e);
        }

        return url;
    }

    /**
     * 一个歌单元素循环，获取歌单中所有歌曲写入数据库
     * 
     * @param root
     * @throws IOException
     */
    private void loopSongSheet(Element songSheet) throws IOException {
        if (songSheet.hasAttr("href") && songSheet.hasAttr("title")
            && ("tit f-thide s-fc0".equals(songSheet.attr("class")))) {// <a>标签
            String songUrl = songSheet.attr("href").trim();// 歌单url

            // 歌单链接
            String songSheetUrl = appendUrl(songUrl);

            // 获取歌单下的所有歌曲
            Document songs = CrawlerUtil.connectUrl(songSheetUrl);
            Element songsDiv = songs.getElementById("song-list-pre-cache");
            Elements songSheetDiv = songs.getElementsByClass("cntc");
            Element songsList = songsDiv.getElementsByTag("textarea").get(0);
            if (songsList != null) {
                writeToDB(songsList.html().replaceAll("&quot;", "\""), songSheetDiv.get(0));
            }
        } else {
            if (!songSheet.children().isEmpty()) {
                for (Element element : songSheet.children()) {
                    loopSongSheet(element);
                }
            }
        }
    }

    /**
     * 将得到的歌集下的音乐保存至数据库
     * 
     * @param musicTr
     */
    private void writeToDB(String songJson, Element songSheetDiv) {
        if (StringUtils.isEmpty(songJson)) {
            return;
        }

        RpSongsDo songDo = new RpSongsDo();
        songDo.setcTime(new Date());
        songDo.setmTime(new Date());
        songDo.setmUser("system");
        songDo.setcUser("system");
        songDo.setStatus("valid");
        songDo.setResource(Constant.CRAWLER_RESOURCE_WANGYI);

        // 歌单名称
        Elements songSheetH2 = songSheetDiv.getElementsByTag("h2");
        if (!songSheetH2.isEmpty()) {
            songDo.setSongSheet(songSheetH2.html().replaceAll("&nbsp;", " "));
        }

        // 歌曲类型
        StringBuffer sb = new StringBuffer();
        Elements songType = songSheetDiv.getElementsByClass("u-tag");
        for (Element song : songType) {
            sb.append(" " + song.html());
        }
        songDo.setType(sb.toString());

        String songUrl = "";
        JSONArray songs = JSONArray.parseArray(songJson);
        for (int i = 0; i < songs.size(); i++) {
            JSONObject song = songs.getJSONObject(i);

            songUrl = getSongUrlById(song.getString("id"));
            // 播放器
            songDo.setResourcepath(appendIframe(song.getString("id")));
            songDo.setSongUrl(songUrl);
            songDo.setSongAlbum(song.getJSONObject("album").getString("name"));
            songDo.setSongAlbumPic(song.getJSONObject("album").getString("picUrl"));
            JSONArray array = song.getJSONArray("artists");

            String author = "";
            for (int b = 0; b < array.size(); b++) {
                author += array.getJSONObject(b).getString("name") + " ";
            }
            songDo.setSongAuthor(author);

            String duration = song.getString("duration");
            songDo.setName(song.getString("name"));
            if (StringUtils.isNotEmpty(duration)) {
                songDo.setSongDuration(DateUtil.getMinSecByTotalMill(Long.parseLong(duration)));
            }

            rpSongsServiceImpl.insert(songDo);
        }

    }

    private String appendUrl(String url) {
        return domainUrl + url;
    }

    private String getSongUrlById(String id) {
        return domainUrl + "/song?id=" + id;
    }

    private String appendIframe(String id) {
        return "<iframe frameborder=\"no\" border=\"0\" marginwidth=\"0\" "
               + "marginheight=\"0\" width=330 height=86 src=\"//music.163.com/outchain/player?type=2&id=" + id
               + "&auto=1&height=66\"></iframe>";
    }

}
