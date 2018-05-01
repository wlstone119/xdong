package com.xdong.admin.service.userrole.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xdong.admin.service.userrole.ISysRoleMenuService;
import com.xdong.admin.service.userrole.ISysRoleService;
import com.xdong.admin.service.userrole.ISysUserRoleService;
import com.xdong.dal.userrole.dao.SysRoleMapper;
import com.xdong.dal.userrole.domain.SysRole;
import com.xdong.dal.userrole.domain.SysRoleMenu;
import com.xdong.dto.SysRoleDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    ISysUserRoleService sysUserRoleService;

    @Autowired
    ISysRoleMenuService sysRoleMenuService;

    @Override
    public List<SysRole> list() {
        return selectByMap(new HashMap<String, Object>(16));
    }

    @Override
    public List<SysRole> list(Long userId) {
        List<Long> rolesIds = sysUserRoleService.listRoleId(userId);
        List<SysRole> roles = list();
        for (SysRole roleDO : roles) {
            roleDO.setRoleSign("false");
            for (Long roleId : rolesIds) {
                if (Objects.equals(roleDO.getRoleId(), roleId)) {
                    roleDO.setRoleSign("true");
                    break;
                }
            }
        }
        return roles;
    }

    @Override
    public boolean batchremove(List<Long> ids) {
        return deleteBatchIds(ids);
    }

    @Transactional
    @Override
    public boolean save(SysRoleDto roleDto) {
        SysRole role = new SysRole();
        BeanUtils.copyProperties(roleDto, role);
        boolean result = insert(role);
        saveRoleMenu(role.getRoleId(), roleDto.getMenuIds());
        return result;
    }

    @Transactional
    @Override
    public boolean update(SysRoleDto roleDto) {
        SysRole role = new SysRole();
        BeanUtils.copyProperties(roleDto, role);
        boolean result = updateById(role);
        saveRoleMenu(role.getRoleId(), roleDto.getMenuIds());
        return result;
    }

    private boolean saveRoleMenu(Long roleId, List<Long> menuIds) {
        List<SysRoleMenu> rms = new ArrayList<SysRoleMenu>();
        for (Long menuId : menuIds) {
            SysRoleMenu rmDo = new SysRoleMenu();
            rmDo.setRoleId(roleId);
            rmDo.setMenuId(menuId);
            rms.add(rmDo);
        }
        sysRoleMenuService.removeByRoleId(roleId);
        if (rms.size() > 0) {
            sysRoleMenuService.insertBatch(rms);
        }
        return true;
    }

}
