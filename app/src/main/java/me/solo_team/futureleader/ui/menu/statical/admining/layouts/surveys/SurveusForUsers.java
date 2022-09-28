package me.solo_team.futureleader.ui.menu.statical.admining.layouts.surveys;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.solo_team.futureleader.Objects.ChatMember;
import me.solo_team.futureleader.Objects.User;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.dialogs.ChatInfodialog;
import me.solo_team.futureleader.dialogs.MemberAdapter;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.SelectMembers;

public class SurveusForUsers extends AppCompatActivity {
    MemberAdapter adapter;
    ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.only_list);
        Button button = findViewById(R.id.custom_btn);
        button.setText("указать пользователей");
        adapter = new MemberAdapter(SurveusForUsers.this, this, -1);
        Drawable wrappedDrawable = DrawableCompat.wrap(getDrawable(R.drawable.trash));
        DrawableCompat.setTint(wrappedDrawable, Color.BLACK);
        adapter.setCustomImageForUtilBtn(wrappedDrawable);

        adapter.addUtilBtn(member -> {
            selectedusers.remove(member);
            runOnUiThread(() -> adapter.remove(member));
        });
        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, SelectMembers.class);
            intent.putExtra("needStuff", false);
            intent.putExtra("checker", true);
            intent.putExtra("removeSelf", false);
            startActivityIfNeeded(intent, 101);
        });
        listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);

    }
    List<ChatMember> selectedusers = new ArrayList<>();

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "")
                .setIcon(R.drawable.done)
                .setOnMenuItemClickListener(item -> {
                    Intent data = new Intent();
                    List<String> usersInfo = new ArrayList<>();
                    for (ChatMember user : selectedusers) {
                        usersInfo.add(user.toChatMemder());
                    }
                    String[] stockArr = new String[usersInfo.size()];
                    usersInfo.toArray(stockArr);
                    data.putExtra("users",stockArr);
                    setResult(1,data);
                    finish();
                    return true;
                })
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return super.onPrepareOptionsMenu(menu);
    }

    private void addIntoAdapter() {
        adapter.clear();
        adapter.addAll(selectedusers);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if(resultCode==-500){
                Utils.ShowSnackBar.show(SurveusForUsers.this,"отказано в доступе!",listView);
                return;
            }
            if(resultCode==100) {
                String[] users = data.getStringArrayExtra("users");
                selectedusers.clear();
                for (String s : users) {
                    try {
                        selectedusers.add(new ChatMember(new JSONObject(s)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                addIntoAdapter();
            }
        }

    }
}
