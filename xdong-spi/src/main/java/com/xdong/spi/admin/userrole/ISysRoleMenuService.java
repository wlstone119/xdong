package com.xdong.spi.admin.userrole;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xdong.model.entity.userrole.SysRoleMenuDo;

/**
 * <p>
 * 角色与菜单对应关系 服务类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
public interface ISysRoleMenuService extends IService<SysRoleMenuDo> {

    List<Long> listMenuIdByRoleId(Long id);

    boolean removeByRoleId(Long roleId);

}
