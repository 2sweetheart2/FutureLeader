package me.solo_team.futureleader.ui.menu.horizontal_menu.surveys.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.Surveys;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.menu.horizontal_menu.surveys.DoSurvey;

public class SurveysForMe extends Fragment implements DialogInterface.OnClickListener {
    LinearLayout list;
    Surveys currentSurvey;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.surveys_for_me, container, false);
        list = root.findViewById(R.id.surveys_for_me_list);

        for(Surveys surveys : Constants.surveysCache.surveysForUser){
            View view = getLayoutInflater().inflate(R.layout.surveys_object,null);
            ((TextView) view.findViewById(R.id.surveys_object_label)).setText(surveys.name);
            ((TextView) view.findViewById(R.id.surveys_object_date)).setText(surveys.date.toVisibleStr());
            if(Constants.surveysCache.checkComplete(surveys))
                ((TextView) view.findViewById(R.id.surveys_object_complete_status)).setText("пройдено");
            else{
                view.setOnClickListener(v -> {
                    AlertDialog.Builder obj = new AlertDialog.Builder(requireContext());
                    obj.setTitle("Пройти опрос?");
                    obj.setIcon(R.drawable.resize_300x0);
                    obj.setPositiveButton("пройти", SurveysForMe.this);
                    obj.setNegativeButton("Закрыть", null);
                    obj.setMessage("хотите пройти это опрос?");
                    obj.show();
                    currentSurvey = surveys;
                });
            }
            list.addView(view);
        }
        return root;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Intent intent = new Intent(SurveysForMe.this.requireContext(), DoSurvey.class);
        intent.putExtra("type","me");
        intent.putExtra("id",currentSurvey.id);
        startActivity(intent);
    }
}
