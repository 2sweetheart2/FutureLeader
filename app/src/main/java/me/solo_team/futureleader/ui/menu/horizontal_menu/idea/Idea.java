package me.solo_team.futureleader.ui.menu.horizontal_menu.idea;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.IdeasStuff;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;
import me.solo_team.futureleader.ui.news.open_news.EditNews;

public class Idea extends Her {
    IdeasStuff ideasStuff;
    LinearLayout list;
    String val;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ideas_view);
        View rootView = findViewById(R.id.ideas_view);
        val = getIntent().getStringExtra("extra");
        if (val != null) {
            findViewById(R.id.ideas_btn).setVisibility(View.GONE);
        }
        list = findViewById(R.id.ideas_list);
        setTitle("Идеи");
        findViewById(R.id.ideas_btn).setOnClickListener(v -> {
            if (ideasStuff.waitIdea.size() == 0) {
                Toast.makeText(this, "Ещё нет одобренных идей", Toast.LENGTH_LONG).show();
                return;
            }
            Intent intent = new Intent(getApplicationContext(), Idea.class);
            intent.putExtra("extra", "123");
            startActivity(intent);
        });
        API.getIdeas(new ApiListener() {
            Dialog d;

            @Override
            public void onError(JSONObject json) throws JSONException {
                d.dismiss();
                this.createNotification(rootView, json.getString("message"));
            }

            @Override
            public void inProcess() {
                d = this.openWaiter(Idea.this);
            }

            @Override
            public void onSuccess(JSONObject json) throws JSONException {
                ideasStuff = new IdeasStuff(json.getJSONArray("waited"), json.getJSONArray("complete"));
                if (val == null)
                    addAll(ideasStuff.userIdeas);
                else
                    addAll(ideasStuff.waitIdea);
                d.dismiss();
            }
        }, new CustomString("token", Constants.user.token));


    }

    public void addAll(List<IdeasStuff.Idea> ideas) {
        runOnUiThread(()->list.removeAllViews());
        for (IdeasStuff.Idea idea : ideas) {
            View view = getLayoutInflater().inflate(R.layout.idea_object, null);
            TextView text = view.findViewById(R.id.idea_object_label);
            text.setText(idea.label);
            ImageView status = view.findViewById(R.id.idea_object_status);
            switch (idea.status) {
                case "wait":
                    status.setColorFilter(Color.YELLOW);
                    break;
                case "deny":
                    status.setColorFilter(Color.RED);
                    break;
                case "allow":
                    status.setColorFilter(Color.GREEN);
                    break;
            }

            runOnUiThread(() -> {
                text.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), Showidea.class);
                    intent.putExtra("label", idea.label);
                    intent.putExtra("text", idea.text);
                    if(idea.comment!=null)
                        intent.putExtra("comment",idea.comment);
                    intent.putExtra("status", idea.status);
                    startActivity(intent);
                });
                if (idea.status.equals("wait"))
                    status.setOnClickListener(v -> Toast.makeText(this, "Ваша идея ещё рассматривается", Toast.LENGTH_LONG).show());
                list.addView(view);
            });
        }


    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (menu.size() == 0 && val==null)
            menu.add(0, 1, 0, "")
                    .setIcon(R.drawable.plus)
                    .setOnMenuItemClickListener(item -> {
                        startActivityIfNeeded(new Intent(this,CreateIdea.class),100);
                        return true;
                    })
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            if(resultCode==-1)
                Utils.ShowSnackBar.show(Idea.this,"что-то пошло не так :/",list);
            if(resultCode==1) {
                assert data != null;
                ideasStuff.waitIdea.add(new IdeasStuff.Idea(data.getStringExtra("label"),data.getStringExtra("text"),Constants.user.id,"wait"));
                addAll(ideasStuff.waitIdea);
                Utils.ShowSnackBar.show(Idea.this, "идея успешно создана!", list);
            }
        }
    }
}
