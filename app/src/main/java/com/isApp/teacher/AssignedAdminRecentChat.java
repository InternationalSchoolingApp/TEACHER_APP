package com.isApp.teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.isApp.teacher.Adapter.RecentConversationAdminAdapter;
import com.isApp.teacher.Adapter.RecentConversionAdapter;
import com.isApp.teacher.Model.AdminStudentMappingModel;
import com.isApp.teacher.Model.ChatMessage;
import com.isApp.teacher.Model.CheckAssignedAdminModel;
import com.isApp.teacher.Network.ApiInterface;
import com.isApp.teacher.Network.NetworkChangeListener;
import com.isApp.teacher.Network.Retrofit.RetroFitClient;
import com.isApp.teacher.common.ColorOfStatusAndNavBar;
import com.isApp.teacher.common.Constants;
import com.isApp.teacher.databinding.ActivityAssignedAdminRecentChatBinding;
import com.isApp.teacher.sharedPreference.PreferenceManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AssignedAdminRecentChat extends AppCompatActivity {

    private ActivityAssignedAdminRecentChatBinding binding;

    private PreferenceManager preferenceManager;
    ProgressDialog progressDialog;
    Integer userId;

    private List<ChatMessage> conversation;
    private RecentConversationAdminAdapter recentConversationAdminAdapter;
    private FirebaseFirestore firebaseFirestore;

    String senderIdClass;




    String adminEmail, adminUserId, adminName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAssignedAdminRecentChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        init();
        loadDetails();
        getAdmin();
        setListners();
        listenConversations();



    }

    private void listenConversations() {


        firebaseFirestore.collection("ADMIN_TEACHER_CONVERSATION")
                .whereEqualTo(Constants.KEY_SENDER_ID, preferenceManager.getString(Constants.USER_EMAIL).toLowerCase())
                .addSnapshotListener(eventListener);
        firebaseFirestore.collection("ADMIN_TEACHER_CONVERSATION")
                .whereEqualTo(Constants.KEY_RECIEVER_ID, preferenceManager.getString(Constants.USER_EMAIL).toLowerCase())
                .addSnapshotListener(eventListener);
    }


    private EventListener<QuerySnapshot> eventListener = (value , error) ->{
        if(error!=null){
            return;
        }
        if (value!= null){
            for(DocumentChange documentChange : value.getDocumentChanges()){
                if (documentChange.getType() == DocumentChange.Type.ADDED){
                    String senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                    String receiverId = documentChange.getDocument().getString(Constants.KEY_RECIEVER_ID);
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.senderId = senderId;
                    chatMessage.receiverId = receiverId;
                    if (senderId.equals(senderIdClass)){
                        chatMessage.conversionName = documentChange.getDocument().getString(Constants.KEY_RECEIVER_NAME);
                        chatMessage.conversionId = documentChange.getDocument().getString(Constants.KEY_RECIEVER_ID);
                    }else{
                        chatMessage.conversionName = documentChange.getDocument().getString(Constants.KEY_SENDER_NAME);
                        chatMessage.conversionId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                    }
                    chatMessage.message = documentChange.getDocument().getString(Constants.KEY_LAST_MESSAGE);
                    chatMessage.dateObject = documentChange.getDocument().getDate(Constants.KEY_TIME_STAMP);
                    chatMessage.adminUserName = documentChange.getDocument().getString(Constants.KEY_RECEIVER_NAME);
                    chatMessage.adminUserId  = documentChange.getDocument().getString(Constants.ADMIN_ID);
                    chatMessage.adminEmail  = documentChange.getDocument().getString("adminEmail");
                    conversation.add(chatMessage);
                }else if (documentChange.getType() == DocumentChange.Type.MODIFIED){
                    for (int i = 0;i<conversation.size();i++){
                        String senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                        String receiverId = documentChange.getDocument().getString(Constants.KEY_RECIEVER_ID);
                        if(conversation.get(i).senderId.equals(senderId) && conversation.get(i).receiverId.equals(receiverId)){
                            conversation.get(i).message = documentChange.getDocument().getString(Constants.KEY_LAST_MESSAGE);
                            conversation.get(i).dateObject = documentChange.getDocument().getDate(Constants.KEY_TIME_STAMP);
                            conversation.get(i).adminUserName = documentChange.getDocument().getString(Constants.KEY_RECEIVER_NAME);
                            conversation.get(i).adminUserId = documentChange.getDocument().getString(Constants.ADMIN_ID);
                            conversation.get(i).adminEmail = documentChange.getDocument().getString("adminEmail");
                            break;
                        }
                    }
                }
            }
            Collections.sort(conversation, (obj1, obj2)-> obj2.dateObject.compareTo(obj1.dateObject));
            recentConversationAdminAdapter.notifyDataSetChanged();
            binding.recentRecyclerView.smoothScrollToPosition(0);
            binding.recentRecyclerView.setVisibility(View.VISIBLE);

        }
    };

    private void setListners() {
        binding.startChatBtn.setOnClickListener(v->{
            progressDialog.show();
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
                        Toast.makeText(AssignedAdminRecentChat.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AdminStudentMappingModel> call, Throwable t) {
                    Toast.makeText(AssignedAdminRecentChat.this, "Sorry Technical glitch occur", Toast.LENGTH_SHORT).show();
                }
            });

        });
        binding.recentTeacherBackButton.setOnClickListener(v->onBackPressed());
        binding.assignedAdmin.setOnClickListener(v->{
            Intent intent = new Intent(v.getContext(), ChatWithAdmin.class);
            intent.putExtra("adminUserId", adminUserId);
            intent.putExtra("email", adminEmail.toLowerCase());
            intent.putExtra("name",adminName);
            v.getContext().startActivity(intent);
        });


    }

    private void getAdmin() {

        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        CheckAssignedAdminModel checkAssignedAdminModel = new CheckAssignedAdminModel(userId);
        Call<CheckAssignedAdminModel> call = apiInterface.checkAssignedAdminModel(checkAssignedAdminModel);
        call.enqueue(new Callback<CheckAssignedAdminModel>() {
            @Override
            public void onResponse(Call<CheckAssignedAdminModel> call, Response<CheckAssignedAdminModel> response) {
                if (response.body().getStatus().equals("success")){
                    binding.name.setVisibility(View.GONE);
                    binding.assAdmin.setText("No Assigned Admin");
                    progressDialog.dismiss();

                }else{
                    adminName = response.body().getAdminName();
                    adminUserId = String.valueOf(response.body().getAdminId());
                    adminEmail = response.body().getAdminEmail();
                    binding.startChatBtn.setVisibility(View.GONE);
                    progressDialog.dismiss();
                    Toast.makeText(AssignedAdminRecentChat.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CheckAssignedAdminModel> call, Throwable t) {

            }
        });

    }

    private void loadDetails() {
        userId = Integer.valueOf(preferenceManager.getString(Constants.USER_ID));
    }

    public void init(){
        preferenceManager = new PreferenceManager(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading");
        ColorOfStatusAndNavBar colorOfStatusAndNavBar = new ColorOfStatusAndNavBar();
        colorOfStatusAndNavBar.loginAndForgetPassword(this);
        senderIdClass= preferenceManager.getString(Constants.USER_EMAIL).toLowerCase();
        conversation = new ArrayList<>();
        recentConversationAdminAdapter = new RecentConversationAdminAdapter(conversation);
        binding.recentRecyclerView.setAdapter(recentConversationAdminAdapter);
        firebaseFirestore = FirebaseFirestore.getInstance();
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