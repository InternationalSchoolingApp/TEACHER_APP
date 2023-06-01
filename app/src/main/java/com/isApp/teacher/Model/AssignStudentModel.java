package com.isApp.teacher.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AssignStudentModel {



    @SerializedName("teacherId")
    private Integer teacherId;

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public AssignStudentModel(Integer teacherId) {
        this.teacherId = teacherId;
    }

    @SerializedName("status")
    @Expose
    private String status;




    @SerializedName("YeLoList")
    @Expose
    private List<YeLo> yeLoList = null;
    @SerializedName("message")
    @Expose
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<YeLo> getYeLoList() {
        return yeLoList;
    }

    public void setYeLoList(List<YeLo> yeLoList) {
        this.yeLoList = yeLoList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class YeLo {

        @SerializedName("linkingTeacherId")
        @Expose
        private String linkingTeacherId;
        @SerializedName("studentId")
        @Expose
        private String studentId;
        @SerializedName("teacherName")
        @Expose
        private String teacherName;
        @SerializedName("studentName")
        @Expose
        private String studentName;
        @SerializedName("coursesName")
        @Expose
        private String coursesName;
        @SerializedName("grade")
        @Expose
        private String grade;

        @SerializedName("countryName")
        @Expose
        private String countryName;
        @SerializedName("cityName")
        @Expose
        private String cityName;
        @SerializedName("timeZone")
        @Expose
        private String timeZone;
        @SerializedName("currentTime")
        @Expose
        private String currentTime;

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(String timeZone) {
            this.timeZone = timeZone;
        }

        public String getCurrentTime() {
            return currentTime;
        }

        public void setCurrentTime(String currentTime) {
            this.currentTime = currentTime;
        }




        public String getLinkingTeacherId() {
            return linkingTeacherId;
        }

        public void setLinkingTeacherId(String linkingTeacherId) {
            this.linkingTeacherId = linkingTeacherId;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @SerializedName("email")
        @Expose
        private String email;


        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public String getCoursesName() {
            return coursesName;
        }

        public void setCoursesName(String coursesName) {
            this.coursesName = coursesName;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

    }

}