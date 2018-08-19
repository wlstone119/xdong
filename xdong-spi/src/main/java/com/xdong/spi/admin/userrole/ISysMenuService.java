package com.xdong.spi.admin.userrole;

import java.util.List;
import java.util.Set;

import com.baomidou.mybatisplus.service.IService;
import com.xdong.model.entity.userrole.SysMenuDo;

/**
 * <p>
 * 菜单管理 服务类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
public interface ISysMenuService extends IService<SysMenuDo> {

    Tree<SysMenuDo> getSysMenuTree(Long id);

    List<Tree<SysMenuDo>> listMenuTree(Long id);

    Tree<SysMenuDo> getTree();

    Tree<SysMenuDo> getTree(Long id);

    List<SysMenuDo> list();

    Set<String> listPerms(Long userId);

}
