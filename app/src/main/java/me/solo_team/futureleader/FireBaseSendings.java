package me.solo_team.futureleader;

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
        super.onNewToken(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        System.out.println(remoteMessage.getData());
        handleNow(new JSONObject(remoteMessage.getData()));
    }

    // Идентификатор уведомления
    private static final int NOTIFY_ID = 101;

    // Идентификатор канала
    private void handleNow(JSONObject data) {
        try {
            NotificationCompat.Builder builder
                    = new NotificationCompat.Builder(getApplicationContext(), "fcm_default_channel")
                        .setSmallIcon(R.drawable.resize_300x0)
                        .setContentTitle(data.getString("title"))
                        .setContentText(data.getString("body"))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager =
                    NotificationManagerCompat.from(getApplicationContext());
            notificationManager.notify(101, builder.build());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
