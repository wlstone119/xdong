package com.xdong.admin.service.userrole.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xdong.admin.service.userrole.ISysMenuService;
import com.xdong.admin.service.userrole.ISysRoleMenuService;
import com.xdong.common.utils.BuildTree;
import com.xdong.dal.common.domain.Tree;
import com.xdong.dal.userrole.dao.SysMenuMapper;
import com.xdong.dal.userrole.domain.SysMenu;

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
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Autowired
    ISysRoleMenuService sysRoleMenuService;

    @Cacheable
    @Override
    public Tree<SysMenu> getSysMenuTree(Long id) {
        List<Tree<SysMenu>> trees = new ArrayList<Tree<SysMenu>>();
        List<SysMenu> SysMenus = baseMapper.listMenuByUserId(id);
        for (SysMenu sysSysMenu : SysMenus) {
            Tree<SysMenu> tree = new Tree<SysMenu>();
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
        Tree<SysMenu> t = BuildTree.build(trees);
        return t;
    }

    @Override
    public List<Tree<SysMenu>> listMenuTree(Long id) {
        List<Tree<SysMenu>> trees = new ArrayList<Tree<SysMenu>>();
        List<SysMenu> SysMenus = baseMapper.listMenuByUserId(id);
        for (SysMenu sysSysMenu : SysMenus) {
            Tree<SysMenu> tree = new Tree<SysMenu>();
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
        List<Tree<SysMenu>> list = BuildTree.buildList(trees, "0");
        return list;
    }

    @Override
    public Tree<SysMenu> getTree() {
        List<Tree<SysMenu>> trees = new ArrayList<Tree<SysMenu>>();
        List<SysMenu> SysMenus = selectByMap(new HashMap<String, Object>(16));
        for (SysMenu sysSysMenu : SysMenus) {
            Tree<SysMenu> tree = new Tree<SysMenu>();
            tree.setId(sysSysMenu.getMenuId().toString());
            tree.setParentId(sysSysMenu.getParentId().toString());
            tree.setText(sysSysMenu.getName());
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<SysMenu> t = BuildTree.build(trees);
        return t;
    }

    @Override
    public Tree<SysMenu> getTree(Long roleId) {
        // 根据roleId查询权限
        List<SysMenu> menus = selectByMap(new HashMap<String, Object>(16));
        List<Long> menuIds = sysRoleMenuService.listMenuIdByRoleId(roleId);
        List<Long> temp = menuIds;
        for (SysMenu menu : menus) {
            if (temp.contains(menu.getParentId())) {
                menuIds.remove(menu.getParentId());
            }
        }
        List<Tree<SysMenu>> trees = new ArrayList<Tree<SysMenu>>();
        List<SysMenu> SysMenus = selectByMap(new HashMap<String, Object>(16));
        for (SysMenu sysSysMenu : SysMenus) {
            Tree<SysMenu> tree = new Tree<SysMenu>();
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
        Tree<SysMenu> t = BuildTree.build(trees);
        return t;
    }

    @Override
    public List<SysMenu> list() {
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
