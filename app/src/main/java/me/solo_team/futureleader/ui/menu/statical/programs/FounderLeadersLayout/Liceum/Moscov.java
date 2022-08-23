package me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.Liceum;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.menu.MenuGrid;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;
import me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.MeropRiytia;

public class Moscov extends Her {
    List<ImageView> views = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.founder_layout);
        MenuGrid grid = new MenuGrid(findViewById(R.id.back__), this, getWindowManager());
        List<String> urls = Arrays.asList(
                "https://future-leaders.ru/resuorces/others/Aoqidcs_j3anUkJxkywud6Xof7Vzebvu9fl1fILi3Iz9InlV9vwYYRM4C20JaQYOztMFVtWovvrBkpzlyn-Ftw.png"
        );
        for (int i = 0; i < urls.size(); i++) {
            ImageView
                    v = grid.addImageElement(null, false);
            views.add(v);
            Constants.cache.addPhoto(urls.get(i), v, this);
        }
        setTitle("Москва");
        views.get(0).setOnClickListener(v -> {
            Intent intent = new Intent(this, MeropRiytia.class);
            intent.putStringArrayListExtra("urls", new ArrayList<>(Arrays.asList(
                    "https://future-leaders.ru/resuorces/others/ezE7Fs8GYpN5VsCCxPstSBq04rqQSFzN0srxz11E2xaniCH53LPSn4JyR52p9X-mfyj7eVq67HwpKVPNOepgpQ.png",
                    "https://future-leaders.ru/resuorces/others/4k-5HHthnI-2oGaO05FcDCHZ0pkz1Aqdhh71ayk4mK_rDmGF1YOJRRRgI1TV9vi3E2xGhubvSlbaltRdy2XiOg.png",
                    "https://future-leaders.ru/resuorces/others/Xus_HhTRAeJQivMoFC4EsLBhUiUrX2Af8MGAgCWXtErWSEM25uHpFnj8fuHP5n-Rfj0dVgY56P1EshC8fYkAkw.png",
                    "https://future-leaders.ru/resuorces/others/0N9hzGdQSiw_p99bR66KalBoT7nmn7OoNGfEVGH-e1wpwD-f5iMFXDOGG9ISfhNRB328tm6jneCaWSBayt5BPA.png",
                    "https://future-leaders.ru/resuorces/others/cDBifPetPP3iaGc4Dg9umhhHzxcWwTaITWg-jmr-6Dk-ORQcSNoKq-TXXP-2FXnQKm635Uwz_bnOzKFbtwSdjA.png",
                    "https://future-leaders.ru/resuorces/others/Qt8NKb6_xvpuBWRu_pNzGoDESWic5GYtAXCmk9KxQpQZBua9IOQy8L4uHayD2h9Z43Rxy4O59EtZ4ApNlT7vNQ.png",
                    "https://future-leaders.ru/resuorces/others/_4F46333vCZcW7XWce6u6-PuamRXutWOEuFRPvqEJZV0bv_uhySkd0yzBpG1ROzqo5v5Kuv7XKckeMiVLC52Ow.png"
            )));
            intent.putExtra("names", new ArrayList<>(Arrays.asList(
                    "Лекция Ани Алексеевой",
                    "Лекция Михаила Алексеева",
                    "Открытие образовательной программы 2021",
                    "Кейс-турнир 2021",
                    "Выпускной Лицея будущих Лидеров",
                    "Experience Еxchange совместно с Фондом «Будущие Лидеры»",
                    "Кейс-турнир в Лицее Будущих Лидеров"
            )));
            intent.putExtra("ids", new ArrayList<>(Arrays.asList(
                    -71, -72, -73, -74, -75, -76, -77
            )));
            intent.putExtra("title", "Мероприятия Лицея Будущих Лидеров");
            startActivity(intent);
        });
    }
}