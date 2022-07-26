package me.solo_team.futureleader;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
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

import java.io.File;
import java.util.LinkedHashMap;

import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.menu.MenuFragment;
import me.solo_team.futureleader.ui.news.NewsFragment;
import me.solo_team.futureleader.ui.news.open_news.EditNews;
import me.solo_team.futureleader.ui.profile.ProfileFragment;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView navView;
    int id;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Constants.res = getResources();
        // КРЧ ТУТ НИЧЕГО НЕ ТРОГАЕМ, ЭТО ОСНОВНОЕ ОКНО В КОТОРОМ У НАС ВСЁ: НАЖНЯЯ ПАНЕЛЬ И ОСТАЛЬНЫЕ ФРАГМЕНТЫ
        super.onCreate(savedInstanceState);
        // метод для опрeделения ширины экрана


        computeWindowSizeClasses();
        setContentView(R.layout.activity_main);
        Constants.mainActivity = this;


        navView = findViewById(R.id.nav_view);
        navView.setOnItemSelectedListener(navListener);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);




        id = navView.getSelectedItemId();
        findViewById(R.id.floatingActionButton).setOnClickListener(v -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, 1);
        });
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

    }
    private static final String VIDEO_DIRECTORY = "/demonuts";


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Log.d("what","gale");
                if (data != null) {
                    Uri contentURI = data.getData();
                    File file = Utils.getVideo.getVideoFromUri(contentURI,MainActivity.this);
                }
            }
        }
    }

    NewsFragment newsFragment = new NewsFragment();
    MenuFragment menuFragment = new MenuFragment();
    ProfileFragment profileFragment = new ProfileFragment();


    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selected = null;

                switch (item.getItemId()){
                    case R.id.navigation_news:
                        selected = newsFragment;
                        setTitle("Новости");
                        break;
                    case R.id.navigation_menu:
                        selected = menuFragment;
                        MainActivity.this.setTitle("Меню");
                        break;
                    case R.id.navigation_profile:
                        selected =  profileFragment;
                        setTitle("Профиль");
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,selected).commit();
                return true;
            };
    /**
     * это крч метод для обновления {@link ActionBar} - верхней полоски над окном
     * с помощью него, я добавляю знак + в правый угол для людей с уровнем != 0
     * это типо для добавления новостей
     * (а условия на то, что этот + может быть только в меню новости)
     * (ты не думай что я такой умный, я просто скопировал всё из инета, заебался искать на самом деле)
     */
    @SuppressLint("ResourceType")
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (Constants.user.adminStatus != 0 && navView.getSelectedItemId() == R.id.navigation_news) {
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

    @RequiresApi(api = Build.VERSION_CODES.R)
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


}