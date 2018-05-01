package com.xdong.common.utils;

import com.xdong.dal.common.domain.ScheduleJob;
import com.xdong.dal.common.domain.SysTask;

public class ScheduleJobUtils {

    public static ScheduleJob entityToData(SysTask scheduleJobEntity) {
        ScheduleJob scheduleJob = new ScheduleJob();
        scheduleJob.setBeanClass(scheduleJobEntity.getBeanClass());
        scheduleJob.setCronExpression(scheduleJobEntity.getCronExpression());
        scheduleJob.setDescription(scheduleJobEntity.getDescription());
        scheduleJob.setIsConcurrent(scheduleJobEntity.getIsConcurrent());
        scheduleJob.setJobName(scheduleJobEntity.getJobName());
        scheduleJob.setJobGroup(scheduleJobEntity.getJobGroup());
        scheduleJob.setJobStatus(scheduleJobEntity.getJobStatus());
        scheduleJob.setMethodName(scheduleJobEntity.getMethodName());
        scheduleJob.setSpringBean(scheduleJobEntity.getSpringBean());
        return scheduleJob;
    }
}
