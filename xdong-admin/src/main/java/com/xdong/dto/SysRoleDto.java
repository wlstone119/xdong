package com.xdong.dto;

import java.util.List;

import com.xdong.dal.userrole.domain.SysRole;

@SuppressWarnings("serial")
public class SysRoleDto extends SysRole {

    private List<Long> menuIds;

    public List<Long> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<Long> menuIds) {
        this.menuIds = menuIds;
    }

}
