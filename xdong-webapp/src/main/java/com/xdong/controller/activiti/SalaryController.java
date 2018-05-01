package com.xdong.controller.activiti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.plugins.Page;
import com.xdong.admin.service.activiti.ISalaryService;
import com.xdong.common.activiti.utils.ActivitiUtils;
import com.xdong.common.controller.BaseController;
import com.xdong.common.utils.PageUtils;
import com.xdong.common.utils.Query;
import com.xdong.common.utils.R;
import com.xdong.common.utils.ShiroUtils;
import com.xdong.dal.common.domain.SysDict;
import com.xdong.dal.common.domain.SysLog;
import com.xdong.dal.activiti.domain.Salary;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 类SalaryController.java的实现描述：TODO 类实现描述
 * 
 * @author wanglei 2018年4月30日 下午7:40:02
 */
@Controller
@RequestMapping("/act/salary")
public class SalaryController extends BaseController {

    @Autowired
    private ISalaryService salaryService;
    @Autowired
    ActivitiUtils          activitiUtils;

    @GetMapping()
    String Salary() {
        return "activiti/salary/salary";
    }

    @ResponseBody
    @GetMapping("/list")
    public PageUtils list(@RequestParam Map<String, Object> params) {

        // 查询列表数据
        Query query = new Query(params);
        Page<Salary> page = new Page<Salary>(query.getPage(), query.getLimit());
        Page<Salary> result = salaryService.selectPage(page.setCondition(convertConditionParam(params)));
        PageUtils pageUtils = new PageUtils(result.getRecords(), result.getTotal());

        return pageUtils;
    }

    @GetMapping("/form")
    String add() {
        return "act/salary/add";
    }

    @GetMapping("/form/{taskId}")
    String edit(@PathVariable("taskId") String taskId, Model model) {
        Salary salary = salaryService.selectById(activitiUtils.getBusinessKeyByTaskId(taskId));
        salary.setTaskId(taskId);
        model.addAttribute("salary", salary);
        return "act/salary/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    public R saveOrUpdate(Salary salary) {
        salary.setCreateDate(new Date());
        salary.setUpdateDate(new Date());
        salary.setCreateBy(ShiroUtils.getUserId().toString());
        salary.setUpdateBy(ShiroUtils.getUserId().toString());
        salary.setDelFlag("1");
        if (salaryService.save(salary)) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    public R update(com.xdong.dal.activiti.domain.Salary salary) {
        String taskKey = activitiUtils.getTaskByTaskId(salary.getTaskId()).getTaskDefinitionKey();
        if ("audit2".equals(taskKey)) {
            salary.setHrText(salary.getTaskComment());
        } else if ("audit3".equals(taskKey)) {
            salary.setLeadText(salary.getTaskComment());
        } else if ("audit4".equals(taskKey)) {
            salary.setMainLeadText(salary.getTaskComment());
        } else if ("apply_end".equals(salary.getTaskComment())) {
            // 流程完成，兑现
        }
        salaryService.update(salary);
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
