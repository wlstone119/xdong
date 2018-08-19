package com.xdong.controller.activiti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.plugins.Page;
import com.xdong.common.activiti.utils.ActivitiUtils;
import com.xdong.common.controller.BaseController;
import com.xdong.common.utils.PageUtils;
import com.xdong.common.utils.Query;
import com.xdong.common.utils.R;
import com.xdong.common.utils.ShiroUtils;
import com.xdong.spi.admin.activiti.ISalaryService;
import com.xdong.model.entity.activiti.SalaryDo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 类SalaryController.java的实现描述：TODO 类实现描述
 * 
 * @author wanglei 2018年4月30日 下午7:40:02
 */
@Controller
@RequestMapping("/act/SalaryDo")
public class SalaryController extends BaseController {

    @Autowired
    private ISalaryService salaryService;
    @Autowired
    ActivitiUtils          activitiUtils;

    @GetMapping()
    String SalaryDo() {
        return "activiti/SalaryDo/SalaryDo";
    }

    @ResponseBody
    @GetMapping("/list")
    public PageUtils list(@RequestParam Map<String, Object> params) {

        // 查询列表数据
        Query query = new Query(params);
        Page<SalaryDo> page = new Page<com.xdong.model.entity.activiti.SalaryDo>(query.getPage(), query.getLimit());
        Page<SalaryDo> result = salaryService.selectPage(page.setCondition(convertConditionParam(params)));
        PageUtils pageUtils = new PageUtils(result.getRecords(), result.getTotal());

        return pageUtils;
    }

    @GetMapping("/form")
    String add() {
        return "act/SalaryDo/add";
    }

    @GetMapping("/form/{taskId}")
    String edit(@PathVariable("taskId") String taskId, Model model) {
        SalaryDo SalaryDo = salaryService.selectById(activitiUtils.getBusinessKeyByTaskId(taskId));
        SalaryDo.setTaskId(taskId);
        model.addAttribute("SalaryDo", SalaryDo);
        return "act/SalaryDo/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    public R saveOrUpdate(SalaryDo SalaryDo) {
        SalaryDo.setCreateDate(new Date());
        SalaryDo.setUpdateDate(new Date());
        SalaryDo.setCreateBy(ShiroUtils.getUserId().toString());
        SalaryDo.setUpdateBy(ShiroUtils.getUserId().toString());
        SalaryDo.setDelFlag("1");
        if (salaryService.save(SalaryDo)) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    public R update(SalaryDo SalaryDo) {
        String taskKey = activitiUtils.getTaskByTaskId(SalaryDo.getTaskId()).getTaskDefinitionKey();
        if ("audit2".equals(taskKey)) {
            SalaryDo.setHrText(SalaryDo.getTaskComment());
        } else if ("audit3".equals(taskKey)) {
            SalaryDo.setLeadText(SalaryDo.getTaskComment());
        } else if ("audit4".equals(taskKey)) {
            SalaryDo.setMainLeadText(SalaryDo.getTaskComment());
        } else if ("apply_end".equals(SalaryDo.getTaskComment())) {
            // 流程完成，兑现
        }
        salaryService.update(SalaryDo);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    public R remove(String id) {
        if (salaryService.deleteById(id)) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    public R remove(@RequestParam("ids[]") String[] ids) {
        salaryService.batchRemove(ids);
        return R.ok();
    }

}
