package com.xdong.admin.service.userrole;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xdong.admin.service.activiti.util.BuildTree;
import com.xdong.dal.userrole.SysDeptMapper;
import com.xdong.model.entity.userrole.SysDeptDo;
import com.xdong.spi.admin.userrole.ISysDeptService;
import com.xdong.spi.admin.userrole.Tree;

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
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDeptDo> implements ISysDeptService {

    @Override
    public Long[] listParentDept() {
        List<Long> pDeptIds = new ArrayList<Long>();
        List<SysDeptDo> deptList = selectByMap(new HashMap<String, Object>());
        for (SysDeptDo dept : deptList) {
            pDeptIds.add(dept.getParentId());
        }
        return pDeptIds.toArray(new Long[pDeptIds.size()]);
    }

    @Override
    public Tree<SysDeptDo> getTree() {
        List<Tree<SysDeptDo>> trees = new ArrayList<Tree<SysDeptDo>>();
        List<SysDeptDo> sysDepts = selectByMap(new HashMap<String, Object>(16));
        for (SysDeptDo SysDeptDo : sysDepts) {
            Tree<SysDeptDo> tree = new Tree<SysDeptDo>();
            tree.setId(SysDeptDo.getDeptId().toString());
            tree.setParentId(SysDeptDo.getParentId().toString());
            tree.setText(SysDeptDo.getName());
            Map<String, Object> state = new HashMap<>(16);
            state.put("opened", true);
            tree.setState(state);
            trees.add(tree);
        }

        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<SysDeptDo> t = BuildTree.build(trees);
        return t;
    }

    @Override
    public boolean checkDeptHasUser(Long deptId) {
        EntityWrapper<SysDeptDo> wrapper = new EntityWrapper<SysDeptDo>();
        wrapper.eq("dept_id", deptId);
        int result = selectCount(wrapper);

        return result == 0 ? true : false;
    }
}
