package me.solo_team.futureleader.ui.menu.statical.admining;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.menu.statical.admining.layouts.surveys.SurveysLayout;
import me.solo_team.futureleader.ui.menu.statical.admining.layouts.users.StructurLayout;
import me.solo_team.futureleader.ui.menu.statical.admining.layouts.users.UsersLayout;

public class AdminingLayout extends AppCompatActivity {
    private final String[] mGroupsArray = new String[]{"Пользователи", "Контент", "Геймификация", "Опросы и ОС", "Модерация", "Обслуживание", "Интеграция"};

    private final String[] govno1 = new String[]{"Список пользователей", "Структура подразделения", "Статусы пользователей"};
    private final String[] govno2 = new String[]{};
    private final String[] govno3 = new String[]{"Достижения", "Открытки", "Журнал отправленных открыток", "Валютный банк", "Отчет по активностям"};
    private final String[] govno4 = new String[]{"Опросы", "Сессии опросов", "Отчет по обратной связи"};
    private final String[] govno5 = new String[]{"Запросы на модерацию", "Подтверждение регистрации пользователей"};
    private final String[] govno6 = new String[]{"соси хуй а не итеграцию"};
    private final String[] govno7 = new String[]{"Статистика", "Журнал изменений", "Журнал авторизаций", "Журнал регистраций"};

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
        сhildDataList.add(addCollection(govno2));
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

        ExpandableListView expandableListView = findViewById(R.id.expListView);
        expandableListView.setAdapter(adapter);


        HashMap<Integer,HashMap<Integer,Class>> classes = new HashMap<>();
        HashMap<Integer,Class> m = new HashMap<>();
        m.put(0, UsersLayout.class);
        m.put(1, StructurLayout.class);

        HashMap<Integer,Class> surveys = new HashMap<>();
        m.put(0, SurveysLayout.class);
        classes.put(3,m);


        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            System.out.println(groupPosition+" "+childPosition);
            startActivity(new Intent(AdminingLayout.this,classes.get(groupPosition).get(childPosition)));
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
}
