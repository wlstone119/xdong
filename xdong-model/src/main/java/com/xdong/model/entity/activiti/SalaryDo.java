package com.xdong.model.entity.activiti;

import com.baomidou.mybatisplus.annotations.TableField;
import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 审批流程测试表
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
public class SalaryDo extends Task implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String            id;
    /**
     * 流程实例ID
     */
    @TableField("PROC_INS_ID")
    private String            procInsId;
    /**
     * 变动用户
     */
    @TableField("USER_ID")
    private String            userId;
    /**
     * 归属部门
     */
    @TableField("OFFICE_ID")
    private String            officeId;
    /**
     * 岗位
     */
    @TableField("POST")
    private String            post;
    /**
     * 性别
     */
    @TableField("AGE")
    private String            age;
    /**
     * 学历
     */
    @TableField("EDU")
    private String            edu;
    /**
     * 调整原因
     */
    @TableField("CONTENT")
    private String            content;
    /**
     * 现行标准 薪酬档级
     */
    @TableField("OLDA")
    private String            olda;
    /**
     * 现行标准 月工资额
     */
    @TableField("OLDB")
    private String            oldb;
    /**
     * 现行标准 年薪总额
     */
    @TableField("OLDC")
    private String            oldc;
    /**
     * 调整后标准 薪酬档级
     */
    @TableField("NEWA")
    private String            newa;
    /**
     * 调整后标准 月工资额
     */
    @TableField("NEWB")
    private String            newb;
    /**
     * 调整后标准 年薪总额
     */
    @TableField("NEWC")
    private String            newc;
    /**
     * 月增资
     */
    @TableField("ADD_NUM")
    private String            addNum;
    /**
     * 执行时间
     */
    @TableField("EXE_DATE")
    private String            exeDate;
    /**
     * 人力资源部门意见
     */
    @TableField("HR_TEXT")
    private String            hrText;
    /**
     * 分管领导意见
     */
    @TableField("LEAD_TEXT")
    private String            leadText;
    /**
     * 集团主要领导意见
     */
    @TableField("MAIN_LEAD_TEXT")
    private String            mainLeadText;
    /**
     * 创建者
     */
    @TableField("create_by")
    private String            createBy;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcInsId() {
        return procInsId;
    }

    public void setProcInsId(String procInsId) {
        this.procInsId = procInsId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEdu() {
        return edu;
    }

    public void setEdu(String edu) {
        this.edu = edu;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOlda() {
        return olda;
    }

    public void setOlda(String olda) {
        this.olda = olda;
    }

    public String getOldb() {
        return oldb;
    }

    public void setOldb(String oldb) {
        this.oldb = oldb;
    }

    public String getOldc() {
        return oldc;
    }

    public void setOldc(String oldc) {
        this.oldc = oldc;
    }

    public String getNewa() {
        return newa;
    }

    public void setNewa(String newa) {
        this.newa = newa;
    }

    public String getNewb() {
        return newb;
    }

    public void setNewb(String newb) {
        this.newb = newb;
    }

    public String getNewc() {
        return newc;
    }

    public void setNewc(String newc) {
        this.newc = newc;
    }

    public String getAddNum() {
        return addNum;
    }

    public void setAddNum(String addNum) {
        this.addNum = addNum;
    }

    public String getExeDate() {
        return exeDate;
    }

    public void setExeDate(String exeDate) {
        this.exeDate = exeDate;
    }

    public String getHrText() {
        return hrText;
    }

    public void setHrText(String hrText) {
        this.hrText = hrText;
    }

    public String getLeadText() {
        return leadText;
    }

    public void setLeadText(String leadText) {
        this.leadText = leadText;
    }

    public String getMainLeadText() {
        return mainLeadText;
    }

    public void setMainLeadText(String mainLeadText) {
        this.mainLeadText = mainLeadText;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
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
        return "Salary{" + ", id=" + id + ", procInsId=" + procInsId + ", userId=" + userId + ", officeId=" + officeId
               + ", post=" + post + ", age=" + age + ", edu=" + edu + ", content=" + content + ", olda=" + olda
               + ", oldb=" + oldb + ", oldc=" + oldc + ", newa=" + newa + ", newb=" + newb + ", newc=" + newc
               + ", addNum=" + addNum + ", exeDate=" + exeDate + ", hrText=" + hrText + ", leadText=" + leadText
               + ", mainLeadText=" + mainLeadText + ", createBy=" + createBy + ", createDate=" + createDate
               + ", updateBy=" + updateBy + ", updateDate=" + updateDate + ", remarks=" + remarks + ", delFlag="
               + delFlag + "}";
    }
}
