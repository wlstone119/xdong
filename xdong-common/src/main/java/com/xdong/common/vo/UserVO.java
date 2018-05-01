package com.xdong.common.vo;

import com.xdong.dal.userrole.domain.SysUser;

/**
 * 类UserVO.java的实现描述：TODO 类实现描述
 * 
 * @author wanglei 2018年4月30日 上午12:48:50
 */
public class UserVO {

    /**
     * 更新的用户对象
     */
    private SysUser user = new SysUser();
    /**
     * 旧密码
     */
    private String  pwdOld;
    /**
     * 新密码
     */
    private String  pwdNew;

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public String getPwdOld() {
        return pwdOld;
    }

    public void setPwdOld(String pwdOld) {
        this.pwdOld = pwdOld;
    }

    public String getPwdNew() {
        return pwdNew;
    }

    public void setPwdNew(String pwdNew) {
        this.pwdNew = pwdNew;
    }
}
