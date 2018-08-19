package com.xdong.model.entity.activiti;

import java.util.Map;

/**
 * 类TaskDO.java的实现描述：TODO 类实现描述
 * 
 * @author wanglei 2018年2月5日 下午3:46:40
 */
public class Task {

    private String              taskId;
    private String              taskComment;
    private String              taskPass;
    private Map<String, Object> vars;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskComment() {
        return taskComment;
    }

    public void setTaskComment(String taskComment) {
        this.taskComment = taskComment;
    }

    public String getTaskPass() {
        return taskPass;
    }

    public void setTaskPass(String taskPass) {
        this.taskPass = taskPass;
    }

    public Map<String, Object> getVars() {
        return vars;
    }

    public void setVars(Map<String, Object> vars) {
        this.vars = vars;
    }

}
