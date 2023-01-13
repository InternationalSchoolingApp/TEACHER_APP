package com.isApp.teacher.Network;

import com.isApp.teacher.Model.AssignStudentModel;
import com.isApp.teacher.Model.AssignedSubjects;
import com.isApp.teacher.Model.DashBoardModel;
import com.isApp.teacher.Model.DashboardNotificationModel;
import com.isApp.teacher.Model.FirebaseTokenModel;
import com.isApp.teacher.Model.ForgetPasswordModel;
import com.isApp.teacher.Model.LoginModel;
import com.isApp.teacher.Model.NotificationForApp;
import com.isApp.teacher.Model.ProfileModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("login-platform-App-teacher")
    Call<LoginModel> loginPostData(@Body LoginModel loginModel);

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

}
