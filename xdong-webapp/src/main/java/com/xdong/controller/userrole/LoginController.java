package com.xdong.controller.userrole;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xdong.common.annotation.Log;
import com.xdong.common.controller.BaseController;
import com.xdong.common.utils.MD5Utils;
import com.xdong.common.utils.R;
import com.xdong.common.utils.ShiroUtils;
import com.xdong.model.entity.common.SysFileDo;
import com.xdong.model.entity.userrole.SysMenuDo;
import com.xdong.spi.admin.common.ISysFileService;
import com.xdong.spi.admin.userrole.ISysMenuService;
import com.xdong.spi.admin.userrole.Tree;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ISysMenuService menuService;
	@Autowired
	ISysFileService fileService;

	@GetMapping({ "/", "" })
	String portalIndex(Model model) {
		return "/blog/index/main";
	}

	@Log("请求访问主页")
	@GetMapping({ "/index" })
	String index(Model model) {
		List<Tree<SysMenuDo>> menus = menuService.listMenuTree(getUserId());
		model.addAttribute("menus", menus);
		model.addAttribute("name", getUser().getName());
		SysFileDo SysFileDo = fileService.selectById(getUser().getPicId());
		if (SysFileDo != null && SysFileDo.getUrl() != null) {
			if (fileService.isExist(SysFileDo.getUrl())) {
				model.addAttribute("picUrl", SysFileDo.getUrl());
			} else {
				model.addAttribute("picUrl", "/img/photo_s.jpg");
			}
		} else {
			model.addAttribute("picUrl", "/img/photo_s.jpg");
		}
		model.addAttribute("username", getUser().getUsername());
		return "index_v1";
	}

	@GetMapping("/login")
	String login(HttpServletRequest request, HttpServletResponse response) {
		return "login";
	}

	@Log("登录")
	@PostMapping("/login")
	@ResponseBody
	R ajaxLogin(String username, String password) {
		password = MD5Utils.encrypt(username, password);
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			return R.ok();
		} catch (AuthenticationException e) {
			return R.error("用户或密码错误");
		}
	}

	@GetMapping("/logout")
	String logout() {
		ShiroUtils.logout();
		return "redirect:/login";
	}

	@GetMapping("/main")
	String main() {
		return "main";
	}

	@GetMapping("/403")
	String error403() {
		return "403";
	}

}
