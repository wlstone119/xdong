package com.xdong.spi.admin.userrole;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xdong.model.dto.userrole.SysRoleDto;
import com.xdong.model.entity.userrole.SysRoleDo;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
public interface ISysRoleService extends IService<SysRoleDo> {

    boolean save(SysRoleDto role);
    
    boolean update(SysRoleDto role);

    List<SysRoleDo> list();

    List<SysRoleDo> list(Long userId);

    boolean batchremove(List<Long> ids);

}
