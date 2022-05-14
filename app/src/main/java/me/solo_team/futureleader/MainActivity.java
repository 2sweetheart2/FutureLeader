package me.solo_team.futureleader;


import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Constants.res = getResources();
        // КРЧ ТУТ НИЧЕГО НЕ ТРОГАЕМ, ЭТО ОСНОВНОЕ ОКНО В КОТОРОМ У НАС ВСЁ: НАЖНЯЯ ПАНЕЛЬ И ОСТАЛЬНЫЕ ФРАГМЕНТЫ
        super.onCreate(savedInstanceState);
        // метод для опрделения ширины экрана
        computeWindowSizeClasses();
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_news, R.id.navigation_menu, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

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