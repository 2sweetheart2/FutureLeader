package me.solo_team.futureleader.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import me.solo_team.futureleader.R;

public class ProfileFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        TableLayout tableLayout = root.findViewById(R.id.profile_table_layout);
        ProfileInfoGrid grid = new ProfileInfoGrid(tableLayout,root.getContext(),inflater,container);
        grid.addElement("Город","Санкт-Петербург");
        grid.addElement("День рождения","14 декабря");
        grid.addRow();
        grid.addElement("Должность","участник ШБЛ");
        grid.addElement("Подразделение","Школа Лидеров Будущего > Санкт-Петербург > Набор 2021");
        grid.addElement("Стаж","5 месяцев 26 дней (c 01.11.2021)");
        grid.addRow("КОНТАКТЫ");
        grid.addElement("Личный","+7 921 551-87-84");
        grid.addElement("Личный","gleb.rosin11@gmail.com");
        grid.addElement("Telegram","Poxodnoe_vremiy");
        grid.addElement("WhatsApp","89215518784");
        return root;
    }
}