package me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.SchoolFutureLeaders;

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
import me.solo_team.futureleader.ui.menu.statical.Founder.ContactLayout;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;
import me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.MeropRiytia;
import me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.SocSeti;
import me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.users_projects.UsersProjects;
import me.solo_team.futureleader.ui.news.open_news.OpenNewsFragment;

public class piter extends Her {

    List<ImageView> views = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.founder_layout);
        MenuGrid grid = new MenuGrid(findViewById(R.id.back__), this, getWindowManager());
        List<String> urls = Arrays.asList(
                "https://future-leaders.ru/resuorces/others/Iu-36xii3VNkZ2R6UH9bun3CFvIRb1ubQas1SPlIazEZPy_hxxkBLLSTDalwxOXd6mBppYIjGnaWSuwkwiQ6RQ.png",
                "https://future-leaders.ru/resuorces/others/QJ8I45Bj-bBV5bBqQPiOb-pxz9AbjSKcFhJBr9_43CyeVRFt50YFfwl_UwAohw5ajYHmtGXvfccV4n6k3OaCAw.png");
        for (int i = 0; i < urls.size(); i++) {

            ImageView v =  grid.addImageElement(null, false);
            views.add(v);
            Constants.cache.addPhoto(urls.get(i), v, this);


        }
        views.get(0).setOnClickListener(v -> {
            Intent intent = new Intent(this, MeropRiytia.class);
            intent.putStringArrayListExtra("urls", new ArrayList<>(Arrays.asList(
                    "https://future-leaders.ru/resuorces/others/7MENuZPrBOkCA-sZxeAwXSFSOrZzOCaonLK-yCxzJdIj-ugdD2lajZDemV-sTurP3qvDBxcxyauqElze5oF5uQ.png",
                    "https://future-leaders.ru/resuorces/others/6vdZZ1Sw-jrZisBMlKXDbQ4DGPEILnjoEulgqMjKy4VswYuDvR1rBK-J3TCx7nie04labVqHvHFA2tdKehA56A.png",
                    "https://future-leaders.ru/resuorces/others/R7eODgujruRqUu_1qC3WHmFBO8X5MKPD5JyUKtjJM_w2OORvHICinYsjkTylZXT8jbIMUCyh7WxoGEqCxBQv9g.png",
                    "https://future-leaders.ru/resuorces/others/kf9YAV2WgHaJFwlpAa6fv3rzonO_uhI4dXXohpwzdpDjxzl4X28rsK1PPTwkHBLLNWIDuPQD9DQi8xQUuwIB-A.png",
                    "https://future-leaders.ru/resuorces/others/YgPUXLBq_Jzn_agN0srnKl5HlKxODg_XSvrXyuzoRxT4t2C_GFaFBp2y-dQVv427YotW75UsqXVbsko_DmD25Q.png",
                    "https://future-leaders.ru/resuorces/others/b_qpcLSK1t0ytdzXlMPuNO7a-AhQTu_GS-C5MISRwgqnIzgEooCqpM9tGKL13jQCARlODuaY3eeew6mTMcog8Q.png"
            )));
            intent.putExtra("names", new ArrayList<>(Arrays.asList(
                    "Отборочный кейс-турнир 2021",
                    "ЗОтборочный кейс-турнир 2020",
                    "Школа будущих лидеров RBI. Лучшее за 2018-2019",
                    "Школа будущих лидеров RBI. Лучшее за 2017-2018",
                    "Квест",
                    "Кейс-турнир 2017"
            )));
            intent.putExtra("ids",new ArrayList<>(Arrays.asList(
                    -48,-49,-50,-51,-52,-53
            )));
            intent.putExtra("title", "Мероприятия");
            startActivity(intent);
        });
        views.get(1).setOnClickListener(v -> {
            Intent intent = new Intent(this, MeropRiytia.class);
            intent.putStringArrayListExtra("urls", new ArrayList<>(Arrays.asList(
                    "https://future-leaders.ru/resuorces/others/KtrpvnOTDi-QzcV8wEyDIyDoZ9qLrXNf3b8zBmbMW67DTLvar-GC7aFV32Hlvvx5GjCfKG1A_KV1LqbghlcSaw.png",
                    "https://future-leaders.ru/resuorces/others/O0kXFOSYSwAbV3sLKP981YQ0gZ0objeyr-6oDzKm4xQ8E6PhKh_GBELRtfGNAwo75RIH88fRS6gTGd1X2iWTig.png",
                    "https://future-leaders.ru/resuorces/others/If9yrQr6c_Gnypj2Yk5UkZlYWEC9LjpL_h4cY-9gvXEPFoo6hT-mZpaD-9VOQxJkG2Hduhdc9rV9YTUYIlV_kA.png",
                    "https://future-leaders.ru/resuorces/others/v_4iL3tDp9aiwfwukT55TBTreZdd9SYxtAnzffJaQETFl_NDsGECM5lipbsHJQTXb19zI8KNGnvJY5YIfVOpxg.png",
                    "https://future-leaders.ru/resuorces/others/x-8wc5_kfKW6ksWwVWSDL7NV426FFK1TYIMAle0rbJ1mBTo0tOstS3ZP3-0gAx1VG-FEyQQZrxK7nzbo35mWNQ.png",
                    "https://future-leaders.ru/resuorces/others/igQABznaxIpLhp1qwAdgYwLGySjY6GxB3YtHhyCEze6_nrzQNBFpdrkXqgEz3mpB_dSXhR2vz5uIprxYuZ23Uw.png",
                    "https://future-leaders.ru/resuorces/others/FyFt2Q0L99xppeY2Wgdfd4bkqABqlnYTdDUeSJ7lDr6Fv4ZMG9PCaBv7TPzbBtuuLng9LCWtJU-udJqFZHrkIQ.png",
                    "https://future-leaders.ru/resuorces/others/AdUn2stb_xcwxWvGgWa_3xQLpBoz6gvzYI_nzbnmXh3EY91DhfTCgQ8it6R1HTMUMVtrtcd_2C_QHlYOCYtW-Q.png",
                    "https://future-leaders.ru/resuorces/others/DDiTcpb9_kvcyEJKphvKbi5oa4WZRiotgwsOdNA9m-B_WD5uiyzgkSLIaeIuegdjJza5fLbe7HLnmx6rOP_L8Q.png"
            )));
            intent.putExtra("names", new ArrayList<>(Arrays.asList(
                    "Роман Герасимов",
                    "Павел Филлипов",
                    "Виталий Антощенко",
                    "Михаил Кнеллер - Умные города",
                    "Алексей Долотов - Разговоры об аналитике и аналитиках",
                    "Роман Чуков - Разговор о молодёжной дипломатии",
                    "Александра Глазкова - Как построить успешную карьеру",
                    "Юлия Сахарова - Тенденции рынка труда",
                    "Илья Степанов - бизнес-тренер и специалист по речевой коммуникации"
            )));
            intent.putExtra("ids",new ArrayList<>(Arrays.asList(
                    -54,-55,-56,-57,-58,-59,-60,-61,-62
            )));
            intent.putExtra("title", "Лекции");
            startActivity(intent);
        });
        setTitle("Санкт-Петербург");
    }
}
