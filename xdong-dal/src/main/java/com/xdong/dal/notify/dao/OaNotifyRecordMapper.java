package com.xdong.dal.notify.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xdong.dal.notify.domain.OaNotifyRecord;

/**
 * <p>
 * 通知通告发送记录 Mapper 接口
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
public interface OaNotifyRecordMapper extends BaseMapper<OaNotifyRecord> {

    /**
     * 更改阅读状态
     * 
     * @return
     */
    int changeRead(OaNotifyRecord notifyRecord);
}
