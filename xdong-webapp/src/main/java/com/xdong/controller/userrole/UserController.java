package com.xdong.controller.userrole;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.plugins.Page;
import com.xdong.common.annotation.Log;
import com.xdong.common.config.Constant;
import com.xdong.common.controller.BaseController;
import com.xdong.common.utils.MD5Utils;
import com.xdong.common.utils.PageUtils;
import com.xdong.common.utils.Query;
import com.xdong.common.utils.R;
import com.xdong.common.vo.UserVO;
import com.xdong.model.dto.userrole.SysUserDto;
import com.xdong.model.entity.userrole.SysDeptDo;
import com.xdong.model.entity.userrole.SysRoleDo;
import com.xdong.model.entity.userrole.SysUserDo;
import com.xdong.spi.admin.common.ISysDictService;
import com.xdong.spi.admin.userrole.ISysRoleService;
import com.xdong.spi.admin.userrole.ISysUserService;
import com.xdong.spi.admin.userrole.Tree;

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

	private String prefix = "system/user";
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
		Page<SysUserDo> page = new Page<SysUserDo>(query.getPage(), query.getLimit());
		Page<SysUserDo> result = userService.selectPage(page.setCondition(convertConditionParam(params)));
		PageUtils pageUtils = new PageUtils(result.getRecords(), result.getTotal());

		return pageUtils;
	}

	@RequiresPermissions("sys:user:add")
	@Log("添加用户")
	@GetMapping("/add")
	String add(Model model) {
		List<SysRoleDo> roles = roleService.list();
		model.addAttribute("roles", roles);
		return prefix + "/add";
	}

	@RequiresPermissions("sys:user:edit")
	@Log("编辑用户")
	@GetMapping("/edit/{id}")
	String edit(Model model, @PathVariable("id") Long id) {
		SysUserDo SysUserDo = userService.get(id);
		model.addAttribute("user", SysUserDo);
		List<SysRoleDo> roles = roleService.list(id);
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
	R updatePeronal(SysUserDo user) {
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

		SysUserDo SysUserDo = new SysUserDo();
		SysUserDo.setUserId(userId);
		model.addAttribute("user", SysUserDo);
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
	public Tree<SysDeptDo> tree() {
		Tree<SysDeptDo> tree = new Tree<SysDeptDo>();
		tree = userService.getTree();
		return tree;
	}

	@GetMapping("/treeView")
	String treeView() {
		return prefix + "/userTree";
	}

	@GetMapping("/personal")
	String personal(Model model) {
		SysUserDo SysUserDo = userService.get(getUserId());
		model.addAttribute("user", SysUserDo);
		model.addAttribute("hobbyList", dictService.getHobbyList(SysUserDo));
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
