package com.xdong.controller.music;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.xdong.common.controller.BaseController;
import com.xdong.model.entity.crawler.RpSongsDo;
import com.xdong.spi.crawler.IRpSongsService;

/**
 * @description 首页音乐板块
 * @author stone
 * @date 2018年1月16日
 */
@Controller
@RequestMapping(value = "/music")
public class MusicHomeController extends BaseController {

    private static Logger       logger = Logger.getLogger(MusicHomeController.class);

    private static final String prefix = "music";

    @Autowired
    private IRpSongsService     rpSongsServiceImpl;

    @RequestMapping(value = "/home", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ModelAndView pageInit(int pageNo, int pageSize) {

        Page<RpSongsDo> page = new Page<RpSongsDo>();
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        Page<RpSongsDo> result = rpSongsServiceImpl.selectPage(page);

        Map<String, List<RpSongsDo>> modelMap = new HashMap<String, List<RpSongsDo>>();
        modelMap.put("songs", result.getRecords());

        ModelAndView mav = new ModelAndView();
        mav.setViewName(getUrl(prefix, "music_home"));
        mav.addAllObjects(modelMap);
        return mav;
    }

    @RequestMapping(value = "/songData", method = { RequestMethod.GET })
    @ResponseBody
    public Object songData(@RequestParam("songId") String songId) {
        return rpSongsServiceImpl.selectById(Long.parseLong(songId));
    }
}
