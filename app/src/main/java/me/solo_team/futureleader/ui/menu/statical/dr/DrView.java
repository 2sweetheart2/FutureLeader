package me.solo_team.futureleader.ui.menu.statical.dr;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.imageview.ShapeableImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.Dr;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class DrView extends Her {

    LinearLayout list;
    List<Dr> drList;
    Dr currentDr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Ближайщие дни рождения");
        setContentView(R.layout.nearest_dr);
        list = findViewById(R.id.neares_dr_list);
        API.getNearestDr(new ApiListener() {
            Dialog d;

            @Override
            public void onError(JSONObject json) throws JSONException {
                System.out.println(json);
                d.dismiss();
            }

            @Override
            public void inProcess() {
                d = openWaiter(DrView.this);
            }

            @Override
            public void onSuccess(JSONObject json) throws JSONException {
                System.out.println(json);
                JSONArray jsonArray = json.getJSONArray("dates");
                drList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    drList.add(new Dr(jsonArray.getJSONObject(i)));
                }
                d.dismiss();
                runOnUiThread(() -> render());
            }
        }, new CustomString("token", Constants.user.token));
    }


    private void render() {
        list.removeAllViews();
        for (Dr dr : drList) {
            View view = getLayoutInflater().inflate(R.layout.dr_object, null);
            ShapeableImageView image = view.findViewById(R.id.dr_object_logo);
            Utils.getBitmapFromURL(dr.userProfilePicture, bitmap -> runOnUiThread(() -> image.setImageBitmap(bitmap)));
            ((TextView) view.findViewById(R.id.dr_object_name)).setText(dr.userName);
            ((TextView) view.findViewById(R.id.dr_object_date)).setText(dr.date.toVisibleStrV2());
            list.addView(view);
            view.setOnClickListener(v -> {
                currentDr = dr;
                AlertDialog.Builder obj = new AlertDialog.Builder(DrView.this);
                obj.setTitle("день рождения " + dr.userName);
                obj.setIcon(R.drawable.resize_300x0);
                obj.setPositiveButton("да", addCalendaryEvent);
                obj.setNegativeButton("нет", null);
                obj.setMessage("добавить этот день в календарь?");
                obj.show();


            });
        }
    }

    private final DialogInterface.OnClickListener addCalendaryEvent = (dialog, which) -> {
        if (Build.VERSION.SDK_INT >= 14) {
            Calendar cal = Calendar.getInstance();
            cal.set(currentDr.date.year, currentDr.date.month-1, currentDr.date.day);
            LocalDate locDate = LocalDate.of(currentDr.date.year, currentDr.date.month, currentDr.date.day);
            int age = Utils.calculateAge(locDate, LocalDate.now());
            Intent intent = new Intent(Intent.ACTION_INSERT)
                    .setData(CalendarContract.Events.CONTENT_URI)
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis())
                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, cal.getTimeInMillis())
                    .putExtra(CalendarContract.Events.TITLE, "День рождения " + currentDr.userName)
                    .putExtra(CalendarContract.Events.DESCRIPTION, "сегодня день рождения " + currentDr.userName + ". Ему исполняется " + (age + 1)+" лет")
                    .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
            startActivity(intent);
        } else {
            Calendar cal = Calendar.getInstance();
            Intent intent = new Intent(Intent.ACTION_EDIT);
            intent.setType("vnd.android.cursor.item/event");
            intent.putExtra("beginTime", cal.getTimeInMillis());
            intent.putExtra("allDay", true);
            intent.putExtra("rrule", "FREQ=YEARLY");
            intent.putExtra("endTime", cal.getTimeInMillis() + 60 * 60 * 1000);
            intent.putExtra("title", "День рождение " + currentDr.userName);
            startActivity(intent);
        }
    };
}
