package com.xdong.admin.service.userrole;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xdong.dal.userrole.domain.SysUserRole;

/**
 * <p>
 * 用户与角色对应关系 服务类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
public interface ISysUserRoleService extends IService<SysUserRole> {
    
    boolean removeByUserId(Long userId);
    
    boolean removeBatchByUserId(Long[] userIds);
    
    List<Long> listRoleId(Long userId);

}
