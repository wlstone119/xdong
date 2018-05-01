package com.xdong.dal.userrole.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xdong.dal.userrole.domain.SysMenu;

/**
 * <p>
 * 菜单管理 Mapper 接口
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> listMenuByUserId(Long id);

    List<String> listUserPerms(Long id);

}
