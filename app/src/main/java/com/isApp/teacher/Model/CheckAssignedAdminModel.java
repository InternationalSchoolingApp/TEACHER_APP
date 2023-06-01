package com.isApp.teacher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckAssignedAdminModel {

    @SerializedName("adminId")
    @Expose
    private Integer adminId;
    @SerializedName("message")
    @Expose
    private String message;

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    @SerializedName("adminName")
    @Expose
    private String adminName;


    @SerializedName("userId")
    @Expose
    private Integer userId;

    public CheckAssignedAdminModel(Integer userId) {
        this.userId = userId;
    }

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("adminEmail")
    @Expose
    private String adminEmail;

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

}