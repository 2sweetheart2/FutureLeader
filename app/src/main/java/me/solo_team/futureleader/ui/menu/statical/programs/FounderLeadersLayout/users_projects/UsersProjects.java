package me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.users_projects;

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
import me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.users_projects.flip.FlipLayout;
import me.solo_team.futureleader.ui.news.open_news.OpenNewsFragment;

public class UsersProjects extends Her {
    List<ImageView> views = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.founder_layout);
        MenuGrid grid = new MenuGrid(findViewById(R.id.back__), this, getWindowManager());
        List<String> urls = Arrays.asList(
                "https://future-leaders.ru/resuorces/others/1Hk-rbq833VKQ23MqeP9Aa6Ig6zAkkIgMvB6mqfdLiBwBkrO6Ass--mGK2dlYt4LYnPaBHCah-6KKj2XWT5QNg.png",
                "https://future-leaders.ru/resuorces/others/N1FyzvRN8lrGrJVntFxfP1Tbb92lcfBXV-981AtYbsF5aYneV3i1VkNLjE8McLwG4KrmX1LMjw5NcEEvFHKsUg.png",
                "https://future-leaders.ru/resuorces/others/nrCoZBOK9BNB1njZtAPhUy1xxoA8mubZSv4E_M5cfmtsy6bn8wdoGWCvDbkc0_YmJe8sXT9mcoFBR5xbS4p4dQ.png",
                "https://future-leaders.ru/resuorces/others/jmQUAs4E3lhm1jmi8_9rcDQoc2aYnoiBGIeRjrs6ayguCz7x5B1GJEo-xta6KlbiHY4Wwf3UpA1ZDa2ycV7rqg.png",
                "https://future-leaders.ru/resuorces/others/_nEYNQsr1HfHaPhYC9SNlUwcZTwKPvqcBtTEgDx1Amu374n1meNpWTGMmsaL27J1rWaULURQgFIuLuc0mMtMxA.png",
                "https://future-leaders.ru/resuorces/others/U0VOY1yvPy7yw4Fp3karJHVDIwjMhYOxDY8qZRy5vqePHTTgulLNf5R6gout8RFOuWBlKawPU7XFn2F3GyrUKw.png",
                "https://future-leaders.ru/resuorces/others/c3teawTcMvn866wNdlQup4V19tVSiXFXIUCJbBe8IzO_HLaCxP-elNsJu4SukxI3R5SulREw_pod7zjRxMJdQQ.png",
                "https://future-leaders.ru/resuorces/others/jHeYbC3Wq3MfRNWcuXmcltx2G4EutTr4P0ulWysNfgbc2W5roWDLLxXBTmWBc7JrGO9qmcnFfQjmuzvUCZuUIQ.png",
                "https://future-leaders.ru/resuorces/others/YI4yAwNAxBXxDrpBQIRsizz8QmtfDNqzLPSpKLretk8N5031lmXK5v7wrj3FNwNgU04yqaSW1j75AzNLzC4uqA.png",
                "https://future-leaders.ru/resuorces/others/VWp_U87-5PDxrTizELKca0-8a6KcdR-Dn7PMMCRQ8YM0hkojgJ9ve9lC2OB-aMIoUfa6JGFmx0o4Tkh7ABeLkw.png",
                "https://future-leaders.ru/resuorces/others/lK37HNEkwmW9PdlovXeNFpJ6Vv3pUsr4JgCMpeIhq7avhR0iXykvdrDVjL940-Plivq-hdjElm7l6OoMigpa7A.png",
                "https://future-leaders.ru/resuorces/others/O1jxciua8_2UVBeyC8Emjn6SDZbqYWOmBF8a3CPLgGGzcoCa_4oUJKLn3dHOg5p7Wd0Vjmh4gseJ3uahd8Y0cA.png",
                "https://future-leaders.ru/resuorces/others/bFuCLVCAb_9OKOf63FUfEGUt_YjLD3pCqNyH_ikCDHE46ok-LSnrm6qd6RYn45bbElMA_8YCvKcAidUgivT64A.png",
                "https://future-leaders.ru/resuorces/others/OfjidpR5x-iO357U9LHpMuV-YvqVC3Q0TzrN7pKsNWoGiFWk6CMZt4Q0WNl6aEiiRqy27DUW4-FmQGwQvfMU8w.png",
                "https://future-leaders.ru/resuorces/others/ihBaDNnfJ11nyDmEzoysoqIG2_LOORsCnaO4pOE9C0uh8NQ8Rwzozqgk6Dp6bBCUs5dMLZ0SeKGJduIORWSqlA.png"
        );
        for (int i = 0; i < urls.size(); i++) {
            boolean onAllColumn = false;
            if(i==14) onAllColumn = true;
            ImageView v = grid.addImageElement(null, onAllColumn);
            views.add(v);
            Constants.cache.addPhoto(urls.get(i), v, this);
        }
        setTitle("Проекты участников");
        views.get(0).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","Юлия Исаченко");
            intent.putExtra("id",-14);
            startActivity(intent);
        });
        views.get(1).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","Сысоева Анастасия");
            intent.putExtra("id",-15);
            startActivity(intent);
        });
        views.get(2).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","Третьяков Алексей");
            intent.putExtra("id",-16);
            startActivity(intent);
        });
        views.get(3).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","Афанасьева Сафи");
            intent.putExtra("id",-17);
            startActivity(intent);
        });
        views.get(4).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","Гутников Михаил");
            intent.putExtra("id",-18);
            startActivity(intent);
        });
        views.get(5).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","Деменкова Екатерина");
            intent.putExtra("id",-19);
            startActivity(intent);
        });
        views.get(6).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","Игорь Баранов");
            intent.putExtra("id",-20);
            startActivity(intent);
        });
        views.get(7).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","Даниил Лазуткин");
            intent.putExtra("id",-21);
            startActivity(intent);
        });
        views.get(8).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","Эврика");
            intent.putExtra("id",-22);
            startActivity(intent);
        });
        views.get(9).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","Ичеткин Дмитрий");
            intent.putExtra("id",-23);
            startActivity(intent);
        });
        views.get(10).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","GuideMap");
            intent.putExtra("id",-24);
            startActivity(intent);
        });
        views.get(11).setOnClickListener(v -> {
            Intent intent = new Intent(this, FlipLayout.class);
            startActivity(intent);
        });
        views.get(12).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","Клименко Ксения");
            intent.putExtra("id",-25);
            startActivity(intent);
        });
        views.get(13).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","Золотова Дарья");
            intent.putExtra("id",-26);
            startActivity(intent);
        });
        views.get(14).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","Никулин Никита");
            intent.putExtra("id",-27);
            startActivity(intent);
        });
    }

}