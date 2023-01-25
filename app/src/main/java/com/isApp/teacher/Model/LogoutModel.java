package com.isApp.teacher.Model;

import com.google.gson.annotations.SerializedName;

public class LogoutModel {
    @SerializedName("platformId")
    private Integer platformId;
    @SerializedName("email")
    private String username;
    @SerializedName("deviceInfo")
    private String deviceInfo;
    @SerializedName("schoolId")
    private Integer schoolId;
    @SerializedName("status")
    private String status;
    @SerializedName("userId")
    private int userId;

    public LogoutModel(Integer platformId, int userId, String deviceInfo, Integer schoolId, String username) {
        this.platformId = platformId;
        this.userId = userId;
        this.deviceInfo = deviceInfo;
        this.schoolId = schoolId;
        this.username = username ;
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
