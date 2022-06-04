package me.solo_team.futureleader.ui.news;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import org.json.JSONException;

import me.solo_team.futureleader.R;

public class AlertDialogForDeleteField extends AppCompatDialogFragment {

    public interface PressOk {
        void pressOk(boolean result,int view) throws JSONException;
    }

    PressOk callback;
    int view;
    public AlertDialogForDeleteField(PressOk callback, int index) {
        this.callback = callback;
        this.view = index;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Удалить")
                .setMessage("Вы уверены что хотите удалить это поле?")
                .setIcon(R.drawable.resize_300x0)
                .setPositiveButton("Да", (dialog, id) -> {
                    try {
                        callback.pressOk(true,view);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    dialog.cancel();
                })
                .setNegativeButton("Нет", (dialog, which) -> {
                    try {
                        callback.pressOk(false,view);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
        return builder.create();
    }
}
