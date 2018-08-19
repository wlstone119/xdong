//package com.xdong.controller.crawler;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.xdong.common.controller.BaseController;
//import com.xdong.crawler.common.ParamVo;
//import com.xdong.crawler.strategy.CrawlerStrategyClient;
//
///**
// * @description spring mvc 爬虫控制器
// * @author stone
// * @date 2017年4月11日
// */
//@Controller
//@RequestMapping(value = "/crawler")
//public class CrawlerController extends BaseController {
//
//    private static Logger         logger = Logger.getLogger(CrawlerController.class);
//
//    private static final String   prefix = "crawler";
//
//    @Autowired
//    private CrawlerStrategyClient crawlerStrategyClient;
//
//    @RequestMapping(value = "/index", method = { RequestMethod.GET, RequestMethod.POST })
//    public ModelAndView execute() {
//        ModelAndView mav = new ModelAndView();
//        mav.setViewName(getUrl(prefix, "crawler"));
//        return mav;
//    }
//
//    @RequestMapping(value = "/get", method = { RequestMethod.GET, RequestMethod.POST })
//    @ResponseBody
//    public Object crawl(ParamVo paramVo) {
//        try {
//            return crawlerStrategyClient.execute(paramVo);
//        } catch (Exception e) {
//            logger.error("爬虫任务调度时出现异常", e);
//            return false;
//        }
//    }
//
//    @RequestMapping(value = "/index", method = RequestMethod.POST)
//    @ResponseBody
//    public boolean index() {
//        try {
//            // indexer.index(true);
//            return true;
//        } catch (Exception e) {
//            logger.error("新闻索引异常", e);
//            return false;
//        }
//    }
//
//    // @RequestMapping(value = "/serach", method = RequestMethod.POST)
//    // @ResponseBody
//    // public List<String> serach(String keyWords) throws Exception {
//    // return searcher.search(keyWords);
//    // }
//}
