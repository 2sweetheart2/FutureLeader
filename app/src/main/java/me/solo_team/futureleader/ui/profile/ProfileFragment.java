package me.solo_team.futureleader.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.API.FullApiListener;
import me.solo_team.futureleader.Objects.Achievement;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.R;

public class ProfileFragment extends Fragment {

    private ImageView picture;
    private TextView name;
    private TextView description;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        requireActivity().invalidateOptionsMenu();
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        TableLayout tableLayout = root.findViewById(R.id.profile_table_layout);
        ProfileInfoGrid grid = new ProfileInfoGrid(tableLayout, root.getContext(), inflater, container);
        RecyclerView recyclerView = root.findViewById(R.id.profile_achievement);
        picture = root.findViewById(R.id.profile_picture);
        name = root.findViewById(R.id.profile_name);
        description = root.findViewById(R.id.profile_description);

        String imageGledUrl = "https://sun9-63.userapi.com/s/v1/if2/ndSILCx38FMurteLrqMWT9JT5SsHSc7aUxRmX1_0kX2rb_MikptV6bznqP2Z3qU060QDdUZaPVkB6RpLXoIfeHX6.jpg?size=300x400&quality=96&type=album";
        name.setText("Глеб Росин");
        description.setText("Aut viam inveniam, aut faciam.");
        //Constants.cache.addPhoto(imageGledUrl, true, picture, this);
//        switch (MainActivity.wightwindowSizeClass) {
//            case COMPACT:
//                name.setTextSize(18);
//                description.setTextSize(14);
//                break;
//            case MEDIUM:
//                name.setTextSize(22);
//                description.setTextSize(17);
//                break;
//            case EXPANDED:
//                name.setTextSize(25);
//                description.setTextSize(20);
//                break;
//        }
        picture.requestLayout();
        grid.addElement("Город", "Санкт-Петербург");
        grid.addElement("День рождения", "14 декабря");
        grid.addRow();
        grid.addElement("Должность", "участник ШБЛ");
        grid.addElement("Подразделение", "Школа Лидеров Будущего > Санкт-Петербург > Набор 2021");
        grid.addElement("Стаж", "5 месяцев 26 дней (c 01.11.2021)");
        grid.addRow("КОНТАКТЫ");
        grid.addElement("Личный", "+7 921 551-87-84");
        grid.addElement("Личный", "gleb.rosin11@gmail.com");
        grid.addElement("Telegram", "Poxodnoe_vremiy");
        grid.addElement("WhatsApp", "89215518784");


        String url = "https://futureleaders.hrbox.io/file/resize/400x400/5b2cb39e-f75a-4a81-b3a2-4c9efd8c892f.png";
        Achievement ach = new Achievement();
        ach.coins = 15;
        ach.description = "Награда самому активному участнику по мнению спикера образовательного мероприятия!";
        ach.name = "Активное участие на мероприятии";
        ach.image_url = url;
        ach.id = 0;
        RecycleAchivementsAdapter adapter = new RecycleAchivementsAdapter(this,
                Collections.singletonList(ach)
        );
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        /**
         * тут крч в мы создаём новый прослушиватель евента, и когда мы отправляем запрос, то прослушиваем ответ который прийдёт
         * если в ответе SUCCESS==false, то сработает метод onError в котором будет response
         * если же иначе SUCCESS==true, то сработает метод onSuccess в котором будет response
         */
        API.loginUser(new ApiListener() {
            @Override
            public void onError(JSONObject json){
                try {
                    this.createNotification(ProfileFragment.this.getView(),json.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(JSONObject json) {

            }
        }, new CustomString("test", "test"));

        API.getNews(new FullApiListener() {
            @Override
            public void inProgress() {
                System.out.println("[GET NEWS] -> IN PROGRESS");
            }

            @Override
            public void onFinish() {
                System.out.println("[GET NEWS] -> FINISHED...GETTING RESULT");
            }

            @Override
            public void onError(JSONObject json) {
                System.out.println("[GET NEWS] -> ERROR");
            }

            @Override
            public void onSuccess(JSONObject json) {
                System.out.println("[GET NEWS] -> SUCCESS");
            }
        },new CustomString("test","test"));
        return root;
    }


}