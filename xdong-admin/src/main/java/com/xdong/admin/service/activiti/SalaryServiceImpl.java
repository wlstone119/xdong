package com.xdong.admin.service.activiti;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xdong.common.activiti.config.ActivitiConstant;
import com.xdong.dal.activiti.SalaryMapper;
import com.xdong.model.entity.activiti.SalaryDo;
import com.xdong.spi.admin.activiti.ActTaskService;
import com.xdong.spi.admin.activiti.ISalaryService;

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
public class SalaryServiceImpl extends ServiceImpl<SalaryMapper, SalaryDo> implements ISalaryService {

    @Autowired
    private ActTaskService actTaskService;

    @Override
    public boolean batchRemove(String[] ids) {
        return deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public boolean save(SalaryDo SalaryDo) {
        SalaryDo.setId(UUID.randomUUID().toString().replace("-", ""));
        actTaskService.startProcess(ActivitiConstant.ACTIVITI_SALARY[0], ActivitiConstant.ACTIVITI_SALARY[1],
                                    SalaryDo.getId(), SalaryDo.getContent(), new HashMap<String, Object>());
        return insert(SalaryDo);
    }

    @Override
    public boolean update(SalaryDo SalaryDo) {
        Map<String, Object> vars = new HashMap<>(16);
        vars.put("pass", SalaryDo.getTaskPass());
        vars.put("title", "");
        actTaskService.complete(SalaryDo.getTaskId(), vars);

        return updateById(SalaryDo);
    }

}
