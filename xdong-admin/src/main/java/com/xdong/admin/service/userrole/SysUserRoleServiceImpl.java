package com.xdong.admin.service.userrole;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xdong.dal.userrole.SysUserRoleMapper;
import com.xdong.model.entity.userrole.SysUserRoleDo;
import com.xdong.spi.admin.userrole.ISysUserRoleService;

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
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRoleDo> implements ISysUserRoleService {

    @Override
    public boolean removeByUserId(Long userId) {
        EntityWrapper<SysUserRoleDo> wrapper = new EntityWrapper<SysUserRoleDo>();
        wrapper.eq("user_id", userId);
        return delete(wrapper);
    }

    @Override
    public List<Long> listRoleId(Long userId) {
        List<Long> roleIds = new ArrayList<Long>();
        EntityWrapper<SysUserRoleDo> wrapper = new EntityWrapper<SysUserRoleDo>();
        wrapper.eq("user_id", userId);
        List<SysUserRoleDo> roleList = selectList(wrapper);
        for (SysUserRoleDo userRole : roleList) {
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
