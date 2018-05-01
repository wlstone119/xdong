package com.xdong.admin.service.userrole;

import com.baomidou.mybatisplus.service.IService;
import com.xdong.dal.common.domain.Tree;
import com.xdong.dal.userrole.domain.SysDept;

/**
 * <p>
 * 部门管理 服务类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
public interface ISysDeptService extends IService<SysDept> {

    Long[] listParentDept();

    Tree<SysDept> getTree();
    
    boolean checkDeptHasUser(Long deptId);

}
