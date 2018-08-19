package com.xdong.admin.service.userrole;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xdong.admin.service.activiti.util.BuildTree;
import com.xdong.dal.userrole.SysMenuMapper;
import com.xdong.model.entity.userrole.SysMenuDo;
import com.xdong.spi.admin.userrole.ISysMenuService;
import com.xdong.spi.admin.userrole.ISysRoleMenuService;
import com.xdong.spi.admin.userrole.Tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单管理 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuDo> implements ISysMenuService {

    @Autowired
    ISysRoleMenuService sysRoleMenuService;

    @Cacheable
    @Override
    public Tree<SysMenuDo> getSysMenuTree(Long id) {
        List<Tree<SysMenuDo>> trees = new ArrayList<Tree<SysMenuDo>>();
        List<SysMenuDo> SysMenus = baseMapper.listMenuByUserId(id);
        for (SysMenuDo sysSysMenu : SysMenus) {
            Tree<SysMenuDo> tree = new Tree<SysMenuDo>();
            tree.setId(sysSysMenu.getMenuId().toString());
            tree.setParentId(sysSysMenu.getParentId().toString());
            tree.setText(sysSysMenu.getName());
            Map<String, Object> attributes = new HashMap<>(16);
            attributes.put("url", sysSysMenu.getUrl());
            attributes.put("icon", sysSysMenu.getIcon());
            tree.setAttributes(attributes);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<SysMenuDo> t = BuildTree.build(trees);
        return t;
    }

    @Override
    public List<Tree<SysMenuDo>> listMenuTree(Long id) {
        List<Tree<SysMenuDo>> trees = new ArrayList<Tree<SysMenuDo>>();
        List<SysMenuDo> SysMenus = baseMapper.listMenuByUserId(id);
        for (SysMenuDo sysSysMenu : SysMenus) {
            Tree<SysMenuDo> tree = new Tree<SysMenuDo>();
            tree.setId(sysSysMenu.getMenuId().toString());
            tree.setParentId(sysSysMenu.getParentId().toString());
            tree.setText(sysSysMenu.getName());
            Map<String, Object> attributes = new HashMap<>(16);
            attributes.put("url", sysSysMenu.getUrl());
            attributes.put("icon", sysSysMenu.getIcon());
            tree.setAttributes(attributes);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        List<Tree<SysMenuDo>> list = BuildTree.buildList(trees, "0");
        return list;
    }

    @Override
    public Tree<SysMenuDo> getTree() {
        List<Tree<SysMenuDo>> trees = new ArrayList<Tree<SysMenuDo>>();
        List<SysMenuDo> SysMenus = selectByMap(new HashMap<String, Object>(16));
        for (SysMenuDo sysSysMenu : SysMenus) {
            Tree<SysMenuDo> tree = new Tree<SysMenuDo>();
            tree.setId(sysSysMenu.getMenuId().toString());
            tree.setParentId(sysSysMenu.getParentId().toString());
            tree.setText(sysSysMenu.getName());
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<SysMenuDo> t = BuildTree.build(trees);
        return t;
    }

    @Override
    public Tree<SysMenuDo> getTree(Long roleId) {
        // 根据roleId查询权限
        List<SysMenuDo> menus = selectByMap(new HashMap<String, Object>(16));
        List<Long> menuIds = sysRoleMenuService.listMenuIdByRoleId(roleId);
        List<Long> temp = menuIds;
        for (SysMenuDo menu : menus) {
            if (temp.contains(menu.getParentId())) {
                menuIds.remove(menu.getParentId());
            }
        }
        List<Tree<SysMenuDo>> trees = new ArrayList<Tree<SysMenuDo>>();
        List<SysMenuDo> SysMenus = selectByMap(new HashMap<String, Object>(16));
        for (SysMenuDo sysSysMenu : SysMenus) {
            Tree<SysMenuDo> tree = new Tree<SysMenuDo>();
            tree.setId(sysSysMenu.getMenuId().toString());
            tree.setParentId(sysSysMenu.getParentId().toString());
            tree.setText(sysSysMenu.getName());
            Map<String, Object> state = new HashMap<>(16);
            Long menuId = sysSysMenu.getMenuId();
            if (menuIds.contains(menuId)) {
                state.put("selected", true);
            } else {
                state.put("selected", false);
            }
            tree.setState(state);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<SysMenuDo> t = BuildTree.build(trees);
        return t;
    }

    @Override
    public List<SysMenuDo> list() {
        return selectByMap(new HashMap<String, Object>(16));
    }

    @Override
    public Set<String> listPerms(Long userId) {
        List<String> perms = baseMapper.listUserPerms(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotBlank(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

}
