package com.xdong.model.dto.userrole;

import java.util.List;

import com.xdong.model.entity.userrole.SysRoleDo;

@SuppressWarnings("serial")
public class SysRoleDto extends SysRoleDo {

    private List<Long> menuIds;

    public List<Long> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<Long> menuIds) {
        this.menuIds = menuIds;
    }

}
