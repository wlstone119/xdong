package com.xdong.spi.admin.common;

import org.quartz.SchedulerException;

import com.baomidou.mybatisplus.service.IService;
import com.xdong.model.entity.common.SysTaskDo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
public interface ISysTaskService extends IService<SysTaskDo> {

    boolean remove(Long id) throws SchedulerException;

    boolean batchRemove(Long[] ids) throws SchedulerException;

    void initSchedule() throws SchedulerException;

    void changeStatus(Long jobId, String cmd) throws SchedulerException;

    void updateCron(Long jobId) throws SchedulerException;

}
