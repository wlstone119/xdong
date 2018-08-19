package com.xdong.model.entity.notify;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import java.util.Date;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 通知通告
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
@TableName("oa_notify")
public class OaNotifyDo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long              id;
    /**
     * 类型
     */
    private String            type;
    /**
     * 标题
     */
    private String            title;
    /**
     * 内容
     */
    private String            content;
    /**
     * 附件
     */
    private String            files;
    /**
     * 状态
     */
    private String            status;
    /**
     * 创建者
     */
    @TableField("create_by")
    private Long              createBy;
    /**
     * 创建时间
     */
    @TableField("create_date")
    private Date              createDate;
    /**
     * 更新者
     */
    @TableField("update_by")
    private String            updateBy;
    /**
     * 更新时间
     */
    @TableField("update_date")
    private Date              updateDate;
    /**
     * 备注信息
     */
    private String            remarks;
    /**
     * 删除标记
     */
    @TableField("del_flag")
    private String            delFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "OaNotify{" + ", id=" + id + ", type=" + type + ", title=" + title + ", content=" + content + ", files="
               + files + ", status=" + status + ", createBy=" + createBy + ", createDate=" + createDate + ", updateBy="
               + updateBy + ", updateDate=" + updateDate + ", remarks=" + remarks + ", delFlag=" + delFlag + "}";
    }
}
