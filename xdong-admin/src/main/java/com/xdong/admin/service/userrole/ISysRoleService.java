package com.xdong.admin.service.userrole;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xdong.dal.userrole.domain.SysRole;
import com.xdong.dto.SysRoleDto;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
public interface ISysRoleService extends IService<SysRole> {

    boolean save(SysRoleDto role);
    
    boolean update(SysRoleDto role);

    List<SysRole> list();

    List<SysRole> list(Long userId);

    boolean batchremove(List<Long> ids);

}
