package com.xdong.admin.service.notify;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xdong.common.utils.DateUtils;
import com.xdong.common.utils.PageUtils;
import com.xdong.dal.notify.OaNotifyMapper;
import com.xdong.model.dto.notify.OaNotifyDto;
import com.xdong.model.entity.common.SysDictDo;
import com.xdong.model.entity.notify.OaNotifyDo;
import com.xdong.model.entity.notify.OaNotifyRecordDo;
import com.xdong.model.entity.userrole.SysUserDo;
import com.xdong.spi.admin.common.ISysDictService;
import com.xdong.spi.admin.notify.IOaNotifyRecordService;
import com.xdong.spi.admin.notify.IOaNotifyService;
import com.xdong.spi.admin.userrole.ISysUserService;
import com.xdong.spi.admin.userrole.SessionService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 通知通告 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
@Service
public class OaNotifyServiceImpl extends ServiceImpl<OaNotifyMapper, OaNotifyDo> implements IOaNotifyService {

    @Autowired
    private ISysUserService        sysUserService;

    @Autowired
    private ISysDictService        sysDictService;

    @Autowired
    private IOaNotifyRecordService oaNotifyRecordService;

    @Autowired
    private SessionService         sessionService;

    @Autowired
    private SimpMessagingTemplate  template;

    @Override
    public PageUtils list(Page<OaNotifyDo> page) {

        Page<OaNotifyDo> result = selectPage(page);

        // 获取通知类型
        List<SysDictDo> SysDictDo = sysDictService.listByType("oa_notify_type");
        Map<String, String> dictMap = new HashMap<String, String>();
        for (SysDictDo dict : SysDictDo) {
            dictMap.put(dict.getValue(), dict.getName());
        }

        // 处理通知类型名称
        for (OaNotifyDo notifyDO : result.getRecords()) {
            notifyDO.setType(dictMap.get(notifyDO.getType()));
        }

        PageUtils pageUtils = new PageUtils(result.getRecords(), result.getTotal());

        return pageUtils;
    }

    @Override
    public PageUtils selfList(Map<String, Object> map) {

        List<OaNotifyDto> rows = baseMapper.listDTO(map);
        for (OaNotifyDto notifyDTO : rows) {
            notifyDTO.setBefore(DateUtils.getTimeBefore(notifyDTO.getUpdateDate()));
            notifyDTO.setSender(sysUserService.get(notifyDTO.getCreateBy()).getName());
        }
        PageUtils page = new PageUtils(rows, baseMapper.countDTO(map));

        return page;
    }

    @Override
    public OaNotifyDo get(Long id) {
        OaNotifyDo rDO = selectById(id);
        rDO.setType(sysDictService.getName("oa_notify_type", rDO.getType()));
        return rDO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean save(final OaNotifyDto notifyDto) {
        OaNotifyDo notify = new OaNotifyDo();
        BeanUtils.copyProperties(notifyDto, notify);

        notify.setUpdateDate(new Date());
        boolean r = insert(notify);

        // 保存到接受者列表中
        final Long[] userIds = notifyDto.getUserIds();
        Long notifyId = notify.getId();
        List<OaNotifyRecordDo> records = new ArrayList<>();
        for (Long userId : userIds) {
            OaNotifyRecordDo record = new OaNotifyRecordDo();
            record.setNotifyId(notifyId);
            record.setUserId(userId);
            record.setIsRead(0);
            records.add(record);
        }
        oaNotifyRecordService.insertBatch(records);

        // 给在线用户发送通知
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS,
                                                             new LinkedBlockingDeque<Runnable>());
        executor.execute(new Runnable() {

            @Override
            public void run() {
                for (SysUserDo userDO : sessionService.listOnlineUser()) {
                    for (Long userId : userIds) {
                        if (userId.equals(userDO.getUserId())) {
                            template.convertAndSendToUser(userDO.toString(), "/queue/notifications",
                                                          "新消息：" + notifyDto.getTitle());
                        }
                    }
                }
            }
        });
        executor.shutdown();

        return r;
    }

    @Override
    public boolean remove(Long id) {
        oaNotifyRecordService.deleteRecordByNotifyId(id);
        return deleteById(id);
    }

    @Override
    public boolean batchRemove(Long[] ids) {
        for (Long id : ids) {
            remove(id);
        }
        return true;
    }

}
