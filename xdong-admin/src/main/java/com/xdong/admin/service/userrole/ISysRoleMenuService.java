package com.xdong.admin.service.userrole;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xdong.dal.userrole.domain.SysRoleMenu;

/**
 * <p>
 * 角色与菜单对应关系 服务类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
public interface ISysRoleMenuService extends IService<SysRoleMenu> {

    List<Long> listMenuIdByRoleId(Long id);

    boolean removeByRoleId(Long roleId);

}
