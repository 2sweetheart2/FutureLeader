package me.solo_team.futureleader;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.TreeMap;

import me.solo_team.futureleader.Objects.User;
import me.solo_team.futureleader.ui.WebViewsContent.WebView;

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

        Constants.user.admin_status = 1;

        computeWindowSizeClasses();
        setContentView(R.layout.activity_main);
        Constants.mainActivity = this;
        navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_news, R.id.navigation_menu, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        id = navView.getSelectedItemId();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        findViewById(R.id.floatingActionButton).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WebView.class);
            intent.putExtra("localhost","file:///android_asset/index.html");
            startActivity(intent);
        });
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        map.put("город","city");
        map.put("день рождения","birthday");
        map.put("line",null);
        map.put("должность","division");
        map.put("подразделение","post");
        map.put("стаж","experience");
        map.put("line2","контакты");
        map.put("телефон","phone");
        map.put("email","email");
        map.put("telegram","telegram");
        map.put("whatsapp","whatsapp");
        Constants.user.enums = map;
        System.out.println(map.keySet());

        JSONObject fields = new JSONObject();
        try {
            fields.put("city","Санкт-Петербург");
            fields.put("birthday",Utils.parseDateBirthday("14/12/2004"));
            fields.put("division","участник ШБЛ");
            fields.put("post","Школа Лидеров Будущего > Санкт-Петербург > Набор 2021");
            fields.put("experience","5 месяцев 26 дней (c 01.11.2021)");
            fields.put("phone","+79215518784");
            fields.put("email","gleb.rosin11@gmail.com");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Constants.user.info_fields = fields;
    }

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
        if (Constants.user.admin_status != 0 && navView.getSelectedItemId() == id) {
            if(menu.size()==0)
                menu.add(0, 1, 0, "")
                        .setIcon(R.drawable.plus)
                        .setOnMenuItemClickListener(item -> {
                            System.out.println("CLICK");
                            return true;
                        })
                        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        } else menu.removeItem(1);
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