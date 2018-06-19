package com.xdong.music.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xdong.dal.music.dao.RpSongsDoMapper;
import com.xdong.dal.music.domain.RpSongsDo;
import com.xdong.music.service.IRpSongsService;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 音乐表 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2018-06-18
 */
@Service
public class RpSongsServiceImpl extends ServiceImpl<RpSongsDoMapper, RpSongsDo> implements IRpSongsService {

}
