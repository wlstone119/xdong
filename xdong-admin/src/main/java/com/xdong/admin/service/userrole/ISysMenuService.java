package com.xdong.admin.service.userrole;

import java.util.List;
import java.util.Set;

import com.baomidou.mybatisplus.service.IService;
import com.xdong.dal.common.domain.Tree;
import com.xdong.dal.userrole.domain.SysMenu;

/**
 * <p>
 * 菜单管理 服务类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
public interface ISysMenuService extends IService<SysMenu> {

    Tree<SysMenu> getSysMenuTree(Long id);

    List<Tree<SysMenu>> listMenuTree(Long id);

    Tree<SysMenu> getTree();

    Tree<SysMenu> getTree(Long id);

    List<SysMenu> list();

    Set<String> listPerms(Long userId);

}
