package com.xdong.spi.admin.userrole;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xdong.model.entity.userrole.SysUserRoleDo;

/**
 * <p>
 * 用户与角色对应关系 服务类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
public interface ISysUserRoleService extends IService<SysUserRoleDo> {
    
    boolean removeByUserId(Long userId);
    
    boolean removeBatchByUserId(Long[] userIds);
    
    List<Long> listRoleId(Long userId);

}
