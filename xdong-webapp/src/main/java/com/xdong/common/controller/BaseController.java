package com.xdong.common.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;

import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.xdong.common.utils.ShiroUtils;
import com.xdong.model.entity.userrole.SysUserDo;

@Controller
public class BaseController {

	protected static String SUCCESS = "SUCCESS";
	protected static String ERROR = "ERROR";

	protected static String REDIRECT = "redirect:";
	protected static String FORWARD = "forward:";

	public SysUserDo getUser() {
		return ShiroUtils.getUser();
	}

	public Long getUserId() {
		return getUser().getUserId();
	}

	public String getUsername() {
		return getUser().getUsername();
	}

	public Map<String, Object> convertConditionParam(Map<String, Object> paramMap) {
		if (paramMap == null || paramMap.isEmpty()) {
			return null;
		}
		Map<String, Object> result = new HashMap<String, Object>();
		for (String key : paramMap.keySet()) {
			if (!"offset".equals(key) && !"limit".equals(key) && !"page".equals(key) && !"sort".equals(key)
					&& !"order".equals(key)) {
				result.put(StringUtils.camelToUnderline(key), paramMap.get(key));
			}
		}
		return result;
	}

	protected String getUrl(String prefix, String url) {
		return prefix + "/" + url;
	}
}
