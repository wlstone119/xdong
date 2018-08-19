package com.xdong.model.dto.notify;

import com.xdong.model.entity.notify.OaNotifyDo;

public class OaNotifyDto extends OaNotifyDo {

    private static final long serialVersionUID = 1L;

    private String            isRead;

    private String            before;

    private String            sender;

    private Long[]            userIds;

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Long[] getUserIds() {
        return userIds;
    }

    public void setUserIds(Long[] userIds) {
        this.userIds = userIds;
    }

    @Override
    public String toString() {
        return "NotifyDTO{" + "isRead='" + isRead + '\'' + ", before='" + before + '\'' + ", sender='" + sender + '\''
               + '}';
    }
}
