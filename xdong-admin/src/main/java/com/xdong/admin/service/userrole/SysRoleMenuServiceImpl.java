package com.xdong.admin.service.userrole;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xdong.dal.userrole.SysRoleMenuMapper;
import com.xdong.model.entity.userrole.SysRoleMenuDo;
import com.xdong.spi.admin.userrole.ISysRoleMenuService;

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
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenuDo> implements ISysRoleMenuService {

    @Override
    public List<Long> listMenuIdByRoleId(Long id) {
        List<Long> menuIds = new ArrayList<Long>();

        EntityWrapper<SysRoleMenuDo> wrapper = new EntityWrapper<SysRoleMenuDo>();
        wrapper.eq("role_id", id);
        List<SysRoleMenuDo> roleMenuList = selectList(wrapper);
        for (SysRoleMenuDo roleMenu : roleMenuList) {
            menuIds.add(roleMenu.getMenuId());
        }
        return menuIds;
    }

    @Override
    public boolean removeByRoleId(Long roleId) {
        EntityWrapper<SysRoleMenuDo> wrapper = new EntityWrapper<SysRoleMenuDo>();
        wrapper.eq("role_id", roleId);
        return delete(wrapper);
    }

}
