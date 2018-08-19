package com.xdong.admin.service.notify;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xdong.dal.notify.OaNotifyRecordMapper;
import com.xdong.model.entity.notify.OaNotifyRecordDo;
import com.xdong.spi.admin.notify.IOaNotifyRecordService;

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
public class OaNotifyRecordServiceImpl extends ServiceImpl<OaNotifyRecordMapper, OaNotifyRecordDo> implements IOaNotifyRecordService {

    @Override
    public int changeRead(OaNotifyRecordDo notifyRecord) {
        return baseMapper.changeRead(notifyRecord);
    }

    @Override
    public boolean deleteRecordByNotifyId(Long notifyId) {
        EntityWrapper<OaNotifyRecordDo> wrapper = new EntityWrapper<OaNotifyRecordDo>();
        wrapper.eq("notify_id", notifyId);
        return delete(wrapper);
    }

}
