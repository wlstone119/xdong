package com.xdong.admin.service.activiti.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xdong.admin.service.activiti.ActTaskService;
import com.xdong.admin.service.activiti.ISalaryService;
import com.xdong.common.activiti.config.ActivitiConstant;
import com.xdong.dal.activiti.dao.SalaryMapper;
import com.xdong.dal.activiti.domain.Salary;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 审批流程测试表 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
@Service
public class SalaryServiceImpl extends ServiceImpl<SalaryMapper, Salary> implements ISalaryService {

    @Autowired
    private ActTaskService actTaskService;

    @Override
    public boolean batchRemove(String[] ids) {
        return deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public boolean save(Salary salary) {
        salary.setId(UUID.randomUUID().toString().replace("-", ""));
        actTaskService.startProcess(ActivitiConstant.ACTIVITI_SALARY[0], ActivitiConstant.ACTIVITI_SALARY[1],
                                    salary.getId(), salary.getContent(), new HashMap<String, Object>());
        return insert(salary);
    }

    @Override
    public boolean update(Salary salary) {
        Map<String, Object> vars = new HashMap<>(16);
        vars.put("pass", salary.getTaskPass());
        vars.put("title", "");
        actTaskService.complete(salary.getTaskId(), vars);

        return updateById(salary);
    }

}
