package com.xdong.admin.service.userrole;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xdong.dal.userrole.SysRoleMapper;
import com.xdong.model.dto.userrole.SysRoleDto;
import com.xdong.model.entity.userrole.SysRoleDo;
import com.xdong.model.entity.userrole.SysRoleMenuDo;
import com.xdong.spi.admin.userrole.ISysRoleMenuService;
import com.xdong.spi.admin.userrole.ISysRoleService;
import com.xdong.spi.admin.userrole.ISysUserRoleService;

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
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleDo> implements ISysRoleService {

    @Autowired
    ISysUserRoleService sysUserRoleService;

    @Autowired
    ISysRoleMenuService sysRoleMenuService;

    @Override
    public List<SysRoleDo> list() {
        return selectByMap(new HashMap<String, Object>(16));
    }

    @Override
    public List<SysRoleDo> list(Long userId) {
        List<Long> rolesIds = sysUserRoleService.listRoleId(userId);
        List<SysRoleDo> roles = list();
        for (SysRoleDo roleDO : roles) {
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
        SysRoleDo role = new SysRoleDo();
        BeanUtils.copyProperties(roleDto, role);
        boolean result = insert(role);
        saveRoleMenu(role.getRoleId(), roleDto.getMenuIds());
        return result;
    }

    @Transactional
    @Override
    public boolean update(SysRoleDto roleDto) {
        SysRoleDo role = new SysRoleDo();
        BeanUtils.copyProperties(roleDto, role);
        boolean result = updateById(role);
        saveRoleMenu(role.getRoleId(), roleDto.getMenuIds());
        return result;
    }

    private boolean saveRoleMenu(Long roleId, List<Long> menuIds) {
        List<SysRoleMenuDo> rms = new ArrayList<SysRoleMenuDo>();
        for (Long menuId : menuIds) {
            SysRoleMenuDo rmDo = new SysRoleMenuDo();
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
