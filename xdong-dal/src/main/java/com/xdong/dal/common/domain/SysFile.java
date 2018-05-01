package com.xdong.dal.common.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import java.util.Date;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 文件上传
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
@TableName("sys_file")
public class SysFile extends Model<SysFile> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long              id;
    /**
     * 文件类型
     */
    private Integer           type;
    /**
     * URL地址
     */
    private String            url;
    /**
     * 创建时间
     */
    @TableField("create_date")
    private Date              createDate;

    public SysFile(){
        super();
    }

    public SysFile(Integer type, String url, Date createDate){
        super();
        this.type = type;
        this.url = url;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SysFile{" + ", id=" + id + ", type=" + type + ", url=" + url + ", createDate=" + createDate + "}";
    }
}
