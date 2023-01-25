package com.isApp.teacher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubjectDescription {

    @SerializedName("sno")
    @Expose
    private Object sno;
    @SerializedName("syllabusId")
    @Expose
    private Integer syllabusId;
    @SerializedName("standardId")
    @Expose
    private Integer standardId;
    @SerializedName("standardName")
    @Expose
    private Object standardName;

    public SubjectDescription(Integer subjectId) {
        this.subjectId = subjectId;
    }

    @SerializedName("subjectId")
    @Expose
    private Integer subjectId;
    @SerializedName("subjectCode")
    @Expose
    private String subjectCode;
    @SerializedName("subjectName")
    @Expose
    private String subjectName;
    @SerializedName("moduleName")
    @Expose
    private Object moduleName;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("startDate")
    @Expose
    private Object startDate;
    @SerializedName("endDate")
    @Expose
    private Object endDate;
    @SerializedName("editSyllabus")
    @Expose
    private Object editSyllabus;
    @SerializedName("action")
    @Expose
    private Object action;
    @SerializedName("syllabusType")
    @Expose
    private Object syllabusType;
    @SerializedName("courseProviderId")
    @Expose
    private Object courseProviderId;
    @SerializedName("courseProviderName")
    @Expose
    private Object courseProviderName;

    public Object getSno() {
        return sno;
    }

    public void setSno(Object sno) {
        this.sno = sno;
    }

    public Integer getSyllabusId() {
        return syllabusId;
    }

    public void setSyllabusId(Integer syllabusId) {
        this.syllabusId = syllabusId;
    }

    public Integer getStandardId() {
        return standardId;
    }

    public void setStandardId(Integer standardId) {
        this.standardId = standardId;
    }

    public Object getStandardName() {
        return standardName;
    }

    public void setStandardName(Object standardName) {
        this.standardName = standardName;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
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

    public Object getModuleName() {
        return moduleName;
    }

    public void setModuleName(Object moduleName) {
        this.moduleName = moduleName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Object getStartDate() {
        return startDate;
    }

    public void setStartDate(Object startDate) {
        this.startDate = startDate;
    }

    public Object getEndDate() {
        return endDate;
    }

    public void setEndDate(Object endDate) {
        this.endDate = endDate;
    }

    public Object getEditSyllabus() {
        return editSyllabus;
    }

    public void setEditSyllabus(Object editSyllabus) {
        this.editSyllabus = editSyllabus;
    }

    public Object getAction() {
        return action;
    }

    public void setAction(Object action) {
        this.action = action;
    }

    public Object getSyllabusType() {
        return syllabusType;
    }

    public void setSyllabusType(Object syllabusType) {
        this.syllabusType = syllabusType;
    }

    public Object getCourseProviderId() {
        return courseProviderId;
    }

    public void setCourseProviderId(Object courseProviderId) {
        this.courseProviderId = courseProviderId;
    }

    public Object getCourseProviderName() {
        return courseProviderName;
    }

    public void setCourseProviderName(Object courseProviderName) {
        this.courseProviderName = courseProviderName;
    }

}