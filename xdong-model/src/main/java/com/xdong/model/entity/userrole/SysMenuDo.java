package com.xdong.model.entity.userrole;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import java.util.Date;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 菜单管理
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
@TableName("sys_menu")
public class SysMenuDo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "menu_id", type = IdType.AUTO)
    private Long              menuId;
    /**
     * 父菜单ID，一级菜单为0
     */
    @TableField("parent_id")
    private Long              parentId;
    /**
     * 菜单名称
     */
    private String            name;
    /**
     * 菜单URL
     */
    private String            url;
    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     */
    private String            perms;
    /**
     * 类型 0：目录 1：菜单 2：按钮
     */
    private Integer           type;
    /**
     * 菜单图标
     */
    private String            icon;
    /**
     * 排序
     */
    @TableField("order_num")
    private Integer           orderNum;
    /**
     * 创建时间
     */
    @TableField("gmt_create")
    private Date              gmtCreate;
    /**
     * 修改时间
     */
    @TableField("gmt_modified")
    private Date              gmtModified;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    public String toString() {
        return "SysMenu{" + ", menuId=" + menuId + ", parentId=" + parentId + ", name=" + name + ", url=" + url
               + ", perms=" + perms + ", type=" + type + ", icon=" + icon + ", orderNum=" + orderNum + ", gmtCreate="
               + gmtCreate + ", gmtModified=" + gmtModified + "}";
    }
}
