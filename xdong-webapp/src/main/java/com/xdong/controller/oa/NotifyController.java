package com.xdong.controller.oa;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.plugins.Page;
import com.xdong.common.config.Constant;
import com.xdong.common.controller.BaseController;
import com.xdong.common.utils.PageUtils;
import com.xdong.common.utils.Query;
import com.xdong.common.utils.R;
import com.xdong.spi.admin.common.ISysDictService;
import com.xdong.spi.admin.notify.IOaNotifyRecordService;
import com.xdong.spi.admin.notify.IOaNotifyService;
import com.xdong.model.dto.notify.OaNotifyDto;
import com.xdong.model.entity.common.SysDictDo;
import com.xdong.model.entity.notify.OaNotifyDo;
import com.xdong.model.entity.notify.OaNotifyRecordDo;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类NotifyController.java的实现描述：TODO 类实现描述
 * 
 * @author wanglei 2018年5月1日 上午12:38:16
 */
@Controller
@RequestMapping("/oa/notify")
public class NotifyController extends BaseController {

    @Autowired
    private IOaNotifyService       notifyService;
    @Autowired
    private IOaNotifyRecordService notifyRecordService;
    @Autowired
    private ISysDictService        dictService;

    @GetMapping()
    @RequiresPermissions("oa:notify:notify")
    String OaNotifyDo() {
        return "oa/notify/notify";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("oa:notify:notify")
    public PageUtils list(@RequestParam Map<String, Object> params) {

        // 查询列表数据
        Query query = new Query(params);
        Page<OaNotifyDo> page = new Page<OaNotifyDo>(query.getPage(), query.getLimit());
        page.setCondition(convertConditionParam(params));

        return notifyService.list(page);
    }

    @GetMapping("/add")
    @RequiresPermissions("oa:notify:add")
    String add() {
        return "oa/notify/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("oa:notify:edit")
    String edit(@PathVariable("id") Long id, Model model) {
        OaNotifyDo notify = notifyService.selectById(id);
        List<SysDictDo> dictDOS = dictService.listByType("oa_notify_type");
        String type = notify.getType();
        for (SysDictDo dictDO : dictDOS) {
            if (type.equals(dictDO.getValue())) {
                dictDO.setRemarks("checked");
            }
        }
        model.addAttribute("oaNotifyTypes", dictDOS);
        model.addAttribute("notify", notify);
        return "oa/notify/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("oa:notify:add")
    public R save(OaNotifyDto notifyDto) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        notifyDto.setCreateBy(getUserId());

        if (notifyService.save(notifyDto)) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("oa:notify:edit")
    public R update(OaNotifyDo notify) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        notifyService.updateById(notify);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("oa:notify:remove")
    public R remove(Long id) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        if (notifyService.deleteById(id)) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("oa:notify:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] ids) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        notifyService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    @ResponseBody
    @GetMapping("/message")
    PageUtils message() {
        Map<String, Object> params = new HashMap<>(16);
        params.put("offset", 0);
        params.put("limit", 3);
        Query query = new Query(params);
        query.put("userId", getUserId());
        query.put("isRead", Constant.OA_NOTIFY_READ_NO);
        return notifyService.selfList(query);
    }

    @GetMapping("/selfNotify")
    String selefNotify() {
        return "oa/notify/selfNotify";
    }

    @ResponseBody
    @GetMapping("/selfList")
    PageUtils selfList(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        query.put("userId", getUserId());

        return notifyService.selfList(query);
    }

    @GetMapping("/read/{id}")
    @RequiresPermissions("oa:notify:edit")
    String read(@PathVariable("id") Long id, Model model) {
        com.xdong.model.entity.notify.OaNotifyDo notify = notifyService.selectById(id);
        // 更改阅读状态
        OaNotifyRecordDo notifyRecordDO = new OaNotifyRecordDo();
        notifyRecordDO.setNotifyId(id);
        notifyRecordDO.setUserId(getUserId());
        notifyRecordDO.setReadDate(new Date());
        notifyRecordDO.setIsRead(Constant.OA_NOTIFY_READ_YES);
        notifyRecordService.changeRead(notifyRecordDO);
        model.addAttribute("notify", notify);
        return "oa/notify/read";
    }

}
