package com.isApp.teacher.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ScheduleModel {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("userId")
    @Expose
    private Integer userId;

    @SerializedName("startDate")
    @Expose
    private String startDate;

    @SerializedName("endDate")
    @Expose
    private String endDate;

    public ScheduleModel(Integer userId, String startDate, String endDate) {
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @SerializedName("statusCode")
    @Expose
    private String statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("schoolCalendar")
    @Expose
    private SchoolCalendar schoolCalendar;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SchoolCalendar getSchoolCalendar() {
        return schoolCalendar;
    }

    public void setSchoolCalendar(SchoolCalendar schoolCalendar) {
        this.schoolCalendar = schoolCalendar;
    }

    public class SchoolCalendar {

        @SerializedName("event")
        @Expose
        private List<Event> event = null;

        public List<Event> getEvent() {
            return event;
        }

        public void setEvent(List<Event> event) {
            this.event = event;
        }


        public class Event {

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("start")
            @Expose
            private String start;
            @SerializedName("end")
            @Expose
            private String end;
            @SerializedName("color")
            @Expose
            private String color;
            @SerializedName("activities")
            @Expose
            private Object activities;
            @SerializedName("url")
            @Expose
            private String url;
            @SerializedName("allDay")
            @Expose
            private Object allDay;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getStart() {
                return start;
            }

            public void setStart(String start) {
                this.start = start;
            }

            public String getEnd() {
                return end;
            }

            public void setEnd(String end) {
                this.end = end;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public Object getActivities() {
                return activities;
            }

            public void setActivities(Object activities) {
                this.activities = activities;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public Object getAllDay() {
                return allDay;
            }

            public void setAllDay(Object allDay) {
                this.allDay = allDay;
            }

        }

    }

}