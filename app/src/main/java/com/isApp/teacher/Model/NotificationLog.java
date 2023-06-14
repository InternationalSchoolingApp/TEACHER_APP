package com.isApp.teacher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationLog {

    public NotificationLog(Integer userId, Integer notificationId) {
        this.userId = userId;
        this.notificationId = notificationId;
    }

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("notificationId")
    @Expose
    private Integer notificationId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

}
