package com.xdong.admin.service.userrole.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xdong.admin.service.userrole.ISysUserRoleService;
import com.xdong.dal.userrole.dao.SysUserRoleMapper;
import com.xdong.dal.userrole.domain.SysUserRole;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户与角色对应关系 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Override
    public boolean removeByUserId(Long userId) {
        EntityWrapper<SysUserRole> wrapper = new EntityWrapper<SysUserRole>();
        wrapper.eq("user_id", userId);
        return delete(wrapper);
    }

    @Override
    public List<Long> listRoleId(Long userId) {
        List<Long> roleIds = new ArrayList<Long>();
        EntityWrapper<SysUserRole> wrapper = new EntityWrapper<SysUserRole>();
        wrapper.eq("user_id", userId);
        List<SysUserRole> roleList = selectList(wrapper);
        for (SysUserRole userRole : roleList) {
            roleIds.add(userRole.getRoleId());
        }
        return roleIds;
    }

    @Override
    public boolean removeBatchByUserId(Long[] userIds) {
        for (Long userId : userIds) {
            removeByUserId(userId);
        }
        return true;
    }

}
