package com.xdong.crawler.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xdong.dal.crawler.RpSongsSheetDoMapper;
import com.xdong.model.entity.crawler.RpSongsSheetDo;
import com.xdong.spi.crawler.IRpSongsSheetService;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 歌单表 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2018-08-12
 */
@Service
public class RpSongsSheetServiceImpl extends ServiceImpl<RpSongsSheetDoMapper, RpSongsSheetDo> implements IRpSongsSheetService {

}
