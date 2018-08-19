package com.xdong.crawler.strategy;

import com.xdong.crawler.common.ParamVo;

/**
 * @description 爬虫抽象策略接口，通过url确定用何种策略算法执行爬虫
 * @author stone
 * @date 2017年4月11日
 */
public interface CrawlerStrategyInterface {

    // 爬虫的执行方法体
    public Object execute(ParamVo paramVo);

}
