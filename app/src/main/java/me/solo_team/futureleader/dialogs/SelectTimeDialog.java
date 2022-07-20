package me.solo_team.futureleader.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import me.solo_team.futureleader.Objects.Time;
import me.solo_team.futureleader.R;

public class SelectTimeDialog extends AppCompatDialogFragment {

    TimeSelector callbakc;
    public SelectTimeDialog(TimeSelector callback){
        this.callbakc = callback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        TimePickerDialog.OnTimeSetListener myTimeListener = (view, hourOfDay, minute) -> {
            if (view.isShown()) {
                callbakc.selectTime(new Time(hourOfDay,minute));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, 0, 0, true);
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return  timePickerDialog;
    }



    public interface TimeSelector{
        public void selectTime(Time time);
    }
}
