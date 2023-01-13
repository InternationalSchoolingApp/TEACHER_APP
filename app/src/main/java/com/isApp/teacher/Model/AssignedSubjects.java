package com.isApp.teacher.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AssignedSubjects {

    @SerializedName("teacherSubjectDTO")
    @Expose
    private List<TeacherSubjectDTO> teacherSubjectDTO = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("userId")
    @Expose
    private Integer userId;

    public AssignedSubjects(Integer userId) {
        this.userId = userId;
    }

    public List<TeacherSubjectDTO> getTeacherSubjectDTO() {
        return teacherSubjectDTO;
    }

    public void setTeacherSubjectDTO(List<TeacherSubjectDTO> teacherSubjectDTO) {
        this.teacherSubjectDTO = teacherSubjectDTO;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class TeacherSubjectDTO {

        @SerializedName("teacherId")
        @Expose
        private Integer teacherId;
        @SerializedName("standardName")
        @Expose
        private String standardName;
        @SerializedName("subjectId")
        @Expose
        private String subjectId;
        @SerializedName("placementSubjectId")
        @Expose
        private Object placementSubjectId;
        @SerializedName("subjectCode")
        @Expose
        private String subjectCode;
        @SerializedName("subjectName")
        @Expose
        private String subjectName;
        @SerializedName("schoolId")
        @Expose
        private Object schoolId;
        @SerializedName("standardId")
        @Expose
        private Object standardId;
        @SerializedName("studentId")
        @Expose
        private Object studentId;
        @SerializedName("subjectTitle")
        @Expose
        private String subjectTitle;
        @SerializedName("subjectIcon")
        @Expose
        private String subjectIcon;
        @SerializedName("subjectDesc")
        @Expose
        private String subjectDesc;
        @SerializedName("subjectRating")
        @Expose
        private Integer subjectRating;
        @SerializedName("courseType")
        @Expose
        private String courseType;

        @SerializedName("courseProviderName")
        @Expose
        private String courseProviderName;
        @SerializedName("imgUrl")
        @Expose
        private String imgUrl;
        @SerializedName("startDate")
        @Expose
        private String startDate;

        public Integer getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(Integer teacherId) {
            this.teacherId = teacherId;
        }

        public String getStandardName() {
            return standardName;
        }

        public void setStandardName(String standardName) {
            this.standardName = standardName;
        }

        public String getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(String subjectId) {
            this.subjectId = subjectId;
        }

        public Object getPlacementSubjectId() {
            return placementSubjectId;
        }

        public void setPlacementSubjectId(Object placementSubjectId) {
            this.placementSubjectId = placementSubjectId;
        }

        public String getSubjectCode() {
            return subjectCode;
        }

        public void setSubjectCode(String subjectCode) {
            this.subjectCode = subjectCode;
        }

        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
        }

        public Object getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(Object schoolId) {
            this.schoolId = schoolId;
        }

        public Object getStandardId() {
            return standardId;
        }

        public void setStandardId(Object standardId) {
            this.standardId = standardId;
        }

        public Object getStudentId() {
            return studentId;
        }

        public void setStudentId(Object studentId) {
            this.studentId = studentId;
        }

        public String getSubjectTitle() {
            return subjectTitle;
        }

        public void setSubjectTitle(String subjectTitle) {
            this.subjectTitle = subjectTitle;
        }

        public String getSubjectIcon() {
            return subjectIcon;
        }

        public void setSubjectIcon(String subjectIcon) {
            this.subjectIcon = subjectIcon;
        }

        public String getSubjectDesc() {
            return subjectDesc;
        }

        public void setSubjectDesc(String subjectDesc) {
            this.subjectDesc = subjectDesc;
        }

        public Integer getSubjectRating() {
            return subjectRating;
        }

        public void setSubjectRating(Integer subjectRating) {
            this.subjectRating = subjectRating;
        }

        public String getCourseType() {
            return courseType;
        }

        public void setCourseType(String courseType) {
            this.courseType = courseType;
        }



        public String getCourseProviderName() {
            return courseProviderName;
        }

        public void setCourseProviderName(String courseProviderName) {
            this.courseProviderName = courseProviderName;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

    }




        }
