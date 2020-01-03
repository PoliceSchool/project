package com.policeschool.mall.mq;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: lujingxiao
 * @description:
 * @since:
 * @version:
 * @date: Created in 2020/1/3.
 */
public class LogMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String msg;
    private String logLevel;
    private String serviceType;
    private Date createTime;
    private Long userId;
    public LogMessage() {
        super();
    }
    public LogMessage(Long id, String msg, String logLevel, String serviceType, Date createTime, Long userId) {
        super();
        this.id = id;
        this.msg = msg;
        this.logLevel = logLevel;
        this.serviceType = serviceType;
        this.createTime = createTime;
        this.userId = userId;
    }
    @Override
    public String toString() {
        return "LogMessage [id=" + id + ", msg=" + msg + ", logLevel=" + logLevel + ", serviceType=" + serviceType
                + ", createTime=" + createTime + ", userId=" + userId + "]";
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getLogLevel() {
        return logLevel;
    }
    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }
    public String getServiceType() {
        return serviceType;
    }
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
