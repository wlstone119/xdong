package com.xdong.model.entity.notify;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import java.util.Date;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 通知通告发送记录
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
@TableName("oa_notify_record")
public class OaNotifyRecordDo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long              id;
    /**
     * 通知通告ID
     */
    @TableField("notify_id")
    private Long              notifyId;
    /**
     * 接受人
     */
    @TableField("user_id")
    private Long              userId;
    /**
     * 阅读标记
     */
    @TableField("is_read")
    private Integer           isRead;
    /**
     * 阅读时间
     */
    @TableField("read_date")
    private Date              readDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(Long notifyId) {
        this.notifyId = notifyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Date getReadDate() {
        return readDate;
    }

    public void setReadDate(Date readDate) {
        this.readDate = readDate;
    }

    @Override
    public String toString() {
        return "OaNotifyRecord{" + ", id=" + id + ", notifyId=" + notifyId + ", userId=" + userId + ", isRead=" + isRead
               + ", readDate=" + readDate + "}";
    }
}
