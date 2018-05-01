package com.xdong.admin.service.activiti;

import com.baomidou.mybatisplus.service.IService;
import com.xdong.dal.activiti.domain.Salary;

/**
 * <p>
 * 审批流程测试表 服务类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
public interface ISalaryService extends IService<Salary> {

    boolean save(Salary salary);

    boolean update(Salary salary);

    boolean batchRemove(String[] ids);

}
