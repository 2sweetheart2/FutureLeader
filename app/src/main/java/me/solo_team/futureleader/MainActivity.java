package me.solo_team.futureleader;


import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;

import io.socket.emitter.Emitter;
import me.solo_team.futureleader.API.websocket.WebScoketClient;
import me.solo_team.futureleader.Objects.Chat;
import me.solo_team.futureleader.Objects.Message;
import me.solo_team.futureleader.stuff.media_player_stuff.service.PlayerService;
import me.solo_team.futureleader.ui.menu.MenuFragment;
import me.solo_team.futureleader.ui.news.NewsFragment;
import me.solo_team.futureleader.ui.news.open_news.EditNews;
import me.solo_team.futureleader.ui.profile.ProfileFragment;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView navView;
    int id;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Constants.res = getResources();

        WebScoketClient.createConnection();
        System.out.println(WebScoketClient.mSocket.connected());

        WebScoketClient.mSocket.on("chat_invite", onChatInvite);
        //WebScoketClient.mSocket.on("chat_remove", OnChatRemoved);
        WebScoketClient.mSocket.on("new_message", onMessageNew);
        WebScoketClient.mSocket.on("chat_title_update",onChatTitleChange);

        super.onCreate(savedInstanceState);


        computeWindowSizeClasses();
        setContentView(R.layout.activity_main);
        Constants.mainActivity = this;


        navView = findViewById(R.id.nav_view);
        navView.setOnItemSelectedListener(navListener);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);


        id = navView.getSelectedItemId();
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("город", "city");
        map.put("день рождения", "birthday");
        map.put("line", null);
        map.put("должность", "division");
        map.put("подразделение", "post");
        map.put("стаж", "experience");
        map.put("line1", "Контакты");
        map.put("телефон", "phone");
        map.put("email", "email");
        map.put("telegram", "telegram");
        map.put("whatsapp", "whatsapp");
        Constants.user.enums = map;
        System.out.println(map.keySet());
        reCreate();
    }

    NewsFragment newsFragment = new NewsFragment();
    MenuFragment menuFragment = new MenuFragment();
    ProfileFragment profileFragment = new ProfileFragment();


    @SuppressLint("NonConstantResourceId")
    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selected = null;

                switch (item.getItemId()) {
                    case R.id.navigation_news:
                        selected = newsFragment;
                        setTitle("Новости");
                        break;
                    case R.id.navigation_menu:
                        selected = menuFragment;
                        MainActivity.this.setTitle("Меню");
                        break;
                    case R.id.navigation_profile:
                        selected = profileFragment;
                        setTitle("Профиль");
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, selected).commit();
                return true;
            };

    /**
     * это крч метод для обновления {@link ActionBar} - верхней полоски над окном
     * с помощью него, я добавляю знак + в правый угол для людей с уровнем != 0
     * это типо для добавления новостей
     * (а условия на то, что этот + может быть только в меню новости)
     * (ты не думай что я такой умный, я просто скопировал всё из инета, заебался искать на самом деле)
     */
    @SuppressLint({"ResourceType", "NonConstantResourceId"})
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (Constants.user.permission.can_add_new && navView.getSelectedItemId() == R.id.navigation_news) {
            if (menu.size() == 0)
                menu.add(0, 1, 0, "")
                        .setIcon(R.drawable.plus)
                        .setOnMenuItemClickListener(item -> {
                            Intent intent = new Intent(this, EditNews.class);
                            JSONObject o = new JSONObject();
                            try {
                                o.put("title", "");
                                o.put("name", "");
                                o.put("objects", new JSONArray());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Constants.newsCache.curentNew = o;
                            startActivity(intent);
                            return true;
                        })
                        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        } else menu.removeItem(1);
        switch (navView.getSelectedItemId()) {
            case R.id.navigation_news:
                setTitle("Новости");
                break;
            case R.id.navigation_menu:
                setTitle("Меню");
                break;
            case R.id.navigation_profile:
                setTitle("Профиль");
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    public static WindowSizeClass wightwindowSizeClass;

    private void computeWindowSizeClasses() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        WindowSizeClass widthWindowSizeClass;

        if (width < 600f) {
            widthWindowSizeClass = WindowSizeClass.COMPACT;
        } else if (width < 840f) {
            widthWindowSizeClass = WindowSizeClass.MEDIUM;
        } else {
            widthWindowSizeClass = WindowSizeClass.EXPANDED;
        }

        wightwindowSizeClass = widthWindowSizeClass;
    }

    private PlayerService.PlayerServiceBinder playerServiceBinder;
    public static MediaControllerCompat mediaController;
    private MediaControllerCompat.Callback callback;
    private ServiceConnection serviceConnection;

    public void reCreate() {
        callback = new MediaControllerCompat.Callback() {
            @Override
            public void onPlaybackStateChanged(PlaybackStateCompat state) {
            }
        };

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                playerServiceBinder = (PlayerService.PlayerServiceBinder) service;
                mediaController = new MediaControllerCompat(MainActivity.this, playerServiceBinder.getMediaSessionToken());
                mediaController.registerCallback(callback);
                callback.onPlaybackStateChanged(mediaController.getPlaybackState());
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                playerServiceBinder = null;
                if (mediaController != null) {
                    mediaController.unregisterCallback(callback);
                    mediaController = null;
                }
            }
        };

        bindService(new Intent(this, PlayerService.class), serviceConnection, BIND_AUTO_CREATE);
    }


    // SOCKETS STUFF

    public Emitter.Listener onChatInvite = args -> MainActivity.this.runOnUiThread(() -> {
        Chat chat = new Chat((JSONObject) args[0]);
        Constants.chatsCache.chats.add(chat);
        if(Constants.chatListeners.chatInviteCallback!=null)
            Constants.chatListeners.chatInviteCallback.chatInvite();
    });

    public Emitter.Listener onMessageNew = args -> MainActivity.this.runOnUiThread(()->{
        Message message = new Message((JSONObject) args[0]);
        if(Constants.chatListeners.messageCallbacks.containsKey(message.peerId))
            Constants.chatListeners.messageCallbacks.get(message.peerId).onMessage(message);

    });

    public Emitter.Listener onChatTitleChange = args -> MainActivity.this.runOnUiThread(() -> System.out.println("onChatTitleChange: " + args[0]));

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WebScoketClient.mSocket.off("new_message", onMessageNew);
        WebScoketClient.mSocket.off("chat_invite", onChatInvite);
        WebScoketClient.mSocket.off("chat_title_update",onChatTitleChange);
        //WebScoketClient.mSocket.off("chat_removed", OnChatRemoved);
        System.out.println("DESTROY");
        playerServiceBinder = null;
        if (mediaController != null) {
            mediaController.unregisterCallback(callback);
            mediaController.getTransportControls().sendCustomAction("die",new Bundle());
            mediaController = null;
        }
        unbindService(serviceConnection);
        finish();
    }

}