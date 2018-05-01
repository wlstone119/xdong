package com.xdong.common.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.xdong.common.utils.Query;
import com.xdong.common.utils.ShiroUtils;
import com.xdong.dal.userrole.domain.SysUser;

@Controller
public class BaseController {

    protected static String SUCCESS  = "SUCCESS";
    protected static String ERROR    = "ERROR";

    protected static String REDIRECT = "redirect:";
    protected static String FORWARD  = "forward:";

    public SysUser getUser() {
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
}
