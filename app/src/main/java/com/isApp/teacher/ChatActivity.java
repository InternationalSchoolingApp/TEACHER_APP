package com.isApp.teacher;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.isApp.teacher.Adapter.ChatAdapter;
import com.isApp.teacher.Model.ChatMessage;
import com.isApp.teacher.common.Constants;
import com.isApp.teacher.databinding.ActivityChatBinding;
import com.isApp.teacher.sharedPreference.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class ChatActivity extends AppCompatActivity {

    private List<ChatMessage> chatMessages;
    private ChatAdapter chatAdapter;
    private FirebaseFirestore database;
    private PreferenceManager preferenceManager;
    private ActivityChatBinding binding;
    private String studentName, subject, studentId, studentEmail, senderId;
    private String conversionId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            studentId = extra.getString("studentId");
            studentName = extra.getString("studentName");
            subject = extra.getString("subject");
            studentEmail = extra.getString("email");
        }
        init();
        setListeners();
        listenMessage();




    }

    void init(){

        preferenceManager = new PreferenceManager(getApplicationContext());
        senderId = preferenceManager.getString(Constants.USER_ID_FIREBASE);
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
        database.collection("CHAT").add(message);
        if (conversionId != null) {
            updateConversion(binding.chatEdittext.getText().toString());
        } else {
            HashMap<String, Object> conversion = new HashMap<>();
            conversion.put(Constants.KEY_SENDER_ID, senderId);
            conversion.put(Constants.KEY_SENDER_NAME, preferenceManager.getString(Constants.NAME));
            conversion.put(Constants.KEY_RECIEVER_ID, studentEmail.toLowerCase());
            conversion.put(Constants.KEY_RECEIVER_NAME, studentName);
            conversion.put(Constants.KEY_STUDENT_ID, studentId);
            conversion.put(Constants.KEY_SUBJECT_NAME, subject);
            conversion.put(Constants.KEY_LAST_MESSAGE, binding.chatEdittext.getText().toString());
            conversion.put(Constants.KEY_TIME_STAMP, new Date());
            addConversion(conversion);
        }
        binding.chatEdittext.setText(null);
    }

    private void listenMessage(){
//        if (studentFireBaseUserId != null){
//
//        }else{
//            studentFireBaseUserId = studentEmail;
//        }

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
        database.collection(Constants.KEY_COLLECTIONS_CONVERSATION)
                .add(conversion)
                .addOnSuccessListener(documentReference -> conversionId = documentReference.getId());
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
}