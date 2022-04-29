package me.solo_team.futureleader.ui.news;

import android.content.Intent;
import android.graphics.Bitmap;
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

import java.util.Arrays;
import java.util.List;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.MainActivity;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.Utils;
import me.solo_team.futureleader.ui.news.open_news.OpenNewsFragment;

public class NewsFragment extends Fragment {

    private LayoutInflater inflater;
    private ViewGroup container;
    private LinearLayout nw;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_news, container, false);
        nw = root.findViewById(R.id.news_list);
        this.inflater = inflater;
        this.container = container;
        List<String> uris = Arrays.asList(
                "https://futureleaders.hrbox.io/file/resize/1024x450/fde053db-23e9-4072-8b44-2621e9d74f70.jpg",
                "https://futureleaders.hrbox.io/file/resize/800x450/3331710e-5c99-4029-8b55-6528bf903a51.jpg",
                "https://futureleaders.hrbox.io/file/resize/800x450/041b3ad4-5425-4444-b0e0-3f99adabf541.jpg",
                "https://futureleaders.hrbox.io/file/resize/800x450/2c2fb9f7-ffac-43b1-89a0-d2aff03fb0ba.jpg",
                "https://futureleaders.hrbox.io/file/resize/800x450/bcc0694b-44e0-46e0-9e6d-7be1105ca2b9.jpg",
                "https://futureleaders.hrbox.io/file/resize/800x450/88671104-328d-47a9-a8f0-13f0608e6009.jpg",
                "https://futureleaders.hrbox.io/file/resize/800x450/5626bbc5-f7ae-46c1-a7dc-697ba427c0de.jpg",
                "https://futureleaders.hrbox.io/file/resize/800x450/c1d39521-c49c-49bc-9bfc-92802c60f493.jpg",
                "https://futureleaders.hrbox.io/file/resize/800x450/74d7d115-b18b-4d50-9ed2-cfd71396d2b1.jpg",
                "https://futureleaders.hrbox.io/file/resize/800x450/1ed81149-559f-4ec6-8bf3-c912a2dc7e8a.jpg"
        );
        List<String> names = Arrays.asList(
                "#КООРДИНАЦИОННЫЙ СОВЕТ",
                "#КООРДИНАЦИОННЫЙ СОВЕТ",
                "#КООРДИНАЦИОННЫЙ СОВЕТ",
                "#КООРДИНАЦИОННЫЙ СОВЕТ",
                "#НОВОСТИ ФОНДА",
                "#НОВОСТИ ФОНДА",
                "#КООРДИНАЦИОННЫЙ СОВЕТ",
                "#НОВОСТИ ФОНДА",
                "#КООРДИНАЦИОННЫЙ СОВЕТ",
                "#СТАРТАП"
        );

        String text = "Прямо сейчас у нас проходит заочный этап голосования, в котором голосуют все участники сообщества!\n" +
                "\n" +
                "Напоминаем, чтобы принять участие в выборах, вам необходимо отдать свой голос за одного из кандидатов через Координатора вашего набора. Полный список координаторов можно посмотреть в этой новости.\n" +
                "\n" +
                "Для того, чтобы не ошибиться и быть точно уверенным в своем решении советуем еще раз ознакомиться с программами наших кандидатов\n" +
                "\uD83D\uDD38 Рогова Юлия, участница \"Фонда Будущие лидеры\" \n" +
                "\uD83D\uDD38 Афанасьев Артем, участник \"Школы лидеров будущего\" \n" +
                "\uD83D\uDD38 Ненастьева Александра, участница \"Лицея будущих лидеров\"  \n" +
                "\n" +
                "Результаты первого этапа мы опубликуем уже 23 апреля!\n" +
                "Прочитать подробнее про все этапы выборов можно здесь \n" +
                "\n" +
                "В эти выходные пройдет выезд Координационного Совета, на котором состоится второй и третий этапы выборов. И уже в понедельник мы опубликуем имена Председателя и Руководителей направлений Координационного Совета!\n" +
                "\n" +
                "Следите за новостями, будет интересно!";
        for (int i = 0; i < uris.size(); i++) {
            Bitmap bitmap = null;
            if (Constants.mv.bitmaps.containsKey(i)) bitmap = Constants.mv.bitmaps.get(i);
            addElement(uris.get(i), names.get(i), bitmap, i).setOnClickListener(v -> {
                Intent intent = new Intent(requireContext(), OpenNewsFragment.class);
                intent.putExtra("title", "Первый этап выборов Председателя Координационного Совета в самом разгаре!");
                intent.putExtra("text", text);
                startActivity(intent);
            });
        }
        return root;
    }

    private View addElement(String uri, String name, Bitmap bitmap_, int pos) {
        ConstraintLayout row = (ConstraintLayout) inflater.inflate(R.layout.news_news, container, false);
        ConstraintLayout cn = row.findViewById(R.id.news_obj);
        if (bitmap_ != null)
            ((ImageView) cn.getChildAt(1)).setImageBitmap(Utils.getRoundedCornerBitmap(bitmap_, 10));
        else {
            Utils.getBitmapFromURL(uri, bitmap -> {
                if (bitmap == null) {
                    return;
                }
                Constants.mv.bitmaps.put(pos, bitmap);
                requireActivity().runOnUiThread(() -> ((ImageView) cn.getChildAt(1)).setImageBitmap(Utils.getRoundedCornerBitmap(bitmap, 10)));
            });
        }
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