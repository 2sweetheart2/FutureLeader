package me.solo_team.futureleader;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class FireBaseSendings extends FirebaseMessagingService {

    private static final String TAG = "FireBase";

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "Refreshed token: " + token);
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
        System.out.println("TOKEN: " + token);
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData() != null) {
            JSONObject o = new JSONObject(remoteMessage.getData());
            System.out.println("DATA: "+o);
            if (o.has("chat_id")) {
                int chat_id = 0;
                try {
                    chat_id = Integer.parseInt(o.getString("chat_id"));
                    System.out.println("FIND CHAT WITH ID: "+chat_id);
                    System.out.println(Constants.chatsCache.currentChatId+"  "+chat_id+" "+(Constants.chatsCache.currentChatId == chat_id));
                    if (Constants.chatsCache.currentChatId != chat_id)
                        MyNotificationManager.getInstance(getApplicationContext()).displayNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }else
                MyNotificationManager.getInstance(getApplicationContext()).displayNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());

        }
        else
            MyNotificationManager.getInstance(getApplicationContext()).displayNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());

    }
    

}
