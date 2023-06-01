package com.isApp.teacher.common;

import java.util.HashMap;

public class Constants {
    public static final String USER_ID = "userId";
    public static final String ON_HOLD = "hold";
    public static final String NAME = "name";
    public static final String PLATFORM_ID = "platformId";
    public static final String TEACHER_ID = "teacherId";
    public static final String SCHOOL_ID = "schoolId";
    public static final String PREFERENCE_NAME = "isTeacherApp";

    public static final String ADMIN_ID = "adminId";
    public static final String USER_LOGGED = "logged";
    public static final String FIREBASE_USER_DB = "USER_TABLE";
    public static final String FIREBASE_CHAT_DB = "CHAT";
    public static final String USER_ID_FIREBASE = "userIdFirebase";
    public static final String FIREBASE_TOKEN = "firebaseToken";
    public static final String TOKEN = "token";
    public static final String USER_EMAIL = "email";
    public static final String CHAT_ELIGIBLE = "chatEligible";
    public static final String KEY_MESSAGE = "message";
    public static final String ROLE = "roleType";
    public static final String STUDENT_ROLE = "STUDENT";
    public static final String TEACHER_ROLE = "TEACHER";
    public static final String ADMIN_ROLE = "ADMIN";
    public static final String EMAIL_FIREBASE = "fireBaseEmail";
    public static final String SSID = "ssid";
    public static final String COUNT = "countNotification";
    public static final String KEY_COLLECTION_CHAT_ADMIN = "CHAT_ADMIN";
    public static final String KEY_SENDER_ID = "senderId";
    public static final String KEY_RECIEVER_ID = "receiverId";
    public static final String KEY_TIME_STAMP = "timeStamp";
    public static final String KEY_COLLECTIONS_CONVERSATION="conversations";
    public static final String KEY_SENDER_NAME="senderName";
    public static final String KEY_RECEIVER_NAME="receiverName";
    public static final String KEY_SUBJECT_NAME="subjectName";
    public static final String KEY_LAST_MESSAGE="lastMessage";
    public static final String KEY_AVAILABLITY="online";
    public static final String AUTHORIZATION="Authorization";
    public static final String KEY_TEACHER_ID="teacherId";
    public static final String KEY_STUDENT_ID="studentId";

    public static final String KEY_ADMIN_ID="adminId";
    public static final String CONTENT_TYPE="Content-Type";
    public static final String REMOTE_MESSAGE_DATA="data";
    public static final String REGISTRATION_IDS="registration_ids";

    public static HashMap<String, String> remoteMessageHeaders = null;
    public static HashMap<String, String> getRemoteMessageHeaders(){
        if (remoteMessageHeaders== null){
            remoteMessageHeaders = new HashMap<>();
            remoteMessageHeaders.put(AUTHORIZATION, "key=AAAAp1ToAyc:APA91bEQ8MZa6IplBbG6QlgvxeJ5joc7W7ylLYdGnK4_KAP8i0rWcYiaiUjnmt2-PdE2gfPqqQaPSXAtzDG0RWqX0PYkL-IZMXBlEG3vGnJwfdAVmPE1t9G7ouYeKZfxrJRZpATl0l8u");
            remoteMessageHeaders.put(CONTENT_TYPE,"application/json");
        }
        return remoteMessageHeaders;
    }


}
