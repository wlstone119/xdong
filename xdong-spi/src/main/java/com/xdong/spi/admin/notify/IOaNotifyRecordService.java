package com.xdong.spi.admin.notify;

import com.baomidou.mybatisplus.service.IService;
import com.xdong.model.entity.notify.OaNotifyRecordDo;

/**
 * <p>
 * 通知通告发送记录 服务类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
public interface IOaNotifyRecordService extends IService<OaNotifyRecordDo> {

    /**
     * 更改阅读状态
     * 
     * @return
     */
    int changeRead(OaNotifyRecordDo notifyRecord);
    
    /**
     * 删除某公告记录
     * 
     * @param notifyId
     * @return
     */
    boolean deleteRecordByNotifyId(Long notifyId);

}
