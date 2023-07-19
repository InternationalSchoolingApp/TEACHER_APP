package com.isApp.teacher;


import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.isApp.teacher.Adapter.ChatAdapter;
import com.isApp.teacher.Model.ChatMessage;
import com.isApp.teacher.Network.NetworkChangeListener;
import com.isApp.teacher.common.BaseActivity;
import com.isApp.teacher.common.ColorOfStatusAndNavBar;
import com.isApp.teacher.common.Constants;
import com.isApp.teacher.databinding.ActivityChatBinding;
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


public class ChatActivity extends BaseActivity {

    private List<ChatMessage> chatMessages;
    private ChatAdapter chatAdapter;
    private FirebaseFirestore database;
    private PreferenceManager preferenceManager;
    private ActivityChatBinding binding;

    private Boolean online = false;
    private Boolean hold = false;
    private String studentName, subject, studentId, studentEmail, senderId, recieverFcmToken;
    private String conversionId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ColorOfStatusAndNavBar colorOfStatusAndNavBar = new ColorOfStatusAndNavBar();
        colorOfStatusAndNavBar.chat(this);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            studentId = extra.getString("studentId");
            studentName = extra.getString("studentName");
            subject = extra.getString("subject");
            studentEmail = extra.getString("email").toLowerCase();
            Log.d("VALUES", "onCreate: "+studentId+", "+studentName+", "+subject+", "+studentEmail);
        }
        init();
        setListeners();
        listenMessage();





    }

    void init(){

        preferenceManager = new PreferenceManager(getApplicationContext());
        senderId = preferenceManager.getString(Constants.USER_ID_FIREBASE).toLowerCase();
//        studentFireBaseUserId =  getTeacherFirebaseUserId(studentEmail);
        binding.chatScreenStudentName.setText(studentName);
        binding.chatStudentSubjectName.setText(subject);
        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatMessages, senderId);
        database = FirebaseFirestore.getInstance();
        binding.chatContent.setAdapter(chatAdapter);
    }

    private String getTeacherFirebaseUserId(String email) {

        final String[] teacherFirebaseUserId = {""};
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference docRef = database.collection("USER_TABLE").document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().getData()!= null) {
                        teacherFirebaseUserId[0] = task.getResult().getId();
                        Log.d("FCM Teacher USer", "Exist : " + task.getResult().getData());
                    } else {
                        Log.d("FCM Teacher User", "Not Found");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
        return teacherFirebaseUserId[0];
    }

    private void setListeners(){
        binding.backButtonChat.setOnClickListener(c -> onBackPressed());
        binding.sendButtonChat.setOnClickListener(v -> {
            if (!binding.chatEdittext.getText().toString().equals("")){
                sendMessage();
            }
        });
    }
    private void sendMessage(){

        HashMap<String, Object> message = new HashMap<>();
        message.put("senderId", senderId);
        message.put("receiverId", studentEmail);
        message.put("message", binding.chatEdittext.getText().toString());
        message.put("timeStamp", new Date());
        message.put("teacherEmail", senderId);
        message.put("studentEmail", studentEmail);
        database.collection("CHAT").add(message);
        if(!online){
            try{

                JSONArray tokens = new JSONArray();
                tokens.put(recieverFcmToken);

                JSONObject data = new JSONObject();
                data.put(Constants.USER_EMAIL, preferenceManager.getString(Constants.USER_EMAIL));
                data.put(Constants.NAME, preferenceManager.getString(Constants.NAME));
                data.put(Constants.FIREBASE_TOKEN, preferenceManager.getString(Constants.FIREBASE_TOKEN));
                data.put(Constants.KEY_MESSAGE, binding.chatEdittext.getText().toString());
                data.put("channel", "STUDENT_TEACHER");

                JSONObject body = new JSONObject();
                body.put(Constants.REMOTE_MESSAGE_DATA, data);
                body.put(Constants.REGISTRATION_IDS, tokens);

                sendNotification(body.toString());

            }catch ( Exception exception){

            }
        }
        if (conversionId != null) {
            updateConversion(binding.chatEdittext.getText().toString());
        } else {
            HashMap<String, Object> conversion = new HashMap<>();
            conversion.put(Constants.KEY_SENDER_ID, senderId);
            conversion.put(Constants.KEY_SENDER_NAME, preferenceManager.getString(Constants.NAME));
            conversion.put(Constants.KEY_RECIEVER_ID, studentEmail.toLowerCase());
            conversion.put(Constants.KEY_RECEIVER_NAME, studentName);
            conversion.put(Constants.KEY_STUDENT_ID, studentId);
            conversion.put("teacherEmail", senderId);
            conversion.put("studentEmail", studentEmail.toLowerCase());
            conversion.put("studentName", studentName);
            conversion.put("teacherName", preferenceManager.getString(Constants.NAME));
            conversion.put(Constants.KEY_SUBJECT_NAME, subject);
            conversion.put(Constants.KEY_TEACHER_ID, String.valueOf(preferenceManager.getInt(Constants.TEACHER_ID)));
            conversion.put(Constants.KEY_LAST_MESSAGE, binding.chatEdittext.getText().toString());
            conversion.put(Constants.KEY_TIME_STAMP, new Date());
            addConversion(conversion);
        }
        binding.chatEdittext.setText(null);
    }

    private void listenMessage(){


        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("CHAT")
                .whereEqualTo("senderId", senderId)
                .whereEqualTo("receiverId", studentEmail)
                .addSnapshotListener(eventListener);
        database.collection("CHAT")
                .whereEqualTo("senderId", studentEmail)
                .whereEqualTo("receiverId", senderId)
                .addSnapshotListener(eventListener);
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

    };

    private String getReadableDateTime(Date date ){
        return  new SimpleDateFormat("MMMM dd, yyyy - hh:mm a", Locale.getDefault()).format(date);
    }

    private void checkForConversion() {
        if (chatMessages.size() != 0) {
            checkForConversionRemotely(senderId, studentEmail.toLowerCase());
            checkForConversionRemotely(studentEmail.toLowerCase(), senderId);
        }
    }


    private void addConversion(HashMap<String, Object> conversion) {
        CollectionReference conversation = database.collection(Constants.KEY_COLLECTIONS_CONVERSATION);
        conversation.document(""+studentEmail.toLowerCase()+" - "+preferenceManager.getString(Constants.USER_EMAIL).toLowerCase()).set(conversion).addOnSuccessListener(documentReference -> conversionId = (""+studentEmail.toLowerCase()+" - "+preferenceManager.getString(Constants.USER_EMAIL).toLowerCase())).addOnFailureListener(exception->{

        });
    }

    private void updateConversion(String message) {
        DocumentReference documentReference = database.collection(Constants.KEY_COLLECTIONS_CONVERSATION).document(conversionId);
        documentReference.update(Constants.KEY_LAST_MESSAGE, message, Constants.KEY_TIME_STAMP, new Date());
    }

    private void checkForConversionRemotely(String senderId, String receiverId) {
        Task<QuerySnapshot> querySnapshotTask = database.collection(Constants.KEY_COLLECTIONS_CONVERSATION).whereEqualTo(Constants.KEY_SENDER_ID, senderId).whereEqualTo(Constants.KEY_RECIEVER_ID, studentEmail.toLowerCase()).get().addOnCompleteListener(conversionCompleteListener);
    }

    private final OnCompleteListener<QuerySnapshot> conversionCompleteListener = task -> {
        if (task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size() > 0) {
            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
            conversionId = documentSnapshot.getId();
        }
    };

    private void listenAvailabilityOfReceiver() {
        database.collection(Constants.FIREBASE_USER_DB).document(""+studentEmail.toLowerCase()).addSnapshotListener(ChatActivity.this, ((value, error) -> {
            if (error != null) {
                return;
            }
            if (value != null) {
                if (value.getLong(Constants.KEY_AVAILABLITY) != null) {
                    int available = Objects.requireNonNull(value.getLong(Constants.KEY_AVAILABLITY).intValue());
                    online = available == 1;
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

    @Override
    protected void onResume() {
        super.onResume();
        listenAvailabilityOfReceiver();
        listenHoldOfChat();
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


    public void listenHoldOfChat(){
        database.collection(Constants.KEY_COLLECTIONS_CONVERSATION).document(""+studentEmail.toLowerCase()+" - "+senderId.toLowerCase()).addSnapshotListener(ChatActivity.this, ((value, error) -> {
            if (error != null) {
                return;
            }
            if (value != null) {
                if (value.getBoolean(Constants.ON_HOLD) != null) {
                    Boolean onhold = Objects.requireNonNull(value.getBoolean(Constants.ON_HOLD).booleanValue());
                    if (onhold){
                        binding.sendButtonChat.setVisibility(View.GONE);
                        binding.chatEdittext.setVisibility(View.GONE);
                        Toast.makeText(this, "Chat is on hold By School Admin", Toast.LENGTH_SHORT).show();
                    }else{
                        binding.sendButtonChat.setVisibility(View.VISIBLE);
                        binding.chatEdittext.setVisibility(View.VISIBLE);
                    }

                }
            }

        }));

        }


}