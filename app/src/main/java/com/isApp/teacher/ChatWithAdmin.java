package com.isApp.teacher;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.isApp.teacher.Adapter.ChatAdapter;
import com.isApp.teacher.Model.AdminStudentMappingModel;
import com.isApp.teacher.Model.ChatMessage;
import com.isApp.teacher.Model.CheckAssignedAdminModel;
import com.isApp.teacher.Network.ApiInterface;
import com.isApp.teacher.Network.NetworkChangeListener;
import com.isApp.teacher.Network.Retrofit.RetroFitClient;
import com.isApp.teacher.common.ColorOfStatusAndNavBar;
import com.isApp.teacher.common.Constants;
import com.isApp.teacher.databinding.ActivityChatWithAdminBinding;
import com.isApp.teacher.fcmApi.FcmApiClient;
import com.isApp.teacher.fcmApi.FcmApiInterface;
import com.isApp.teacher.sharedPreference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatWithAdmin extends AppCompatActivity {

    private ActivityChatWithAdminBinding binding;

    private List<ChatMessage> chatMessages;
    private ChatAdapter chatAdapter;
    private FirebaseFirestore database;
    private PreferenceManager preferenceManager;
    private String adminName, adminEmail, adminUserId,  senderId, recieverFcmToken;
    private String conversionId = null;

    Integer userId;

    ProgressDialog progressDialog;

    private Boolean online = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatWithAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ColorOfStatusAndNavBar colorOfStatusAndNavBar = new ColorOfStatusAndNavBar();
        colorOfStatusAndNavBar.chat(this);





        binding.chatScreenStudentName.setText("School Admin");

        init();
        getAdmin();



        setListeners();




    }
    private void getAdmin() {

        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        CheckAssignedAdminModel checkAssignedAdminModel = new CheckAssignedAdminModel(userId);
        Call<CheckAssignedAdminModel> call = apiInterface.checkAssignedAdminModel(checkAssignedAdminModel);
        call.enqueue(new Callback<CheckAssignedAdminModel>() {
            @Override
            public void onResponse(Call<CheckAssignedAdminModel> call, Response<CheckAssignedAdminModel> response) {
                if (response.body().getStatus().equals("success")){
                    assignAdmin();
                    progressDialog.dismiss();

                }else{
                    adminName = response.body().getAdminName();
                    adminUserId = String.valueOf(response.body().getAdminId());
                    adminEmail = response.body().getAdminEmail();
                    Log.d("ADMIN", "onResponse: "+ adminEmail);
                    progressDialog.dismiss();

                    listenAvailabilityOfReceiver();
                    listenMessage();

                }
            }

            @Override
            public void onFailure(Call<CheckAssignedAdminModel> call, Throwable t) {

            }
        });

    }

    private void assignAdmin() {
        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        AdminStudentMappingModel adminStudentMappingModel = new AdminStudentMappingModel(userId);
        Call<AdminStudentMappingModel> call = apiInterface.adminStudentMappingModel(adminStudentMappingModel);
        call.enqueue(new Callback<AdminStudentMappingModel>() {
            @Override
            public void onResponse(Call<AdminStudentMappingModel> call, Response<AdminStudentMappingModel> response) {
                if(response.body().getStatus().equals("success")){
                    progressDialog.dismiss();
                    finish();
                    startActivity(getIntent());
                }else{
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<AdminStudentMappingModel> call, Throwable t) {

            }
        });
    }

    private void setListeners(){
        binding.backButtonChat.setOnClickListener(c -> onBackPressed());
        binding.sendButtonChat.setOnClickListener(v -> {
            if (!binding.chatEdittext.getText().toString().equals("")){
                sendMessage();
            }
        });
    }

    void init(){
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading");
        preferenceManager = new PreferenceManager(getApplicationContext());
        userId = Integer.valueOf(preferenceManager.getString(Constants.USER_ID));
        senderId = preferenceManager.getString(Constants.USER_ID_FIREBASE);
        binding.chatScreenStudentName.setText("School Admin");
        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatMessages, senderId);
        database = FirebaseFirestore.getInstance();
        binding.chatContent.setAdapter(chatAdapter);
    }

    private void sendMessage() {

        HashMap<String, Object> message = new HashMap<>();
        message.put("senderId", senderId);
        message.put("receiverId", adminEmail);
        message.put("teacherEmail", senderId);
        message.put("adminEmail", adminEmail);
        message.put("message", binding.chatEdittext.getText().toString());
        message.put("timeStamp", new Date());
        message.put("type", "ADMIN_TEACHER");
        database.collection(Constants.KEY_COLLECTION_CHAT_ADMIN).add(message);
        if (conversionId != null) {
            updateConversion(binding.chatEdittext.getText().toString());
        } else {
            HashMap<String, Object> conversion = new HashMap<>();
            conversion.put(Constants.KEY_SENDER_ID, senderId);
            conversion.put(Constants.KEY_SENDER_NAME, preferenceManager.getString(Constants.NAME));
            conversion.put(Constants.KEY_RECIEVER_ID, adminEmail);
            conversion.put(Constants.KEY_RECEIVER_NAME, adminName);
            conversion.put("adminName", adminName);
            conversion.put("adminEmail", adminEmail);
            conversion.put("teacherName",preferenceManager.getString(Constants.NAME));
            conversion.put("teacherEmail",senderId);
            conversion.put(Constants.KEY_LAST_MESSAGE, binding.chatEdittext.getText().toString());
            conversion.put(Constants.KEY_TIME_STAMP, new Date());
            addConversion(conversion);
        }
        if(!online){
            try{

                JSONArray tokens = new JSONArray();
                tokens.put(recieverFcmToken);

                JSONObject data = new JSONObject();
                data.put(Constants.USER_EMAIL, preferenceManager.getString(Constants.USER_EMAIL));
                data.put(Constants.NAME, preferenceManager.getString(Constants.NAME));
                data.put(Constants.FIREBASE_TOKEN, preferenceManager.getString(Constants.FIREBASE_TOKEN));
                data.put(Constants.KEY_MESSAGE, binding.chatEdittext.getText().toString());
                data.put("channel", "TEACHER_ADMIN");

                JSONObject body = new JSONObject();
                body.put(Constants.REMOTE_MESSAGE_DATA, data);
                body.put(Constants.REGISTRATION_IDS, tokens);

                sendNotification(body.toString());

            }catch ( Exception exception){

            }
        }
        binding.chatEdittext.setText(null);
    }

    private void listenAvailabilityOfReceiver() {
        Log.d("CHECKING FCM", "listenAvailabilityOfReceiver: " + adminEmail);
        database.collection(Constants.FIREBASE_USER_DB).document(""+adminEmail).addSnapshotListener(ChatWithAdmin.this, ((value, error) -> {

            if (error != null) {
                return;
            }
            if (value != null) {
                if (value.getLong(Constants.KEY_AVAILABLITY) != null) {

                }
                if (value.get(Constants.FIREBASE_TOKEN) != null) {
                    recieverFcmToken = Objects.requireNonNull(value.get(Constants.FIREBASE_TOKEN).toString());
                }
            }
            if (value.get(Constants.FIREBASE_TOKEN) != null) {
                recieverFcmToken = Objects.requireNonNull(value.get(Constants.FIREBASE_TOKEN).toString());
                Log.d("FCM TOKEN", "listenAvailabilityOfReceiver: " + recieverFcmToken);
            } else {
                Log.d("FCM TOKEN NULL", "listenAvailabilityOfReceiver: ");
            }
            if (online) {
                binding.textOnline.setVisibility(View.VISIBLE);
            } else {
                binding.textOnline.setVisibility(View.GONE);
            }

        }));
    }


    private String getReadableDateTime(Date date ){
        return  new SimpleDateFormat("MMMM dd, yyyy - hh:mm a", Locale.getDefault()).format(date);
    }

    private final EventListener<QuerySnapshot> eventListener = (value, error) ->{
        if(error != null){
            return;
        }
        if(value != null){
            int count = chatMessages.size();
            for (DocumentChange documentChange : value.getDocumentChanges()){
                if (documentChange.getType() == DocumentChange.Type.ADDED){
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.senderId = documentChange.getDocument().getString("senderId");
                    chatMessage.receiverId = documentChange.getDocument().getString("receiverId");
                    chatMessage.message = documentChange.getDocument().getString("message");
                    chatMessage.time = getReadableDateTime(documentChange.getDocument().getDate("timeStamp"));
                    chatMessage.dateObject = documentChange.getDocument().getDate("timeStamp");
                    chatMessages.add(chatMessage);
                }
            }
            Collections.sort(chatMessages, (obj1, obj2) -> obj1.dateObject.compareTo(obj2.dateObject));
            if(count == 0){
                chatAdapter.notifyDataSetChanged();
            }else{
                chatAdapter.notifyItemRangeInserted(chatMessages.size(), chatMessages.size());
                binding.chatContent.smoothScrollToPosition(chatMessages.size()-1);
            }
            binding.chatContent.setVisibility(View.VISIBLE);
        }

        if (conversionId == null) {
            checkForConversion();
        }


    };

    private void listenMessage(){


        Log.d("ADMIN", "listenMessage: " + adminEmail);
        Log.d("TEACHER", "listenMessage: " + senderId);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_CHAT_ADMIN)
                .whereEqualTo("senderId", senderId)
                .whereEqualTo("receiverId", adminEmail)
                .addSnapshotListener(eventListener);
        database.collection(Constants.KEY_COLLECTION_CHAT_ADMIN)
                .whereEqualTo("senderId", adminEmail)
                .whereEqualTo("receiverId", senderId)
                .addSnapshotListener(eventListener);

    }



    private void checkForConversion() {
        if (chatMessages.size() != 0) {
            checkForConversionRemotely(senderId);
            checkForConversionRemotely(adminEmail);
        }
    }

    private void checkForConversionRemotely(String senderId) {
        database.collection("ADMIN_TEACHER_CONVERSATION").whereEqualTo(Constants.KEY_SENDER_ID, senderId).whereEqualTo(Constants.KEY_RECIEVER_ID, adminEmail).get().addOnCompleteListener(conversionCompleteListener);
    }


    private final OnCompleteListener<QuerySnapshot> conversionCompleteListener = task -> {
        if (task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size() > 0) {
            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
            conversionId = documentSnapshot.getId();
        }
    };


    private void addConversion(HashMap<String, Object> conversion) {
        CollectionReference conversation = database.collection("ADMIN_TEACHER_CONVERSATION");
        conversation.document(""+adminEmail+" - "+preferenceManager.getString(Constants.USER_EMAIL).toLowerCase()).set(conversion).addOnSuccessListener(documentReference -> conversionId = (""+preferenceManager.getString(Constants.USER_EMAIL).toLowerCase()+" - "+adminEmail)).addOnFailureListener(exception->{
        });

    }

    private void updateConversion(String message) {
        DocumentReference documentReference = database.collection("ADMIN_TEACHER_CONVERSATION").document(conversionId);
        documentReference.update(Constants.KEY_LAST_MESSAGE, message, Constants.KEY_TIME_STAMP, new Date(), Constants.KEY_SENDER_ID, senderId,
                Constants.KEY_SENDER_NAME, preferenceManager.getString(Constants.NAME),
                Constants.KEY_RECIEVER_ID, adminEmail,
                "adminEmail", adminEmail,
                "adminName", adminName,
                "teacherName",preferenceManager.getString(Constants.NAME),
                Constants.ADMIN_ID, preferenceManager.getString(Constants.USER_ID));
    }

    private void sendNotification(String messageBody) {

        FcmApiClient.getClient().create(FcmApiInterface.class).sendMessage(Constants.getRemoteMessageHeaders(), messageBody).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {

                if (response.isSuccessful()) {
                    try {
                        if (response.body() != null) {
                            JSONObject responseJson = new JSONObject(response.body());
                            JSONArray results = responseJson.getJSONArray("results");
                            if (responseJson.getInt("failure") == 1) {
                                JSONObject error = (JSONObject) results.get(0);
                                return;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {

                }
            }


            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

            }
        });

    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListner, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListner);
        super.onStop();
    }

    NetworkChangeListener networkChangeListner = new NetworkChangeListener();



}