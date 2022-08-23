package me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.pazl.prof;

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
import me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.SocSeti;
import me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.future_leaders.Contacts;
import me.solo_team.futureleader.ui.news.open_news.OpenNewsFragment;

public class Prof extends Her {
    List<ImageView> views = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.founder_layout);
        MenuGrid grid = new MenuGrid(findViewById(R.id.back__), this, getWindowManager());
        List<String> urls = Arrays.asList(
                "https://future-leaders.ru/resuorces/others/i9rE_msis8Aa2_gQLk0XlW00xeqAPy5wGucOrPaZ55GGMMHJDWlVzfcfHA4oKzNBxOuejpcP66Y11uPuQ44ELA.png",
                "https://future-leaders.ru/resuorces/others/v4behHVYGWtbkVgLxBLz0bGjZ_uqc0M947mgJqjsosrKzoc4CiejJO4AktSLI7wp0gjI9gtg4_zsBZonY7Z6cg.png",
                "https://future-leaders.ru/resuorces/others/gfqK7sii9l902dQ4dRwPHyZwF8pypMSQxkakh8zgGL1c8Dg43dnPpLzuPX8xH_7u00PC0EdFZxNMSMe0jiZuiQ.png"
        );
        for (int i = 0; i < urls.size(); i++) {
            ImageView v;

            if (i == 2)
                v = grid.addImageElement(null, true);
            else
                v = grid.addImageElement(null, false);
            views.add(v);
            Constants.cache.addPhoto(urls.get(i), v, this);
        }
        setTitle("Профессия");
        views.get(0).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag", "О проекте - ПАЗЛ. Профессия");
            intent.putExtra("id", -39);
            startActivity(intent);
        });
        views.get(1).setOnClickListener(v -> {
            Intent intent = new Intent(this, MeropRiytia.class);
            intent.putStringArrayListExtra("urls", new ArrayList<>(Arrays.asList(
                    "https://future-leaders.ru/resuorces/others/RaFT3S7dBfwyN3UCOvxJmK1m_VOFmprIJkFiK65B9Ewhqk26yjqKeWgEHvr2V8IQX5UD3635SeUDWEJeDA4g7w.png",
                    "https://future-leaders.ru/resuorces/others/856iUwzMgpgZL3cS1I-ZQwUzlldVuqx2lHHTrxQI9ti1D19QS-EvY-fvL9vJyJte0m5uvYIodJWBCnbvZs9Vjw.png",
                    "https://future-leaders.ru/resuorces/others/eVH91Z6cyBFP6gpPL6biK6t4tVzttdaarwgDwaqZzBT4_10blebHo_xRQ4Kz7E2O4bY97JZRUnzlMNbUEcNRCg.png",
                    "https://future-leaders.ru/resuorces/others/2UvWLeJ8qiSC8nWo6BjKyMc52PJO8I9hKyZAuRp_M56MOfC7l6KSEHrK5X8vKWTJt8WyCy_exWuSW8UVa6H3pA.png",
                    "https://future-leaders.ru/resuorces/others/ZfCgeqsEVdr7DexVbw3VeZEuma27avzhQLTWZGopHq9vpuimxEnspPF4ZPBdFsB7WAkyqHgGvaOUhTLknsrGSg.png",
                    "https://future-leaders.ru/resuorces/others/fMF6tH2gOXdZREpYEiOXCY2DBMLa9MNk9A-5X8BZpipjEEMr2LlPH4jtHNBSChoVL7GCyofkozes3uKbPP1xFw.png",
                    "https://future-leaders.ru/resuorces/others/01JNjFzQzrswwCRzbGpC-pfB-x8fi7gcuT3jlFgr01d66Hglo-NwWmZ1SszjBzkpmXOIG09XNcugJIuXg-PcRA.png",
                    "https://future-leaders.ru/resuorces/others/15HzHEDJZjC-hj3Ghz6nqc35XAkjTz7mUrKJjISLoigRpGeJVzYj57lPDJQkpxQhE_Eyhu0_5K44fR-0aKxVzg.png",
                    "https://future-leaders.ru/resuorces/others/kfeSl0AkyljeOepeJ71nDB4pBrgjSRfv2zSfMPqex_gHIgI9i81gGUZEtma2HGvzjObzQaEE6CYxCreV-SPDyQ.png",
                    "https://future-leaders.ru/resuorces/others/RiavDGJiHERZ5mGV82mnP0MCtmGbQ5oXYGzK3WelM-_TkV2DTH4YlrwGSitABFJbjDj1GUp30R08osGEjCZAOA.png",
                    "https://future-leaders.ru/resuorces/others/5QQ5K2lBuAUgzc3r9Vy9yOZizddZ6byZHXTI0IsHWO2v6x-9mZ4YlDVh_P0w1PcIWYQR5KC_GQyfDXihpm59ZA.png",
                    "https://future-leaders.ru/resuorces/others/7CZRpedD0V_YfAP_qETDE4zmkx6xUtBS3hzzGXA1eyHWNvogPTvG9z7LnQYI3HHuafdV0gYFT-siUJs1pPehXA.png"
            )));
            intent.putExtra("you_tube_link", true);
            intent.putExtra("names", new ArrayList<>(Arrays.asList(
                    "https://www.youtube.com/watch?v=LrK44b2JPsM&list=PL6KHjpK67K52BMzAp5BuLDVCWIujQUoi8",
                    "https://youtu.be/6Jgv0MwMeS4?list=PLQauMaSaUiEmnoGhGV_ecOxSXowoktywa",
                    "https://youtu.be/rkLe4JwtPR8?list=PLQauMaSaUiEmnoGhGV_ecOxSXowoktywa",
                    "https://youtu.be/MyJafvabKEs",
                    "https://youtu.be/psiqL8qgNbw?list=PL6KHjpK67K52BMzAp5BuLDVCWIujQUoi8",
                    "https://youtu.be/s4Q-2dzAYVI?list=PLQauMaSaUiEmnoGhGV_ecOxSXowoktywa",
                    "https://youtu.be/a17DADffEFE?list=PLQauMaSaUiEmnoGhGV_ecOxSXowoktywa",
                    "https://www.youtube.com/watch?v=h8CqafYUO9A&list=PLQauMaSaUiEmnoGhGV_ecOxSXowoktywa",
                    "https://youtu.be/gI2SbOtaIoM?list=PLQauMaSaUiEmnoGhGV_ecOxSXowoktywa",
                    "https://youtu.be/mjuTG_rlTzo?list=PLQauMaSaUiEmnoGhGV_ecOxSXowoktywa",
                    "https://youtu.be/8grZkAcMStM?list=PLQauMaSaUiEmnoGhGV_ecOxSXowoktywa",
                    "https://youtu.be/BhfbArswyA0?list=PLQauMaSaUiEmnoGhGV_ecOxSXowoktywa"
            )));
            intent.putExtra("title","Наши мероприятия");
            startActivity(intent);
        });
        views.get(2).setOnClickListener(v -> {
            Intent intent = new Intent(this, SocSeti.class);
            intent.putExtra("title","Соцсети ПАЗЛ");
            intent.putExtra("urls",new ArrayList<>(Arrays.asList(
                    "https://future-leaders.ru/resuorces/others/QUdzUXn4APTdGvHQuW6ipqeyuRMHbNRWFXCWyYyryFoqLMXtvFOQQwiZAwmXvGrOBGnROEONFmm6igaaD9fbjQ.png",
                    "https://future-leaders.ru/resuorces/others/10x3C7X1s3WjS9fPKkoRtS7OK0XLfxWTl6XlfB4J5m8MkRaGdlu90birjHh4DfNCysqIyXy2XY0P-7TKeVDbjw.png"
            )));
            intent.putExtra("names",new ArrayList<>(Arrays.asList(
                    "Вконтакте",
                    "сайт"
            )));
            intent.putExtra("redirect",new ArrayList<>(Arrays.asList(
                    "https://vk.com/puzzle_construction2020",
                    "https://www.puzzleplace.ru/"
            )));
            startActivity(intent);
        });
    }
}
