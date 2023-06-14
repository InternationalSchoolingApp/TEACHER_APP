package com.isApp.teacher.Network;

import com.isApp.teacher.Model.AdminStudentMappingModel;
import com.isApp.teacher.Model.AssignStudentModel;
import com.isApp.teacher.Model.AssignedSubjects;
import com.isApp.teacher.Model.CheckAssignedAdminModel;
import com.isApp.teacher.Model.CreateMyNotes;
import com.isApp.teacher.Model.DashBoardModel;
import com.isApp.teacher.Model.DashboardNotificationModel;
import com.isApp.teacher.Model.DeleteNotes;
import com.isApp.teacher.Model.FirebaseTokenModel;
import com.isApp.teacher.Model.ForgetPasswordModel;
import com.isApp.teacher.Model.GetNotes;
import com.isApp.teacher.Model.LoginModel;
import com.isApp.teacher.Model.LogoutModel;
import com.isApp.teacher.Model.NotificationForApp;
import com.isApp.teacher.Model.NotificationLog;
import com.isApp.teacher.Model.ProfileModel;
import com.isApp.teacher.Model.ScheduleModel;
import com.isApp.teacher.Model.Status;
import com.isApp.teacher.Model.SubjectDescription;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("login-platform-App-teacher")
    Call<LoginModel> loginPostData(@Body LoginModel loginModel);

    @POST("get-my-notes")
    Call<GetNotes> getMyNotes(@Body GetNotes getNotes);

    @POST("create-my-notes")
    Call<CreateMyNotes> createMyNotes(@Body CreateMyNotes createMyNotes);

    @POST("device-token")
    Call<FirebaseTokenModel> firebaseToken(@Body FirebaseTokenModel firebaseTokenModel);

    @POST("assigned-students")
    Call<AssignStudentModel> assignStudentModelCall(@Body AssignStudentModel assignStudentModel);

    @POST("forgot-password")
    Call<ForgetPasswordModel> forgetPassword(@Body ForgetPasswordModel forgetPasswordModel);

    @POST("get-notification-app")
    Call<NotificationForApp> notificationForApp(@Body NotificationForApp notificationForApp);

    @POST("notification-check")
    Call<DashboardNotificationModel> dashboardNotificationModel(@Body DashboardNotificationModel dashboardNotificationModel);

    @POST("get-small-teacher-profile")
    Call<DashBoardModel> dashTeacherProfile(@Body DashBoardModel dashBoardModel);


    @POST("profile-teacher-activity")
    Call<ProfileModel> profileModel(@Body ProfileModel profileModel);

    @POST("teacher-subject")
    Call<AssignedSubjects> getSubject(@Body AssignedSubjects assignedSubjects);

    @POST("subject-description")
    Call<SubjectDescription> getSubjectDescription(@Body SubjectDescription subjectDescription);

    @POST("school-calendar")
    Call<ScheduleModel> getSchedule(@Body ScheduleModel scheduleModel);

    @POST("logout-platform-App")
    Call<LogoutModel> logoutPostData(@Body LogoutModel logoutModel);

    @POST("delete-notes")
    Call<DeleteNotes> deleteNotes(@Body DeleteNotes deleteNotes);


    @POST("student-admin-mapping-check")
    Call<CheckAssignedAdminModel> checkAssignedAdminModel(@Body CheckAssignedAdminModel checkAssignedAdminModel);

    @POST("student-admin-mapping-for-chat")
    Call<AdminStudentMappingModel> adminStudentMappingModel(@Body AdminStudentMappingModel adminStudentMappingModel);

    @POST("user-status")
    Call<Status> getUserStatus(@Body Status appUpdationCheckModel);

    @POST("update-notification-log")
    Call<NotificationLog> getUpdateNotification(@Body NotificationLog notificationLog);

}
