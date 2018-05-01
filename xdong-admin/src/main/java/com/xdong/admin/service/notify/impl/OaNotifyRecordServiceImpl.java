package com.xdong.admin.service.notify.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xdong.admin.service.notify.IOaNotifyRecordService;
import com.xdong.dal.notify.dao.OaNotifyRecordMapper;
import com.xdong.dal.notify.domain.OaNotifyRecord;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 通知通告发送记录 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
@Service
public class OaNotifyRecordServiceImpl extends ServiceImpl<OaNotifyRecordMapper, OaNotifyRecord> implements IOaNotifyRecordService {

    @Override
    public int changeRead(OaNotifyRecord notifyRecord) {
        return baseMapper.changeRead(notifyRecord);
    }

    @Override
    public boolean deleteRecordByNotifyId(Long notifyId) {
        EntityWrapper<OaNotifyRecord> wrapper = new EntityWrapper<OaNotifyRecord>();
        wrapper.eq("notify_id", notifyId);
        return delete(wrapper);
    }

}
