package com.xdong.dto;

import java.util.List;

import com.xdong.dal.userrole.domain.SysUser;

public class SysUserDto extends SysUser {

    private static final long serialVersionUID = -8951772929784766387L;

    /**
     * 角色
     */
    private List<Long>        roleIds;

    /**
     * 部门名称
     */
    private String            deptName;

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

}
