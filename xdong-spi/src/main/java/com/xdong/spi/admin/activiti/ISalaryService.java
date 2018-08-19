package com.xdong.spi.admin.activiti;

import com.baomidou.mybatisplus.service.IService;
import com.xdong.model.entity.activiti.SalaryDo;

/**
 * <p>
 * 审批流程测试表 服务类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
public interface ISalaryService extends IService<SalaryDo> {

    boolean save(SalaryDo salaryDo);

    boolean update(SalaryDo salaryDo);

    boolean batchRemove(String[] ids);

}
