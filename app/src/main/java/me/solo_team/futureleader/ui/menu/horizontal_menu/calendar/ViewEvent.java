package me.solo_team.futureleader.ui.menu.horizontal_menu.calendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
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
import me.solo_team.futureleader.MediaAudioAdapters.VideoAdapter.VideoView;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.Event;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.SelectMembers;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;
import me.solo_team.futureleader.ui.menu.statical.admining.layouts.users.StatusOfUsers;

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
                    if(json.getInt("code")==22)
                        createNotification(root, "Вы уже записались на это событие!");
                    else {
                        createNotification(root, json.getString("message"));
                    }
                    d.dismiss();
                }

                @Override
                public void inProcess() {
                    d = openWaiter(ViewEvent.this);
                }

                @Override
                public void onSuccess(JSONObject json) throws JSONException {
                    d.dismiss();
                    setResult(1);
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
        if (Constants.user.permission.can_delete_event) {
            menu.add(0, 1, 0, "")
                    .setIcon(R.drawable.trash)
                    .setTitle("")
                    .setOnMenuItemClickListener(item -> {
                        if(!Constants.user.permission.can_delete_event){
                            Utils.ShowSnackBar.show(ViewEvent.this,"отказано в доступе!",label);
                            return false;
                        }
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
        }
        if(Constants.user.permission.can_get_events_tickets){
            menu.add(0, 2, 0, "")
                    .setIcon(R.drawable.menu_white)
                    .setTitle("")
                    .setOnMenuItemClickListener(item -> {
                        if(!Constants.user.permission.can_get_events_tickets){
                            Utils.ShowSnackBar.show(ViewEvent.this,"отказано в доступе!",label);
                            return false;
                        }
                        API.getTickets(new ApiListener() {
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
                                runOnUiThread(() -> {
                                    Intent intent = new Intent(ViewEvent.this, SelectMembers.class);
                                    intent.putExtra("showStatistic", false);
                                    intent.putExtra("needStuff", false);
                                    try {
                                        intent.putExtra("users", String.valueOf(json.getJSONArray("users")));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    startActivityIfNeeded(intent, 101);
                                });
                                d.dismiss();
                            }
                        }, new CustomString("token", Constants.user.token), new CustomString("id", String.valueOf(event.id)));
                        return true;
                    })
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        }
        return super.onPrepareOptionsMenu(menu);
    }
}
