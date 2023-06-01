package com.isApp.teacher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Status {


    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("status")
    @Expose
    private String status;

    public Status(Integer userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
