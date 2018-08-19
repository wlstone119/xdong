package com.xdong.controller.userrole;

import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.xdong.common.config.Constant;
import com.xdong.common.controller.BaseController;
import com.xdong.common.utils.R;
import com.xdong.model.entity.userrole.SysDeptDo;
import com.xdong.spi.admin.userrole.ISysDeptService;
import com.xdong.spi.admin.userrole.Tree;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类DeptController.java的实现描述：TODO 类实现描述
 * 
 * @author wanglei 2018年4月30日 下午7:55:47
 */
@Controller
@RequestMapping("/system/sysDept")
public class DeptController extends BaseController {

    private String          prefix = "system/dept";
    @Autowired
    private ISysDeptService sysDeptService;

    @GetMapping()
    @RequiresPermissions("system:sysDept:sysDept")
    String dept() {
        return prefix + "/dept";
    }

    @ApiOperation(value = "获取部门列表", notes = "")
    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("system:sysDept:sysDept")
    public List<SysDeptDo> list() {
        Map<String, Object> query = new HashMap<String, Object>(16);
        List<SysDeptDo> sysDeptList = sysDeptService.selectByMap(query);
        return sysDeptList;
    }

    @GetMapping("/add/{pId}")
    @RequiresPermissions("system:sysDept:add")
    String add(@PathVariable("pId") Long pId, Model model) {
        model.addAttribute("pId", pId);
        if (pId == 0) {
            model.addAttribute("pName", "总部门");
        } else {
            model.addAttribute("pName", sysDeptService.selectById(pId).getName());
        }
        return prefix + "/add";
    }

    @GetMapping("/edit/{deptId}")
    @RequiresPermissions("system:sysDept:edit")
    String edit(@PathVariable("deptId") Long deptId, Model model) {
    	SysDeptDo sysDept = sysDeptService.selectById(deptId);
        model.addAttribute("sysDept", sysDept);
        if (Constant.DEPT_ROOT_ID.equals(sysDept.getParentId())) {
            model.addAttribute("parentDeptName", "无");
        } else {
        	SysDeptDo parDept = sysDeptService.selectById(sysDept.getParentId());
            model.addAttribute("parentDeptName", parDept.getName());
        }
        return prefix + "/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("system:sysDept:add")
    public R save(SysDeptDo sysDept) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        if (sysDeptService.insert(sysDept)) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("system:sysDept:edit")
    public R update(SysDeptDo sysDept) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        if (sysDeptService.updateById(sysDept)) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("system:sysDept:remove")
    public R remove(Long deptId) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("parentId", deptId);
        if (sysDeptService.selectByMap(map).size() > 0) {
            return R.error(1, "包含下级部门,不允许修改");
        }
        if (sysDeptService.checkDeptHasUser(deptId)) {
            if (sysDeptService.deleteById(deptId)) {
                return R.ok();
            }
        } else {
            return R.error(1, "部门包含用户,不允许修改");
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("system:sysDept:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] deptIds) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        sysDeptService.deleteBatchIds(Arrays.asList(deptIds));
        return R.ok();
    }

    @GetMapping("/tree")
    @ResponseBody
    public Tree<SysDeptDo> tree() {
        Tree<SysDeptDo> tree = new Tree<SysDeptDo>();
        tree = sysDeptService.getTree();
        return tree;
    }

    @GetMapping("/treeView")
    String treeView() {
        return prefix + "/deptTree";
    }

}
