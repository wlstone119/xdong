package com.xdong.crawler.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xdong.dal.crawler.RpSongsDoMapper;
import com.xdong.model.entity.crawler.RpSongsDo;
import com.xdong.spi.crawler.IRpSongsService;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 音乐表 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2018-08-12
 */
@Service
public class RpSongsServiceImpl extends ServiceImpl<RpSongsDoMapper, RpSongsDo> implements IRpSongsService {

}
