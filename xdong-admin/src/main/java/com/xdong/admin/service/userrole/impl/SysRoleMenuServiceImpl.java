package com.xdong.admin.service.userrole.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xdong.admin.service.userrole.ISysRoleMenuService;
import com.xdong.dal.userrole.dao.SysRoleMenuMapper;
import com.xdong.dal.userrole.domain.SysRoleMenu;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色与菜单对应关系 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {

    @Override
    public List<Long> listMenuIdByRoleId(Long id) {
        List<Long> menuIds = new ArrayList<Long>();

        EntityWrapper<SysRoleMenu> wrapper = new EntityWrapper<SysRoleMenu>();
        wrapper.eq("role_id", id);
        List<SysRoleMenu> roleMenuList = selectList(wrapper);
        for (SysRoleMenu roleMenu : roleMenuList) {
            menuIds.add(roleMenu.getMenuId());
        }
        return menuIds;
    }

    @Override
    public boolean removeByRoleId(Long roleId) {
        EntityWrapper<SysRoleMenu> wrapper = new EntityWrapper<SysRoleMenu>();
        wrapper.eq("role_id", roleId);
        return delete(wrapper);
    }

}
