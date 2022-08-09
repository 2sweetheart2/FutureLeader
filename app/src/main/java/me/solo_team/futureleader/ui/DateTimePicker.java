package me.solo_team.futureleader.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.GregorianCalendar;

import me.solo_team.futureleader.Objects.Date;
import me.solo_team.futureleader.Objects.Time;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.dialogs.SelectTimeDialog;
import me.solo_team.futureleader.stuff.Utils;

public class DateTimePicker extends AppCompatActivity {

    Time timeT;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datetime_picker);
        TextView timePicker = findViewById(R.id.date_time_btn);
        timePicker.setOnClickListener(v -> {
            SelectTimeDialog selectTimeDialog = new SelectTimeDialog(time -> {
                timeT = time;
                timePicker.setText("время: " + time.toStr());
            });
            selectTimeDialog.show(getSupportFragmentManager(), null);
        });
        findViewById(R.id.date_time_set).setOnClickListener(view -> {

            DatePicker datePicker = findViewById(R.id.date_picker);

            if(timeT==null){
                Utils.ShowSnackBar.show(DateTimePicker.this,"Время не выбрано!",view);
                return;
            }
            Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                    datePicker.getMonth(),
                    datePicker.getDayOfMonth(),
                    timeT.hour,
                    timeT.minute);

            long time = calendar.getTimeInMillis();
            Intent data = new Intent();
            data.putExtra("date", (new Date(datePicker.getDayOfMonth(), datePicker.getMonth(), datePicker.getYear())).toStr());
            data.putExtra("time", timeT.toStr());
            setResult(1, data);
            finish();
        });
    }
}
