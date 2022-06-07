package me.solo_team.futureleader;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class FireBaseSendings extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        System.out.println("TOKEN: "+token );
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        System.out.println(remoteMessage.getData());
        if (remoteMessage.getData().size() > 0) {
            System.out.println("Message data payload: " + remoteMessage.getData());
            handleNow(new JSONObject(remoteMessage.getData()));
        }
    }

    // Идентификатор канала
    private void handleNow(JSONObject data) {
        try {
            MyNotificationManager.getInstance(this).displayNotification(data.getString("title"),data.getString("body"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
