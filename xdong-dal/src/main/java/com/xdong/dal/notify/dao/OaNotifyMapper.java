package com.xdong.dal.notify.dao;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xdong.dal.notify.domain.OaNotifyDto;
import com.xdong.dal.notify.domain.OaNotify;

/**
 * <p>
 * 通知通告 Mapper 接口
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
public interface OaNotifyMapper extends BaseMapper<OaNotify> {
    
    int countDTO(Map<String, Object> map);
    
    List<OaNotifyDto> listDTO(Map<String, Object> map);
    
}
