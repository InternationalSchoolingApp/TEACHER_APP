package com.isApp.teacher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GetNotes {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("list")
    @Expose
    private java.util.List<List> list = null;

    @SerializedName("status")
    @Expose
    private String status;

    public GetNotes(Integer userId) {
        this.userId = userId;
    }

    @SerializedName("userId")
    @Expose
    private Integer userId;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public java.util.List<List> getList() {
        return list;
    }

    public void setList(java.util.List<List> list) {
        this.list = list;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public class List {

        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("deleted")
        @Expose
        private String deleted;
        @SerializedName("active")
        @Expose
        private String active;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("userId")
        @Expose
        private Integer userId;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDeleted() {
            return deleted;
        }

        public void setDeleted(String deleted) {
            this.deleted = deleted;
        }

        public String getActive() {
            return active;
        }

        public void setActive(String active) {
            this.active = active;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

    }

}
