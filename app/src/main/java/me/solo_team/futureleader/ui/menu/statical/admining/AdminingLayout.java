package me.solo_team.futureleader.ui.menu.statical.admining;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.menu.statical.admining.layouts.gameifecation.AchievementsLayout;
import me.solo_team.futureleader.ui.menu.statical.admining.layouts.gameifecation.CoinsBank;
import me.solo_team.futureleader.ui.menu.statical.admining.layouts.moderation.GetShopRequests;
import me.solo_team.futureleader.ui.menu.statical.admining.layouts.moderation.Nastavniki;
import me.solo_team.futureleader.ui.menu.statical.admining.layouts.moderation.UnverifyUsers;
import me.solo_team.futureleader.ui.menu.statical.admining.layouts.stat.AllStat;
import me.solo_team.futureleader.ui.menu.statical.admining.layouts.stat.LoginStat;
import me.solo_team.futureleader.ui.menu.statical.admining.layouts.stat.UploadStat;
import me.solo_team.futureleader.ui.menu.statical.admining.layouts.surveys.Ideas;
import me.solo_team.futureleader.ui.menu.statical.admining.layouts.surveys.SurveysLayout;
import me.solo_team.futureleader.ui.menu.statical.admining.layouts.surveys.SurveysStatistic;
import me.solo_team.futureleader.ui.menu.statical.admining.layouts.users.StatusOfUsers;
import me.solo_team.futureleader.ui.menu.statical.admining.layouts.users.UsersLayout;

public class AdminingLayout extends AppCompatActivity {
    private final String[] mGroupsArray = new String[]{"Пользователи",  "Геймификация", "Опросы и ОС", "Модерация", "Статистика", "Интеграция"};
    ExpandableListView expandableListView;
    private final String[] govno1 = new String[]{"Список пользователей",  "Статусы пользователей"};
    private final String[] govno3 = new String[]{"Достижения",  "Валютный банк"};
    private final String[] govno4 = new String[]{"Опросы", "Сессии опросов", "Идеи"};
    private final String[] govno5 = new String[]{"Подтверждение регистрации пользователей","заявки в магазине","наставники"};
    private final String[] govno6 = new String[]{"интеграций не будет, ибо не с чем интегрировать"};
    private final String[] govno7 = new String[]{"Статистика", "Журнал добавления файлов", "Журнал авторизаций"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminig_layout);
        setTitle("Администрирование");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // TODO: этот скоированный пиздец рефракторить я не хочу
        Map<String, String> map;
        // коллекция для групп
        ArrayList<Map<String, String>> groupDataList = new ArrayList<>();
        // заполняем коллекцию групп из массива с названиями групп

        for (String group : mGroupsArray) {
            // заполняем список атрибутов для каждой группы
            map = new HashMap<>();
            map.put("groupName", group); // время года
            groupDataList.add(map);
        }

        // список атрибутов групп для чтения
        String[] groupFrom = new String[]{"groupName"};
        // список ID view-элементов, в которые будет помещены атрибуты групп
        int[] groupTo = new int[]{android.R.id.text1};

        // создаем общую коллекцию для коллекций элементов
        ArrayList<ArrayList<Map<String, String>>> сhildDataList = new ArrayList<>();

        // в итоге получится сhildDataList = ArrayList<сhildDataItemList>

        // создаем коллекцию элементов для первой группы
        // заполняем список атрибутов для каждого элемента
        сhildDataList.add(addCollection(govno1));
        сhildDataList.add(addCollection(govno3));
        сhildDataList.add(addCollection(govno4));
        сhildDataList.add(addCollection(govno5));
        сhildDataList.add(addCollection(govno7));
        сhildDataList.add(addCollection(govno6));
        // список атрибутов элементов для чтения
        String[] childFrom = new String[]{"monthName"};
        // список ID view-элементов, в которые будет помещены атрибуты
        // элементов
        int[] childTo = new int[]{android.R.id.text1};

        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                this, groupDataList,
                android.R.layout.simple_expandable_list_item_1, groupFrom,
                groupTo, сhildDataList, android.R.layout.simple_list_item_1,
                childFrom, childTo);

        expandableListView = findViewById(R.id.expListView);
        expandableListView.setAdapter(adapter);


        HashMap<Integer,HashMap<Integer,Class>> classes = new HashMap<>();
        HashMap<Integer,Class> m = new HashMap<>();
        m.put(0, UsersLayout.class);
        m.put(1, StatusOfUsers.class);
        classes.put(0,m);

        HashMap<Integer,Class> surveys = new HashMap<>();
        surveys.put(0, SurveysLayout.class);
        surveys.put(1, SurveysStatistic.class);
        surveys.put(2, Ideas.class);
        classes.put(2,surveys);

        HashMap<Integer, Class> moderarion = new HashMap<>();
        moderarion.put(0, UnverifyUsers.class);
        moderarion.put(1, GetShopRequests.class);
        moderarion.put(2, Nastavniki.class);
        classes.put(3,moderarion);

        HashMap<Integer, Class> stat = new HashMap<>();
        stat.put(0, AllStat.class);
        stat.put(1, UploadStat.class);
        stat.put(2, LoginStat.class);
        classes.put(4,stat);

        HashMap<Integer, Class> gameifectaion = new HashMap<>();
        gameifectaion.put(0, AchievementsLayout.class);
        gameifectaion.put(1, CoinsBank.class);
        classes.put(1,gameifectaion);



        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            System.out.println(groupPosition+" "+childPosition);
            if(classes.containsKey(groupPosition))
                if(classes.get(groupPosition).get(childPosition)!=null)
                    startActivityIfNeeded(new Intent(AdminingLayout.this,classes.get(groupPosition).get(childPosition)),100);
            return true;
        });
    }

    private ArrayList<Map<String, String>> addCollection(String[] arr) {
        ArrayList<Map<String, String>> сhildDataItemList = new ArrayList<>();
        for (String month : arr) {
            Map<String, String> map = new HashMap<>();
            map.put("monthName", month);
            сhildDataItemList.add(map);
        }
        return сhildDataItemList;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && resultCode==-500)
            Utils.ShowSnackBar.show(AdminingLayout.this,"отказано в доступе!",expandableListView);
    }
}
