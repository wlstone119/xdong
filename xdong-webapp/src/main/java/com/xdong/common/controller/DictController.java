package com.xdong.common.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xdong.common.config.Constant;
import com.xdong.common.utils.PageUtils;
import com.xdong.common.utils.Query;
import com.xdong.common.utils.R;
import com.xdong.spi.admin.common.ISysDictService;
import com.xdong.model.entity.common.SysDictDo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类DictController.java的实现描述：TODO 类实现描述
 * 
 * @author wanglei 2018年4月30日 下午7:08:45
 */
@Controller
@RequestMapping("/common/sysDict")
public class DictController extends BaseController {

    @Autowired
    private ISysDictService sysDictService;

    @GetMapping()
    @RequiresPermissions("common:sysDict:sysDict")
    String SysDictDo() {
        return "common/sysDict/sysDict";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("common:sysDict:sysDict")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        
        // 查询列表数据
        Query query = new Query(params);
        Page<SysDictDo> page = new Page<com.xdong.model.entity.common.SysDictDo>(query.getPage(), query.getLimit());
        Page<SysDictDo> result = sysDictService.selectPage(page.setCondition(convertConditionParam(params)));
        PageUtils pageUtils = new PageUtils(result.getRecords(), result.getTotal());
        
        return pageUtils;
    }

    @GetMapping("/add")
    @RequiresPermissions("common:sysDict:add")
    String add() {
        return "common/sysDict/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("common:sysDict:edit")
    String edit(@PathVariable("id") Long id, Model model) {
        SysDictDo SysDictDo = sysDictService.selectById(id);
        model.addAttribute("sysDict", SysDictDo);
        return "common/sysDict/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("common:sysDict:add")
    public R save(SysDictDo SysDictDo) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        if (sysDictService.insert(SysDictDo)) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("common:sysDict:edit")
    public R update(SysDictDo SysDictDo) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        sysDictService.updateById(SysDictDo);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("common:sysDict:remove")
    public R remove(Long id) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        if (sysDictService.deleteById(id)) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("common:sysDict:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] ids) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        sysDictService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    @GetMapping("/type")
    @ResponseBody
    public List<SysDictDo> listType() {
        return sysDictService.listType();
    };

    // 类别已经指定增加
    @GetMapping("/add/{type}/{description}")
    @RequiresPermissions("common:sysDict:add")
    String addD(Model model, @PathVariable("type") String type, @PathVariable("description") String description) {
        model.addAttribute("type", type);
        model.addAttribute("description", description);
        return "common/sysDict/add";
    }

    @ResponseBody
    @GetMapping("/list/{type}")
    public List<SysDictDo> listByType(@PathVariable("type") String type) {
        // 查询列表数据
        List<SysDictDo> dictList = sysDictService.listByType(type);
        return dictList;
    }
}
