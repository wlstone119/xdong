package com.xdong.admin.service.userrole.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xdong.admin.service.userrole.ISysDeptService;
import com.xdong.common.utils.BuildTree;
import com.xdong.dal.common.domain.Tree;
import com.xdong.dal.userrole.dao.SysDeptMapper;
import com.xdong.dal.userrole.domain.SysDept;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 部门管理 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {

    @Override
    public Long[] listParentDept() {
        List<Long> pDeptIds = new ArrayList<Long>();
        List<SysDept> deptList = selectByMap(new HashMap<String, Object>());
        for (SysDept dept : deptList) {
            pDeptIds.add(dept.getParentId());
        }
        return pDeptIds.toArray(new Long[pDeptIds.size()]);
    }

    @Override
    public Tree<SysDept> getTree() {
        List<Tree<SysDept>> trees = new ArrayList<Tree<SysDept>>();
        List<SysDept> sysDepts = selectByMap(new HashMap<String, Object>(16));
        for (SysDept sysDept : sysDepts) {
            Tree<SysDept> tree = new Tree<SysDept>();
            tree.setId(sysDept.getDeptId().toString());
            tree.setParentId(sysDept.getParentId().toString());
            tree.setText(sysDept.getName());
            Map<String, Object> state = new HashMap<>(16);
            state.put("opened", true);
            tree.setState(state);
            trees.add(tree);
        }

        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<SysDept> t = BuildTree.build(trees);
        return t;
    }

    @Override
    public boolean checkDeptHasUser(Long deptId) {
        EntityWrapper<SysDept> wrapper = new EntityWrapper<SysDept>();
        wrapper.eq("dept_id", deptId);
        int result = selectCount(wrapper);

        return result == 0 ? true : false;
    }
}
