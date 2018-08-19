package com.xdong.spi.admin.userrole;

import com.baomidou.mybatisplus.service.IService;
import com.xdong.model.entity.userrole.SysDeptDo;

/**
 * <p>
 * 部门管理 服务类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
public interface ISysDeptService extends IService<SysDeptDo> {

    Long[] listParentDept();

    Tree<SysDeptDo> getTree();
    
    boolean checkDeptHasUser(Long deptId);

}
