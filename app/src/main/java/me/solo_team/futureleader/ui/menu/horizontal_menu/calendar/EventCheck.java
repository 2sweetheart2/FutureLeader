package me.solo_team.futureleader.ui.menu.horizontal_menu.calendar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.imageview.ShapeableImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.Date;
import me.solo_team.futureleader.Objects.Event;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class EventCheck extends Her {

    Date currentDate;
    LinearLayout list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendary_v2);
        setTitle("События");
        currentDate = new Date(getIntent().getStringExtra("date"));
        list = findViewById(R.id.event_list);
        getEvents();
    }

    private void getEvents() {
        API.getEvents(new ApiListener() {
            Dialog d;
                          @Override
                          public void onError(JSONObject json) throws JSONException {
                            System.out.println(json);
                            d.dismiss();
                            finish();
                          }

                          @Override
                          public void inProcess() {
                                d = openWaiter(EventCheck.this);
                          }

                          @Override
                          public void onSuccess(JSONObject json) throws JSONException {
                              JSONArray events = json.getJSONArray("events");
                              List<Event> eventList = new ArrayList<>();
                              for(int i=0;i<events.length();i++) {
                                  eventList.add(new Event(events.getJSONObject(i)));
                              }
                              d.dismiss();
                              runOnUiThread(()->addEventsInList(eventList));


                          }
                      },
                new CustomString("token", Constants.user.token),
                new CustomString("date", currentDate.toStr())
        );

    }

    private void addEventsInList(List<Event> eventList) {
        for(Event event: eventList) {
            View eventObject = getLayoutInflater().inflate(R.layout.event_object, null);
            Utils.getBitmapFromURL(event.urlLogo, bitmap -> {
                if(bitmap!=null){
                    runOnUiThread(()->((ShapeableImageView) eventObject.findViewById(R.id.event_object_image)).setImageBitmap(bitmap));
                }
            });
            Utils.getBitmapFromURL(event.creatorPhoto,bitmap -> {
                if(bitmap!=null){
                    runOnUiThread(()->((ShapeableImageView) eventObject.findViewById(R.id.event_object_creator_image)).setImageBitmap(bitmap));
                }
            });
            ((TextView) eventObject.findViewById(R.id.event_object_creator_name)).setText(event.creatorName);
            ((TextView) eventObject.findViewById(R.id.event_object_label)).setText(event.name);
            list.addView(eventObject);
            eventObject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(EventCheck.this, ViewEvent.class);
                    intent.putExtra("payload",event.payload.toString());
                    startActivityIfNeeded(intent,100);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            if(resultCode==1)
                Utils.ShowSnackBar.show(EventCheck.this,"вы записались на это событие!",list);
        }
    }
}
