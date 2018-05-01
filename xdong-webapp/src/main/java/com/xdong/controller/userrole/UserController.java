package com.xdong.controller.userrole;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.plugins.Page;
import com.xdong.admin.service.common.ISysDictService;
import com.xdong.admin.service.userrole.ISysRoleService;
import com.xdong.admin.service.userrole.ISysUserService;
import com.xdong.common.annotation.Log;
import com.xdong.common.config.Constant;
import com.xdong.common.controller.BaseController;
import com.xdong.common.utils.MD5Utils;
import com.xdong.common.utils.PageUtils;
import com.xdong.common.utils.Query;
import com.xdong.common.utils.R;
import com.xdong.common.vo.UserVO;
import com.xdong.dal.blog.domain.BlogContent;
import com.xdong.dal.common.domain.Tree;
import com.xdong.dal.notify.domain.OaNotify;
import com.xdong.dal.userrole.domain.SysDept;
import com.xdong.dal.userrole.domain.SysRole;
import com.xdong.dal.userrole.domain.SysUser;
import com.xdong.dto.SysUserDto;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/sys/user")
@Controller
public class UserController extends BaseController {

    private String  prefix = "system/user";
    @Autowired
    ISysUserService userService;
    @Autowired
    ISysRoleService roleService;
    @Autowired
    ISysDictService dictService;

    @RequiresPermissions("sys:user:user")
    @GetMapping("")
    String user(Model model) {
        return prefix + "/user";
    }

    @GetMapping("/list")
    @ResponseBody
    PageUtils list(@RequestParam Map<String, Object> params) {

        // 查询列表数据
        Query query = new Query(params);
        Page<SysUser> page = new Page<SysUser>(query.getPage(), query.getLimit());
        Page<SysUser> result = userService.selectPage(page.setCondition(convertConditionParam(params)));
        PageUtils pageUtils = new PageUtils(result.getRecords(), result.getTotal());

        return pageUtils;
    }

    @RequiresPermissions("sys:user:add")
    @Log("添加用户")
    @GetMapping("/add")
    String add(Model model) {
        List<SysRole> roles = roleService.list();
        model.addAttribute("roles", roles);
        return prefix + "/add";
    }

    @RequiresPermissions("sys:user:edit")
    @Log("编辑用户")
    @GetMapping("/edit/{id}")
    String edit(Model model, @PathVariable("id") Long id) {
        SysUser SysUser = userService.get(id);
        model.addAttribute("user", SysUser);
        List<SysRole> roles = roleService.list(id);
        model.addAttribute("roles", roles);
        return prefix + "/edit";
    }

    @RequiresPermissions("sys:user:add")
    @Log("保存用户")
    @PostMapping("/save")
    @ResponseBody
    R save(SysUserDto userDto) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        userDto.setPassword(MD5Utils.encrypt(userDto.getUsername(), userDto.getPassword()));
        if (userService.save(userDto)) {
            return R.ok();
        }
        return R.error();
    }

    @RequiresPermissions("sys:user:edit")
    @Log("更新用户")
    @PostMapping("/update")
    @ResponseBody
    R update(SysUserDto userDto) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        if (userService.update(userDto)) {
            return R.ok();
        }
        return R.error();
    }

    @RequiresPermissions("sys:user:edit")
    @Log("更新用户")
    @PostMapping("/updatePeronal")
    @ResponseBody
    R updatePeronal(SysUser user) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        if (userService.updateById(user)) {
            return R.ok();
        }
        return R.error();
    }

    @RequiresPermissions("sys:user:remove")
    @Log("删除用户")
    @PostMapping("/remove")
    @ResponseBody
    R remove(Long id) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        if (userService.deleteById(id)) {
            return R.ok();
        }
        return R.error();
    }

    @RequiresPermissions("sys:user:batchRemove")
    @Log("批量删除用户")
    @PostMapping("/batchRemove")
    @ResponseBody
    R batchRemove(@RequestParam("ids[]") Long[] userIds) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        if (userService.deleteBatchIds(Arrays.asList(userIds))) {
            return R.ok();
        }
        return R.error();
    }

    @PostMapping("/exit")
    @ResponseBody
    boolean exit(@RequestParam Map<String, Object> params) {
        // 存在，不通过，false
        return !userService.exit(params);
    }

    @RequiresPermissions("sys:user:resetPwd")
    @Log("请求更改用户密码")
    @GetMapping("/resetPwd/{id}")
    String resetPwd(@PathVariable("id") Long userId, Model model) {

        SysUser SysUser = new SysUser();
        SysUser.setUserId(userId);
        model.addAttribute("user", SysUser);
        return prefix + "/reset_pwd";
    }

    @Log("提交更改用户密码")
    @PostMapping("/resetPwd")
    @ResponseBody
    R resetPwd(UserVO userVO) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        try {
            userService.resetPwd(userVO, getUser());
            return R.ok();
        } catch (Exception e) {
            return R.error(1, e.getMessage());
        }

    }

    @RequiresPermissions("sys:user:resetPwd")
    @Log("admin提交更改用户密码")
    @PostMapping("/adminResetPwd")
    @ResponseBody
    R adminResetPwd(UserVO userVO) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        try {
            userService.adminResetPwd(userVO);
            return R.ok();
        } catch (Exception e) {
            return R.error(1, e.getMessage());
        }

    }

    @GetMapping("/tree")
    @ResponseBody
    public Tree<SysDept> tree() {
        Tree<SysDept> tree = new Tree<SysDept>();
        tree = userService.getTree();
        return tree;
    }

    @GetMapping("/treeView")
    String treeView() {
        return prefix + "/userTree";
    }

    @GetMapping("/personal")
    String personal(Model model) {
        SysUser SysUser = userService.get(getUserId());
        model.addAttribute("user", SysUser);
        model.addAttribute("hobbyList", dictService.getHobbyList(SysUser));
        model.addAttribute("sexList", dictService.getSexList());
        return prefix + "/personal";
    }

    @ResponseBody
    @PostMapping("/uploadImg")
    R uploadImg(@RequestParam("avatar_file") MultipartFile file, String avatar_data, HttpServletRequest request) {
        if ("test".equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        Map<String, Object> result = new HashMap<>();
        try {
            result = userService.updatePersonalImg(file, avatar_data, getUserId());
        } catch (Exception e) {
            return R.error("更新图像失败！");
        }
        if (result != null && result.size() > 0) {
            return R.ok(result);
        } else {
            return R.error("更新图像失败！");
        }
    }
}
