package me.solo_team.futureleader.ui.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.MainActivity;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.News;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.news.open_news.OpenNewsFragment;

public class NewsFragment extends Fragment {


    private LayoutInflater inflater;
    private ViewGroup container;
    private LinearLayout nw;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        requireActivity().invalidateOptionsMenu();
        View root = inflater.inflate(R.layout.fragment_news, container, false);
        nw = root.findViewById(R.id.news_list);
        this.inflater = inflater;
        this.container = container;
        API.getNews(new ApiListener() {
            @Override
            public void onError(JSONObject json) {
                System.err.println(json.toString());
            }

            @Override
            public void inProcess() {

            }

            @Override
            public void onSuccess(JSONObject json) throws JSONException {
                System.out.println(json.toString());
                JSONArray news = json.getJSONArray("news");
                for (int i = 0; i <= news.length(); i++) {
                    JSONObject o = news.getJSONObject(i);
                    News new_ = new News(
                            o.getInt("id"),
                            o.getString("photo_paths"),
                            o.getString("text"),
                            o.getString("name"),
                            o.getString("title"));
                    requireActivity().runOnUiThread(() -> {
                        addElement(new_.photoUrl, new_.title).setOnClickListener(v -> {
                            Intent intent = new Intent(requireContext(), OpenNewsFragment.class);
                            intent.putExtra("tag",new_.title);
                            intent.putExtra("title", new_.name);
                            intent.putExtra("text", new_.text);
                            startActivity(intent);
                        });
                    });
                }
            }
        }, new CustomString("token", Constants.user.token));
//        List<String> uris = Arrays.asList(
//                "https://sun9-82.userapi.com/s/v1/if2/CPSHVmdt6BZ7xk5nMMXWpwslz51c8cbYALeG9qnmEeXFO5lncRh7di8sFHqr45iqWw9NkJ_zNY-I4bFTsnh9Jb6G.jpg?size=800x450&quality=96&type=album",
//                "https://sun9-74.userapi.com/s/v1/if2/2TiaYMMsXjgPkmE79NYhVP4X1PUB3lLISIIR1iTOk-Qw_COIKmx9FS3NRNRLz2aPzONYS9whxRwk7Cr8hMrGaTXa.jpg?size=800x450&quality=96&type=album",
//                "https://sun9-14.userapi.com/s/v1/if2/I9V15CFpKZ-Fyscc16XejFzOF2Idjj0MPILCVX7m298nBlySTwjuNdewkPs0n9zwk1JGs37Vh0Ogm_D0K12pp52B.jpg?size=800x450&quality=96&type=album",
//                "https://sun9-44.userapi.com/s/v1/if2/1fvsNDYfxDE2Nk8Rg3GEz2c98w3gcVg6NyDw_-LH-sYvWi0VfIgRTs6-35SFJSaVOviFHwWsP_Cfo4lPV2FPPZC5.jpg?size=800x450&quality=96&type=album",
//                "https://sun9-54.userapi.com/s/v1/if2/4wUcAqG3-UgKR4jJ-pz6GAVjps-3mPUFN7MKTeBaknK3lKStzw6sup4BBhq4Eimu2ulV4iFbHzK8ONjoyzl19Q51.jpg?size=800x450&quality=96&type=album",
//                "https://sun9-77.userapi.com/s/v1/if2/6_cYhhKZbv6gjjV3DNHpl2wS-Z9gf4tz6XW6QY95TtYWqb29IfO-lLfcj4Q8zIutT0Ly6Mp_mVCWpffjpp-LRy94.jpg?size=800x450&quality=96&type=album",
//                "https://sun9-7.userapi.com/s/v1/if2/AJXIRVo-KyP2418vOm6ZTYF3KYIulzDkw8coTB3WvmnNi94vQBdlT8X3-KU1KgbLM4FzP_IXs3vNAaphwfQg_0Jo.jpg?size=800x450&quality=96&type=album",
//                "https://sun9-30.userapi.com/s/v1/if2/cMmdnmZO5prmwmKaeQEToEE40FcJkumuuVbUdMmT6MNHQeugxs7jfVgcci6BrCMWdtdSoDGnnM9Zt80aN3nGS1ax.jpg?size=800x450&quality=96&type=album",
//                "https://sun9-81.userapi.com/s/v1/if2/KlWM1GdeqNoW-wynuB6sfm36CsXkYPGIxt9cIP2LZMbjBCSy1XdpLOLe_x_V9ygomWTUrpPhx1cQ2Vayc0wgyaA8.jpg?size=800x450&quality=96&type=album",
//                "https://sun9-80.userapi.com/s/v1/if2/ivDJeA1zC9WIli_gPYv3Jpt37_fDhUqYLUTj9UK69JFd10w5wAKLH9W1eW9jxN-5gpSKuGYSRdd4vOsxSZNJc8Sg.jpg?size=800x450&quality=96&type=album"
//        );
//        List<String> names = Arrays.asList(
//                "#КООРДИНАЦИОННЫЙ СОВЕТ",
//                "#КООРДИНАЦИОННЫЙ СОВЕТ",
//                "#КООРДИНАЦИОННЫЙ СОВЕТ",
//                "#КООРДИНАЦИОННЫЙ СОВЕТ",
//                "#НОВОСТИ ФОНДА",
//                "#НОВОСТИ ФОНДА",
//                "#КООРДИНАЦИОННЫЙ СОВЕТ",
//                "#НОВОСТИ ФОНДА",
//                "#КООРДИНАЦИОННЫЙ СОВЕТ",
//                "#СТАРТАП"
//        );
//
//        String text = "Прямо сейчас у нас проходит заочный этап голосования, в котором голосуют все участники сообщества!\n" +
//                "\n" +
//                "Напоминаем, чтобы принять участие в выборах, вам необходимо отдать свой голос за одного из кандидатов через Координатора вашего набора. Полный список координаторов можно посмотреть в этой новости.\n" +
//                "\n" +
//                "Для того, чтобы не ошибиться и быть точно уверенным в своем решении советуем еще раз ознакомиться с программами наших кандидатов\n" +
//                "\uD83D\uDD38 Рогова Юлия, участница \"Фонда Будущие лидеры\" \n" +
//                "\uD83D\uDD38 Афанасьев Артем, участник \"Школы лидеров будущего\" \n" +
//                "\uD83D\uDD38 Ненастьева Александра, участница \"Лицея будущих лидеров\"  \n" +
//                "\n" +
//                "Результаты первого этапа мы опубликуем уже 23 апреля!\n" +
//                "Прочитать подробнее про все этапы выборов можно здесь \n" +
//                "\n" +
//                "В эти выходные пройдет выезд Координационного Совета, на котором состоится второй и третий этапы выборов. И уже в понедельник мы опубликуем имена Председателя и Руководителей направлений Координационного Совета!\n" +
//                "\n" +
//                "Следите за новостями, будет интересно!";
//        for (int i = 0; i < uris.size(); i++) {
//            addElement(uris.get(i), names.get(i)).setOnClickListener(v -> {
//                Intent intent = new Intent(requireContext(), OpenNewsFragment.class);
//                intent.putExtra("title", "Первый этап выборов Председателя Координационного Совета в самом разгаре!");
//                intent.putExtra("text", text);
//                startActivity(intent);
//            });
//        }

        return root;
    }

    private View addElement(String uri, String name) {
        ConstraintLayout row = (ConstraintLayout) inflater.inflate(R.layout.news_news, container, false);
        ConstraintLayout cn = row.findViewById(R.id.news_obj);
        Constants.cache.addPhoto(uri, true, (ImageView) cn.getChildAt(1), this);
        ((TextView) cn.getChildAt(2)).setText(name);
        switch (MainActivity.wightwindowSizeClass) {
            case COMPACT:
                ((TextView) cn.getChildAt(2)).setTextSize(12f);
                break;
            case MEDIUM:
                ((TextView) cn.getChildAt(2)).setTextSize(15f);
                break;
            case EXPANDED:
                ((TextView) cn.getChildAt(2)).setTextSize(20);
                break;
        }
        nw.addView(row);
        return row;
    }


}