package me.solo_team.futureleader.ui.menu.statical.admining.layouts.surveys;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.ChatMember;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.IdeasStuff;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class Ideas extends Her {

    LinearLayout list;
    HashMap<ChatMember, List<IdeasStuff.Idea>> ideasList = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Идеи");
        setContentView(R.layout.only_linearlayout);
        list = findViewById(R.id.list);
        API.getWaitedIdeas(new ApiListener() {
            Dialog d;

            @Override
            public void onError(JSONObject json) throws JSONException {
                System.out.println(json.getString("message"));
                Intent intent = new Intent();
                intent.putExtra("message", json.getString("message"));
                setResult(-100, intent);
                d.dismiss();
                finish();
            }

            @Override
            public void inProcess() {
                d = openWaiter(Ideas.this);
            }

            @Override
            public void onSuccess(JSONObject json) throws JSONException {
                JSONArray array = json.getJSONArray("ideas");
                ideasList.clear();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject id = array.getJSONObject(i).getJSONObject("idea");
                    JSONObject cm = array.getJSONObject(i).getJSONObject("user");
                    ChatMember chatMember = new ChatMember(cm);
                    IdeasStuff.Idea idea = new IdeasStuff.Idea(id);
                    if (ideasList.containsKey(chatMember))
                        ideasList.get(chatMember).add(idea);
                    else
                        ideasList.put(chatMember, Collections.singletonList(idea));
                }
                runOnUiThread(() -> {
                    render();
                    d.dismiss();
                });
            }
        }, new CustomString("token", Constants.user.token));
    }

    HashMap<IdeasStuff.Idea, View> views = new HashMap<>();

    private void render() {
        list.removeAllViews();
        for (Map.Entry<ChatMember, List<IdeasStuff.Idea>> entry : ideasList.entrySet()) {
            for (IdeasStuff.Idea idea : entry.getValue()) {
                View view = getLayoutInflater().inflate(R.layout.admin_idea, null);
                Constants.cache.addPhoto(entry.getKey().profilePicture, true, view.findViewById(R.id.admin_idea_user_logo), this);
                ((TextView) view.findViewById(R.id.admin_idea_name)).setText(idea.label);
                ((TextView) view.findViewById(R.id.admin_idea_user_name)).setText(entry.getKey().getFullName());
                view.setOnClickListener(v -> {
                    Intent intent = new Intent(Ideas.this, SubmitIdea.class);
                    intent.putExtra("label", idea.label);
                    intent.putExtra("id", idea.id);
                    intent.putExtra("text", idea.text);
                    intent.putExtra("logo", entry.getKey().profilePicture);
                    intent.putExtra("name", entry.getKey().getFullName());
                    intent.putExtra("user_id", entry.getKey().userId);
                    startActivityIfNeeded(intent, 100);
                });
                views.put(idea, view);
                list.addView(view);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1 && requestCode == 100) {
            assert data != null;
            int id_ = data.getIntExtra("idea_id", -1);
            for (Map.Entry<IdeasStuff.Idea, View> s : views.entrySet())
                if (s.getKey().id == id_) {
                    list.removeView(s.getValue());
                    views.remove(s.getKey());
                    break;
                }
            Utils.ShowSnackBar.show(Ideas.this, "Решение успешно вынесено!", list);
        }
        if (resultCode == -1 && requestCode == 100) {
            assert data != null;
            Utils.ShowSnackBar.show(Ideas.this, data.getStringExtra("message"), list);
        }
    }
}
