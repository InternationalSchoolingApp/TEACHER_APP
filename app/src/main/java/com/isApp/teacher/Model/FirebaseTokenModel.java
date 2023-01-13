package com.isApp.teacher.Model;


import com.google.gson.annotations.SerializedName;

public class FirebaseTokenModel {

    @SerializedName("userId")
    private Integer userId;
    @SerializedName("deviceId")
    private String deviceId;
    @SerializedName("token")
    private String token;
    @SerializedName("Token")
    private String tokenOnResponse;
    @SerializedName("status")
    private String status;

    public FirebaseTokenModel(Integer userId, String deviceId, String token) {
        this.userId = userId;
        this.deviceId = deviceId;
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenOnResponse() {
        return tokenOnResponse;
    }

    public void setTokenOnResponse(String tokenOnResponse) {
        this.tokenOnResponse = tokenOnResponse;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

