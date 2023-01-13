package com.isApp.teacher.Model;

import com.google.gson.annotations.SerializedName;

public class DashBoardModel {
    @SerializedName("teacherId")
    private Integer teacherId;
    @SerializedName("firstName")
    private String fName;
    @SerializedName("applicationNumber")
    private String applicationNumber;
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private String status;

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }

    @SerializedName("pictureLink")
    private String pictureLink;



    public DashBoardModel(Integer teacherId) {
        this.teacherId = teacherId;
    }


    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String ApplicationNumber) {
        this.applicationNumber = ApplicationNumber;
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
}
