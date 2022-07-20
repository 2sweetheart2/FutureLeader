package me.solo_team.futureleader.ui.menu.statical.admining.layouts.users;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.User;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;
import me.solo_team.futureleader.ui.profile.ProfileInfoGrid;

public class ViewUserProfile extends Her {

    private ImageView picture;
    private TextView name;
    private TextView description;
    TableLayout tableLayout;
    ProfileInfoGrid grid;
    LinkedHashMap<String, String> notAddedItems = new LinkedHashMap<>();
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);
        View root = findViewById(R.id.profile_main);
        API.getUser(new ApiListener() {
            @Override
            public void onError(JSONObject json) throws JSONException {
                createNotification(root,json.getString("message"));
                runOnUiThread(()->setTitle("Ошибка"));
            }

            @Override
            public void inProcess() {

            }

            @Override
            public void onSuccess(JSONObject json) throws JSONException {
                User user = new User();
                System.out.println(json);
                user.firstName = json.getString("first_name");
                user.lastName = json.getString("last_name");
                user.achievementsIds = json.getString("achievement_ids");
                user.adminStatus = json.getInt("admin_status");
                user.age = json.getInt("age");
                user.addFields(json.getString("fields"));
                user.id = json.getInt("id");
                user.profilePictureLink = json.getString("profile_picture");
                user.status = json.getString("status");
                user.token = json.getString("token");
                Constants.currentUser = user;
                loadInfo();
            }
        },new CustomString("token",Constants.user.token),new CustomString("id",getIntent().getStringExtra("id")));
    }

    private void loadInfo() {
    }
}
