package me.solo_team.futureleader.ui.news.open_news;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.ChatMember;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.dialogs.ChatInfodialog;
import me.solo_team.futureleader.dialogs.MemberAdapter;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class CheckUsers extends Her {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Статистика");
        setContentView(R.layout.only_list);
        ((Button) findViewById(R.id.custom_btn)).setVisibility(View.GONE);
        ListView list = findViewById(R.id.list_view);
        List<ChatMember> members = new ArrayList<>();
        boolean liked = getIntent().getBooleanExtra("liked",true);
        JSONObject currentNew = Constants.newsCache.curentNew;
        try {
            if (liked) {
                for (int i = 0; i<currentNew.getJSONArray("likes_users").length();i++){
                    members.add(new ChatMember(currentNew.getJSONArray("likes_users").getJSONObject(i)));
                }
            }
            else{
                for (int i = 0; i<currentNew.getJSONArray("views_users").length();i++){
                    members.add(new ChatMember(currentNew.getJSONArray("views_users").getJSONObject(i)));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }// Остальной код
        MemberAdapter adapter = new MemberAdapter(CheckUsers.this, this,-1);
        adapter.needCustomButton = false;
        adapter.addUtilBtn(member -> {});
        adapter.addAll(members);
        list.setAdapter(adapter);
    }
}
