package me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.DvizSmelyz;

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
import me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.pazl.prof.Prof;
import me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.pazl.programing.Programing;
import me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.pazl.stroy.Stroy;
import me.solo_team.futureleader.ui.news.open_news.OpenNewsFragment;

public class DvizLayout extends Her {
    List<ImageView> views = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.founder_layout);
        MenuGrid grid = new MenuGrid(findViewById(R.id.back__), this, getWindowManager());
        List<String> urls = Arrays.asList(
                "https://future-leaders.ru/resuorces/others/sEd8ARmVC_2675jA8tuKzM7as9akP940g0WrCdwZ3Usu8mlyi8p0VJ2I_FqTlHmjSXGL_rzROt6OFLZ9TwEU2g.png",
                "https://future-leaders.ru/resuorces/others/-5YSLDmd3yv62By3c4Bwujy938IdMS7wfJHqUrWdOHTUkCtItQ-VRvla81mpR4b2RcSg4tmfTErCRPPIhZiH_A.png",
                "https://future-leaders.ru/resuorces/others/z5qIqTsbSUJr2OOpEuOYaiUdzXm8bgG3I_FUYgIzMJCiRqRjVRi34q2enefQy5KAVyPG7HjzBfGbOsRT9H5okg.png"
        );
        for (int i = 0; i < urls.size(); i++) {
            ImageView v = grid.addImageElement(null, false);
            views.add(v);
            Constants.cache.addPhoto(urls.get(i), v, this);
        }
        setTitle("Конкурс робототехники \"Движение Смелых\"");
        views.get(0).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","О проекте");
            intent.putExtra("id",-66);
            startActivity(intent);
        });
        views.get(1).setOnClickListener(v -> {
            Intent intent = new Intent(this, MeropRiytia.class);
            intent.putStringArrayListExtra("urls", new ArrayList<>(Arrays.asList(
                    "https://future-leaders.ru/resuorces/others/AkQ4sRrNQCpea9oYA0nOttKWn10FcikhWr2w6s7JBpIthFpSonF3DvVkZlsqsQA9ds8MEftNO-lsQ9wyi9Xueg.png",
                    "https://future-leaders.ru/resuorces/others/8C_WafIK-D_QQ9rNnlN442unqdN1euPMJPesuP52HEBhkc_tC-i6FxxrMz_x9WLXrUB1ygJx4qhcnxfzPh3dDQ.png",
                    "https://future-leaders.ru/resuorces/others/yLzT6DmRPZsbRQAdxzBLMZXEpVdiyKU46-K2ymk49b-mF64bkFKG4rCFNbvHu-1F3_vJ_PcuGhHVcjzBf_Q8Vg.png"
            )));
            intent.putExtra("names", new ArrayList<>(Arrays.asList(
                    "Конкурс робототехники 2021",
                    "Конкурс робототехники 2020",
                    "Конкурс робототехники 2019"
            )));
            intent.putExtra("ids",new ArrayList<>(Arrays.asList(
                    -67,-69,-68
            )));
            intent.putExtra("title", "Мероприятия");
            startActivity(intent);
        });
        views.get(2).setOnClickListener(v -> {
            Intent intent = new Intent(this, SocSeti.class);
            intent.putExtra("title","Соцсети");
            intent.putExtra("urls",new ArrayList<>(Arrays.asList(
                    "https://future-leaders.ru/resuorces/others/a-WLglG7S_pRg2BCwnbBtg8h6n9YSEys8yVgfiMw5ydqeHu7byGp0XXcPSJG79hPisKg9M_sr0gg3aJFOTmpGg.png",
                    "https://future-leaders.ru/resuorces/others/WQcKYiqO30hVJRsQo43BqVft0EqxnUooKafTFD0iGNBAx0jaB2G8Eh_fzsKiLip5DJcACiih2RoZ62Y6d4xDxw.png"
            )));
            intent.putExtra("names",new ArrayList<>(Arrays.asList(
                    "Вконтакте",
                    "сайт"
            )));
            intent.putExtra("redirect",new ArrayList<>(Arrays.asList(
                    "https://vk.com/robotleaders",
                    "https://roboleaders.ru/"
            )));
            startActivity(intent);
        });
    }
}