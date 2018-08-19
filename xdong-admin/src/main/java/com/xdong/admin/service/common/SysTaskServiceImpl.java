package com.xdong.admin.service.common;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xdong.common.config.Constant;
import com.xdong.common.job.ScheduleJob;
import com.xdong.common.quartz.utils.QuartzManager;
import com.xdong.common.utils.ScheduleJobUtils;
import com.xdong.dal.common.SysTaskMapper;
import com.xdong.model.entity.common.SysTaskDo;
import com.xdong.spi.admin.common.ISysTaskService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
@Service
public class SysTaskServiceImpl extends ServiceImpl<SysTaskMapper, SysTaskDo> implements ISysTaskService {

    @Autowired
    QuartzManager quartzManager;

    @Override
    public boolean remove(Long id) throws SchedulerException {
        SysTaskDo scheduleJob = selectById(id);
        quartzManager.deleteJob(ScheduleJobUtils.entityToData(scheduleJob));
        return deleteById(id);
    }

    @Override
    public boolean batchRemove(Long[] ids) throws SchedulerException {
        for (Long id : ids) {
            remove(id);
        }
        return true;
    }

    @Override
    public void initSchedule() throws SchedulerException {
        // 这里获取任务信息数据
        List<SysTaskDo> jobList = selectByMap(new HashMap<String, Object>(16));
        for (SysTaskDo scheduleJob : jobList) {
            if ("1".equals(scheduleJob.getJobStatus())) {
                ScheduleJob job = ScheduleJobUtils.entityToData(scheduleJob);
                quartzManager.addJob(job);
            }

        }
    }

    @Override
    public void changeStatus(Long jobId, String cmd) throws SchedulerException {
        SysTaskDo scheduleJob = selectById(jobId);
        if (scheduleJob == null) {
            return;
        }
        if (Constant.STATUS_RUNNING_STOP.equals(cmd)) {
            quartzManager.deleteJob(ScheduleJobUtils.entityToData(scheduleJob));
            scheduleJob.setJobStatus(ScheduleJob.STATUS_NOT_RUNNING);
        } else {
            if (!Constant.STATUS_RUNNING_START.equals(cmd)) {
            } else {
                scheduleJob.setJobStatus(ScheduleJob.STATUS_RUNNING);
                quartzManager.addJob(ScheduleJobUtils.entityToData(scheduleJob));
            }
        }
        updateById(scheduleJob);
    }

    @Override
    public void updateCron(Long jobId) throws SchedulerException {
        SysTaskDo scheduleJob = selectById(jobId);
        if (scheduleJob == null) {
            return;
        }
        if (ScheduleJob.STATUS_RUNNING.equals(scheduleJob.getJobStatus())) {
            quartzManager.updateJobCron(ScheduleJobUtils.entityToData(scheduleJob));
        }
        updateById(scheduleJob);
    }
}
