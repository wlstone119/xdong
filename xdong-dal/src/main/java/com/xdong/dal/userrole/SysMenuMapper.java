package com.xdong.dal.userrole;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xdong.model.entity.userrole.SysMenuDo;

/**
 * <p>
 * 菜单管理 Mapper 接口
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
public interface SysMenuMapper extends BaseMapper<SysMenuDo> {

	List<SysMenuDo> listMenuByUserId(Long id);

	List<String> listUserPerms(Long id);

}
