package me.solo_team.futureleader.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import me.solo_team.futureleader.R;

public class EditFieldsDialog extends AppCompatDialogFragment {

    PressOk callback;
    String message;
    String fildsName;
    public EditFieldsDialog(PressOk callbakc,String fieldsName,String message){
        this.callback = callbakc;
        this.fildsName=fieldsName;
        this.message = message;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String message = "Вы уверены что хотите изменить \""+fildsName+"\"?";
        if(this.message!=null) message=this.message;
        builder.setTitle("Изменение поля")
                .setMessage(message)
                .setIcon(R.drawable.resize_300x0)
                .setPositiveButton("Да", (dialog, id) -> {
                    // Закрываем окно
                    callback.pressOk(true);
                    dialog.cancel();
                }).setNegativeButton("Нет",(dialog, which) -> {
                    callback.pressOk(false);
                    dialog.cancel();
        });
        return builder.create();
    }

    public interface PressOk{
        void pressOk(boolean result);
    }
}
