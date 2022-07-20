package me.solo_team.futureleader.ui.menu.horizontal_menu.calendar;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.Event;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class ViewEvent extends Her {
    TextView label;
    TextView date;
    TextView timeFrom;
    TextView timeTo;
    TextView maxPeople;
    TextView eventType;
    TextView description;
    Button createTicket;
    Event event;
    View root;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_event);
        try {
            event = new Event(new JSONObject(getIntent().getStringExtra("payload")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (event == null) {
            finish();
        }
        setTitle("Событие");
        initWidget();
        label.setText(event.name);
        date.setText("дата: " + event.date.toVisibleStr());
        timeFrom.setText("начало: " + event.timeFrom.toStr());
        timeTo.setText("конец: " + event.timeTo.toStr());
        maxPeople.setText("максимум цчастников: " + event.maxMembers + " (осталось " + event.freePlace + ")");
        eventType.setText("тип мероприятия: " + event.type);
        description.setText(event.description);
        root = findViewById(R.id.view_event);
        createTicket.setOnClickListener(v -> {
            API.createTicket(new ApiListener() {
                Dialog d;

                @Override
                public void onError(JSONObject json) throws JSONException {
                    createNotification(root, json.getString("message"));
                    d.dismiss();
                }

                @Override
                public void inProcess() {
                    d = openWaiter(ViewEvent.this);
                }

                @Override
                public void onSuccess(JSONObject json) throws JSONException {
                    d.dismiss();
                    finish();
                }
            }, new CustomString("token", Constants.user.token), new CustomString("id", String.valueOf(event.id)));
        });

    }

    private void initWidget() {
        label = findViewById(R.id.view_event_label);
        date = findViewById(R.id.view_event_date);
        timeFrom = findViewById(R.id.view_event_from);
        timeTo = findViewById(R.id.view_event_to);
        maxPeople = findViewById(R.id.view_event_max_people);
        eventType = findViewById(R.id.view_event_type);
        description = findViewById(R.id.view_event_description);
        createTicket = findViewById(R.id.view_event_create_ticket);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (Constants.user.adminStatus != 0) {
            menu.add(0, 1, 0, "")
                    .setIcon(R.drawable.trash)
                    .setTitle("")
                    .setOnMenuItemClickListener(item -> {
                        API.deleteEvent(new ApiListener() {
                            Dialog d;

                            @Override
                            public void onError(JSONObject json) throws JSONException {
                                createNotification(root, json.getString("message"));
                                d.dismiss();
                            }

                            @Override
                            public void inProcess() {
                                d = openWaiter(ViewEvent.this);
                            }

                            @Override
                            public void onSuccess(JSONObject json) throws JSONException {
                                d.dismiss();
                                finish();
                            }
                        }, new CustomString("token", Constants.user.token), new CustomString("id", String.valueOf(event.id)));
                        return true;
                    })
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        } else menu.removeItem(1);
        return super.onPrepareOptionsMenu(menu);
    }
}
