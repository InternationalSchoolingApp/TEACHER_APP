package com.isApp.teacher.Model;

import com.google.gson.annotations.SerializedName;

public class DashboardNotificationModel {

    @SerializedName("userId")
    private Integer userId;
    @SerializedName("status")
    private String status;
    @SerializedName("count")
    private Integer notificationCount;

    public DashboardNotificationModel(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getNotificationCount() {
        return notificationCount;
    }

    public void setNotificationCount(Integer notificationCount) {
        this.notificationCount = notificationCount;
    }
}
