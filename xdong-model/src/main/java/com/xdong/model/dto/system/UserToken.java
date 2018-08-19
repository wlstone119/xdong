package com.xdong.model.dto.system;

import java.io.Serializable;

/**
 * 类UserToken.java的实现描述：TODO 类实现描述
 * 
 * @author wanglei 2018年4月30日 上午2:24:37
 */
public class UserToken implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long              userId;
    private String            username;
    private String            name;
    private String            password;
    private Long              deptId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }
}
