package me.solo_team.futureleader.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import me.solo_team.futureleader.R;

public class SaveOrSeePhoto extends AppCompatDialogFragment {
    SaveOrSeePhoto.PressSee callback;
    public SaveOrSeePhoto(SaveOrSeePhoto.PressSee callback){
        this.callback = callback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Просмотреть или скачать?")
                .setIcon(R.drawable.resize_300x0)
                .setPositiveButton("Скачать", (dialog, id) -> {
                    // Закрываем окно
                    callback.pressSee(false);
                    dialog.cancel();
                }).setNegativeButton("Просмотреть",(dialog, which) -> {
            callback.pressSee(true);
            dialog.cancel();
        });
        return builder.create();
    }

    public interface PressSee{
        void pressSee(boolean result);
    }
}
