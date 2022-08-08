package me.solo_team.futureleader.ui.menu.horizontal_menu.surveys.fragments;

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

public class SurveysComplete extends Fragment {
    LinearLayout list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.surveys_complete, container, false);
        list = root.findViewById(R.id.surveys_complete_list);
        list.removeAllViews();
        for (Surveys surveys : Constants.surveysCache.completeSurveys) {
            View view = getLayoutInflater().inflate(R.layout.surveys_object, null);
            ((TextView) view.findViewById(R.id.surveys_object_label)).setText(surveys.name);
            ((TextView) view.findViewById(R.id.surveys_object_date)).setText(surveys.date.toVisibleStr());
            ((TextView) view.findViewById(R.id.surveys_object_complete_status)).setText("пройдено");
            list.addView(view);
            view.setOnClickListener(v -> {
                Intent intent = new Intent(SurveysComplete.this.requireContext(), DoSurvey.class);
                intent.putExtra("type","check");
                intent.putExtra("id",surveys.id);
                startActivity(intent);
            });
        }

        return root;
    }
}
