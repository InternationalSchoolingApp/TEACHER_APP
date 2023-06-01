package com.isApp.teacher.Firebase;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.isApp.teacher.ChatWithAdmin;
import com.isApp.teacher.Model.User;
import com.isApp.teacher.R;
import com.isApp.teacher.RecentChatActivity;
import com.isApp.teacher.common.Constants;
import com.isApp.teacher.sharedPreference.PreferenceManager;
import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private PreferenceManager preferenceManager;
    private FirebaseFirestore firebaseFirestore;
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        firebaseFirestore = FirebaseFirestore.getInstance();
        Log.d("FCM", "Token: " + token);
        preferenceManager = new PreferenceManager(getApplicationContext());
        preferenceManager.putString(Constants.FIREBASE_TOKEN, token);
        updateFireBaseToken(token);
    }
    private void updateFireBaseToken(String token) {
        if (token != null && !token.equals("")) {
            preferenceManager.putString(Constants.FIREBASE_TOKEN, token);
            DocumentReference docRef = firebaseFirestore.collection(Constants.FIREBASE_USER_DB).document(token);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    docRef.update("firebaseToken", preferenceManager.getString(Constants.FIREBASE_TOKEN));
                }
            });
        }
    }
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        User user = new User();
        user.id = message.getData().get(Constants.USER_EMAIL);
        user.name = message.getData().get(Constants.NAME);
        user.token = message.getData().get(Constants.FIREBASE_TOKEN);
        String channelinmessage = message.getData().get("channel");
        if (channelinmessage.equals("TEACHER_ADMIN")){
            int notificationId = new Random().nextInt();
            String channelId = "chat_message";
            Intent intent = new Intent(this, ChatWithAdmin.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);
            builder.setSmallIcon(R.drawable.islogomipmap);
            builder.setContentTitle(user.name);
            builder.setContentText(message.getData().get(Constants.KEY_MESSAGE));
            builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message.getData().get(Constants.KEY_MESSAGE)));
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            builder.setContentIntent(pendingIntent);
            builder.setAutoCancel(true);
            CharSequence channelName = "Chat Message";
            String channelDescription = "This notification channel used for chat message";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription(channelDescription);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            notificationManagerCompat.notify(notificationId, builder.build());
        }
        int notificationId = new Random().nextInt();
        String channelId = "chat_message";
        Intent intent = new Intent(this, RecentChatActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);
        builder.setSmallIcon(R.drawable.islogomipmap);
        builder.setContentTitle(user.name);
        builder.setContentText(message.getData().get(Constants.KEY_MESSAGE));
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message.getData().get(Constants.KEY_MESSAGE)));
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "Chat Message";
            String channelDescription = "This notification channel used for chat message";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription(channelDescription);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManagerCompat.notify(notificationId, builder.build());
    }
}
