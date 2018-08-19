package com.xdong.dal.notify;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xdong.model.dto.notify.OaNotifyDto;
import com.xdong.model.entity.notify.OaNotifyDo;

/**
 * <p>
 * 通知通告 Mapper 接口
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
public interface OaNotifyMapper extends BaseMapper<OaNotifyDo> {
    
    int countDTO(Map<String, Object> map);
    
    List<OaNotifyDto> listDTO(Map<String, Object> map);
    
}
