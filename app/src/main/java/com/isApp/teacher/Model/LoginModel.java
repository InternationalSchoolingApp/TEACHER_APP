package com.isApp.teacher.Model;


import com.google.gson.annotations.SerializedName;

public class LoginModel {
    @SerializedName("platformId")
    private Integer platformId;
    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SerializedName("email")
    private String username;
    @SerializedName("password")
    private String pass;
    @SerializedName("deviceInfo")
    private String deviceInfo;
    @SerializedName("schoolId")
    private Integer schoolId;
    @SerializedName("successMessage")
    private String successMessage;
    @SerializedName("status")
    private String status;
    @SerializedName("Token")
    private String token;
    @SerializedName("userId")
    private int userId;
    @SerializedName("teacherId")
    private int teacherId;


    public LoginModel(String username, String pass, String deviceInfo, Integer schoolId) {
        this.username = username;
        this.pass = pass;
        this.deviceInfo = deviceInfo;
        this.schoolId = schoolId;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
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

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int studentStandardId) {
        this.teacherId = studentStandardId;
    }
}

