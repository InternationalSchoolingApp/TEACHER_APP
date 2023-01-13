package com.isApp.teacher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationForApp {

    @SerializedName("userId")
    @Expose
    private Integer userId;

    public NotificationForApp(Integer userId) {
        this.userId = userId;
    }

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("listSize")
    @Expose
    private Integer listSize;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("listForApp")
    @Expose
    private List<ListForApp> listForApp = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Integer getListSize() {
        return listSize;
    }

    public void setListSize(Integer listSize) {
        this.listSize = listSize;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ListForApp> getListForApp() {
        return listForApp;
    }

    public void setListForApp(List<ListForApp> listForApp) {
        this.listForApp = listForApp;
    }

    public class ListForApp {

        @SerializedName("createdDate")
        @Expose
        private String createdDate;
        @SerializedName("school")
        @Expose
        private Integer school;
        @SerializedName("entityName")
        @Expose
        private String entityName;
        @SerializedName("entityType")
        @Expose
        private String entityType;
        @SerializedName("topic")
        @Expose
        private String topic;
        @SerializedName("isSend")
        @Expose
        private Integer isSend;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("updatedDate")
        @Expose
        private String updatedDate;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("intentBackup")
        @Expose
        private String intentBackup;
        @SerializedName("userId")
        @Expose
        private Integer userId;
        @SerializedName("intent")
        @Expose
        private Integer intent;

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public Integer getSchool() {
            return school;
        }

        public void setSchool(Integer school) {
            this.school = school;
        }

        public String getEntityName() {
            return entityName;
        }

        public void setEntityName(String entityName) {
            this.entityName = entityName;
        }

        public String getEntityType() {
            return entityType;
        }

        public void setEntityType(String entityType) {
            this.entityType = entityType;
        }

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public Integer getIsSend() {
            return isSend;
        }

        public void setIsSend(Integer isSend) {
            this.isSend = isSend;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUpdatedDate() {
            return updatedDate;
        }

        public void setUpdatedDate(String updatedDate) {
            this.updatedDate = updatedDate;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getIntentBackup() {
            return intentBackup;
        }

        public void setIntentBackup(String intentBackup) {
            this.intentBackup = intentBackup;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getIntent() {
            return intent;
        }

        public void setIntent(Integer intent) {
            this.intent = intent;
        }

    }
}
