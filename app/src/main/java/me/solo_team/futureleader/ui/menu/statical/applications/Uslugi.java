package me.solo_team.futureleader.ui.menu.statical.applications;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.Usluga;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class Uslugi extends Her {

    LinearLayout list;
    private HashMap<Integer, List<Usluga>> viewsJ = new HashMap<>();
    int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("услуги");

        type = getIntent().getIntExtra("type", -1);
        if (type == -1)
            finish();
        try {
            createJSON();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.uslugi);
        list = findViewById(R.id.uslugi_list);
        add();
    }


    private Usluga getUsluga(View v) {
        for (Usluga usluga : viewsJ.get(type)) {
            if (usluga.name.equals(((TextView) v.findViewById(R.id.usluga_name)).getText().toString()) && usluga.description.equals(((TextView) v.findViewById(R.id.usluga_description)).getText().toString()))
                return usluga;
        }
        return null;
    }

    private void add() {
        list.removeAllViews();
        switch (type) {
            case 0:
                for (Usluga usluga : viewsJ.get(0)) {
                    View v = createView(usluga);
                    v.setOnClickListener(v1 -> {
                        Intent intent = new Intent(this, ViewUsluga.class);
                        intent.putExtra("title", usluga.name);
                        intent.putExtra("obj",usluga.fieldsToString());
                        startActivityForResult(intent,100);
                    });
                    list.addView(v);
                }
        }
    }

    private View createView(Usluga usluga) {
        View view = getLayoutInflater().inflate(R.layout.usluga, null);
        Constants.cache.addPhoto(usluga.image, view.findViewById(R.id.usluga_photo), this);
        ((TextView) view.findViewById(R.id.usluga_name)).setText(usluga.name);
        ((TextView) view.findViewById(R.id.usluga_description)).setText(usluga.description);
        return view;
    }

    private void createJSON() throws JSONException {
        viewsJ.put(0, new ArrayList<>(Arrays.asList(
                new Usluga("https://future-leaders.ru/resuorces/others/kwKQoPPOol3_d5LE-q5QnC1kVkfADAOmYu3rE68q9b04Ri5_DVi7feHxZV6cyvhjfI6feBnWEzS_0ymT3hwAOA.png", "Дизайн", "наши дизайнеры помогут вам креативно оформить любую идею",
                        new JSONArray("[{\"name\":\"что нужно сделать?\",\"type\":\"text\"},{\"name\":\"для какого проекта?\",\"type\":\"text\"},{\"name\":\"ТЗ дизайнеру\",\"type\":\"file\"}]")),
                new Usluga("https://future-leaders.ru/resuorces/others/YD-wt2M0BmlPYpIyyU6bfdfS65RDkCdL0LqYcq3A730wpKT1srFYUIOBI4RwGWTYYhSpvaeLZQhyslKRxar_4g.png", "Ресурсы", "если вам необходимы материалы для проведения мероприятия (флипчарт, маркеры, техническое оборудование и тд)",
                        new JSONArray("[{\"name\":\"что необходимо?\",\"type\":\"text\"},{\"name\":\"для чего?\",\"type\":\"text\"}]")),
                new Usluga("https://future-leaders.ru/resuorces/others/135Mx0Go8Knv3fs2NYt98U6ObUQJpU55ExvWCd2KjJKyj8NBNUbzNcf1PzTgBkzme66wwx8jtCdXCp-5l7lTxw.png", "Мероприятия/ проект", "Если у тебя есть идея для события или проекта, то расскажи об этом здесь",
                        new JSONArray("[{\"name\":\"название мероприятия/ проекта\",\"type\":\"text\"},{\"name\":\"цель мероприятия/ проекта\",\"type\":\"text\"},{\"name\":\"презентация о мероприятии/ проекте\",\"type\":\"file\"}]")),
                new Usluga("https://future-leaders.ru/resuorces/others/mEyCQFJJOv4PSFH1jCGIBnII_0COcEFbNPepzCAqAYOleO_e6x4BUPOPdE2fybZcU4VrQ4hPQETDoisVGKvyWw.png", "Проведение совещания", "договориться о времени и теме совещания с Руководством Фонда",
                        new JSONArray("[{\"name\":\"желаемая дата и время для проведения совещания\",\"type\":\"datetime\"},{\"name\":\"тема совещания\",\"type\":\"text\"}]")),
                new Usluga("https://future-leaders.ru/resuorces/others/LV8f3RAQR9HwbzWduU-8ea7T1v-b9aDvL81R5ef7vYgxNJ-O59B1o6alwyp2yuEeg_zuMhObyj77HVXpvCHAyA.png", "Участие в реализации фонда", "участие в деятельности Фонда через реализацию проектов из маркетплейса",
                        new JSONArray("[{\"name\":\"*\",\"type\":\"text\"}]"))
                )));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1){
            if(requestCode==100){
                Utils.ShowSnackBar.show(Uslugi.this,"Ваша заявка успешно подана",list);
            }
        }
    }
}
