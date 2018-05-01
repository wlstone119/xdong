package com.xdong.admin.service.notify;

import com.baomidou.mybatisplus.service.IService;
import com.xdong.dal.notify.domain.OaNotifyRecord;

/**
 * <p>
 * 通知通告发送记录 服务类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
public interface IOaNotifyRecordService extends IService<OaNotifyRecord> {

    /**
     * 更改阅读状态
     * 
     * @return
     */
    int changeRead(OaNotifyRecord notifyRecord);
    
    /**
     * 删除某公告记录
     * 
     * @param notifyId
     * @return
     */
    boolean deleteRecordByNotifyId(Long notifyId);

}
