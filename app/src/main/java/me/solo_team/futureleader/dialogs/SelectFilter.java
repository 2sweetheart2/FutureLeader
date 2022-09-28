package me.solo_team.futureleader.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import me.solo_team.futureleader.R;

public class SelectFilter extends AppCompatDialogFragment {

    public onChange onChange;

    int type;
    public SelectFilter(int type){
        this.type = type;
    }

    private CheckBox by_name;
    private CheckBox by_post;
    private CheckBox by_division;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.select_filter, null);
        builder.setView(view);
        // Остальной код

        by_name = view.findViewById(R.id.checkBox);
        by_post = view.findViewById(R.id.checkBox2);
        by_division = view.findViewById(R.id.checkBox3);
        switch (type){
            case 0:
                by_name.setChecked(true);
                break;
            case 1:
                by_post.setChecked(true);
                break;
            case 2:
                by_division.setChecked(true);
                break;
        }

        by_name.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                by_post.setChecked(false);
                by_division.setChecked(false);
            }
            onChange.change(0);
        });
        by_post.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                by_name.setChecked(false);
                by_division.setChecked(false);
            }
            onChange.change(1);
        });
        by_division.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                by_post.setChecked(false);
                by_name.setChecked(false);
            }
            onChange.change(2);
        });

        return builder.create();
    }

    public interface onChange {
        void change(int type);
    }


}
