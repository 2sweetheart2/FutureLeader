package me.solo_team.futureleader.ui.menu.statical.admining.layouts.gameifecation;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.Permission;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;
import me.solo_team.futureleader.ui.news.open_news.EditNews;

public class ChangeRole extends Her {
    Permission permission;

    EditText editText;
    CheckBox role_can_add_new;
    CheckBox role_can_view_new_views;
    CheckBox role_can_view_like_views;
    CheckBox role_can_view_admin_panel;
    CheckBox role_can_get_ideas;
    CheckBox role_can_set_ideas_status;
    CheckBox role_can_get_users;
    CheckBox role_can_get_user;
    CheckBox role_can_get_admin_roles;
    CheckBox role_can_chage_admin_role;
    CheckBox role_can_get_log_log;
    CheckBox role_can_get_file_log;
    CheckBox role_can_view_shop_request;
    CheckBox role_can_set_shop_request;
    CheckBox role_can_get_unverified_users;
    CheckBox role_can_verifi_user;
    CheckBox role_can_get_stat;
    CheckBox role_can_create_achievement;
    CheckBox role_can_remove_achievement;
    CheckBox role_can_add_achievement_user;
    CheckBox role_can_get_complete_surveys;
    CheckBox role_can_delete_survey;
    CheckBox role_can_create_surveys;
    CheckBox role_can_delete_event;
    CheckBox role_can_add_event;
    CheckBox role_can_create_admin_role;
    CheckBox role_can_remove_role;
    CheckBox role_can_get_who_has_this_role;
    CheckBox role_can_update_role;
    CheckBox role_can_get_events_tickets;
    CheckBox role_can_remove_unverifi_users;
    CheckBox role_can_get_all_currency;
    CheckBox role_can_add_currency;
    CheckBox role_can_add_mentors;
    CheckBox role_can_edit_profile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_role);

        role_can_remove_unverifi_users = findViewById(R.id.role_can_remove_unverifi_users);
        role_can_add_mentors = findViewById(R.id.role_can_add_mentors);
        role_can_edit_profile = findViewById(R.id.role_can_edit_profile);
        role_can_add_currency = findViewById(R.id.role_can_add_currency);
        role_can_get_all_currency = findViewById(R.id.role_can_get_all_currency);
        role_can_update_role = findViewById(R.id.role_can_update_role);
        role_can_get_who_has_this_role = findViewById(R.id.role_can_get_who_has_this_role);
        role_can_remove_role = findViewById(R.id.role_can_remove_role);
        role_can_create_admin_role = findViewById(R.id.role_can_create_admin_role);
        role_can_add_event = findViewById(R.id.role_can_add_event);
        role_can_delete_event = findViewById(R.id.role_can_delete_event);
        role_can_create_surveys = findViewById(R.id.role_can_create_surveys);
        role_can_delete_survey = findViewById(R.id.role_can_delete_survey);
        role_can_get_complete_surveys = findViewById(R.id.role_can_get_complete_surveys);
        role_can_add_achievement_user = findViewById(R.id.role_can_add_achievement_user);
        role_can_remove_achievement = findViewById(R.id.role_can_remove_achievement);
        role_can_create_achievement = findViewById(R.id.role_can_create_achievement);
        role_can_get_stat = findViewById(R.id.role_can_get_stat);
        role_can_verifi_user = findViewById(R.id.role_can_verifi_user);
        role_can_get_unverified_users = findViewById(R.id.role_can_get_unverified_users);
        role_can_set_shop_request = findViewById(R.id.role_can_set_shop_request);
        role_can_view_shop_request = findViewById(R.id.role_can_view_shop_request);
        role_can_get_file_log = findViewById(R.id.role_can_get_file_log);
        role_can_get_log_log = findViewById(R.id.role_can_get_log_log);
        role_can_chage_admin_role = findViewById(R.id.role_can_chage_admin_role);
        role_can_get_admin_roles = findViewById(R.id.role_can_get_admin_roles);
        role_can_get_user = findViewById(R.id.role_can_get_user);
        role_can_get_users = findViewById(R.id.role_can_get_users);
        role_can_set_ideas_status = findViewById(R.id.role_can_set_ideas_status);
        role_can_get_ideas = findViewById(R.id.role_can_get_ideas);
        role_can_view_admin_panel = findViewById(R.id.role_can_view_admin_panel);
        role_can_view_like_views = findViewById(R.id.role_can_view_like_views);
        role_can_view_new_views = findViewById(R.id.role_can_view_new_views);
        role_can_add_new = findViewById(R.id.role_can_add_new);
        role_can_get_events_tickets = findViewById(R.id.role_can_get_events_tickets);
        editText = findViewById(R.id.admin_role_name);
        if (getIntent().hasExtra("perm")) {
            if(!Constants.user.permission.can_update_role){
                setResult(-500);
                finish();
            }
            permission = (Permission) getIntent().getExtras().getSerializable("perm");
            setTitle("изменение роли");
            setInfo();
        }else{
            setTitle("создание роли");
            if(!Constants.user.permission.can_create_admin_role){
                setResult(-500);
                finish();
            }
        }

    }

    public void setInfo() {
        try {
            for (Field field : permission.getClass().getDeclaredFields()) {
                if (field.getName().equals("can_get_reg_log") || field.getName().equals("id"))
                    continue;
                if (field.getName().equals("name")) {
                    editText.setText((String) field.get(permission));
                    continue;
                }
                ((CheckBox) this.getClass().getDeclaredField(("role_" + field.getName())).get(this)).setChecked(field.getBoolean(permission));
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "")
                .setIcon(R.drawable.done)
                .setOnMenuItemClickListener(item -> {
                    StringBuilder text = new StringBuilder();
                    List<CustomString> params = new ArrayList<>();
                    for(Field field : getClass().getDeclaredFields()){
                        try {
                            if(field.getName().equals("permission"))
                                continue;
                            if(field.getName().equals("editText")){
                                text.append("name="+((EditText)field.get(ChangeRole.this)).getText().toString()).append("&");
                                params.add(new CustomString("name",((EditText)field.get(ChangeRole.this)).getText().toString()));
                                continue;
                            }
                            text.append(field.getName().substring(5)).append('=').append(boolToInt(((CheckBox) field.get(ChangeRole.this)).isChecked())).append("&");
                            params.add(new CustomString(field.getName().substring(5),String.valueOf(boolToInt(((CheckBox) field.get(ChangeRole.this)).isChecked()))));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    params.add(new CustomString("token",Constants.user.token));
                    params.add(new CustomString("can_get_reg_log","0"));
                    if(getTitle().toString().equals("изменение роли")){
                        if(editText.getText().length()==0){
                            Utils.ShowSnackBar.show(ChangeRole.this,"название роли не должно быть пустым!",editText);
                            return false;
                        }
                        API.updateRole(new ApiListener() {
                            Dialog d;
                            @Override
                            public void onError(JSONObject json) throws JSONException {
                                d.dismiss();
                                createNotification(editText,json.getString("message"));
                            }

                            @Override
                            public void inProcess() {
                                d = openWaiter(ChangeRole.this);
                            }

                            @Override
                            public void onSuccess(JSONObject json) throws JSONException {
                                d.dismiss();
                                setResult(2);
                                finish();
                            }
                        },new CustomString("token",Constants.user.token),new CustomString("id",String.valueOf(permission.id)),new CustomString("params",text.substring(0,text.length()-1)));
                    }
                    else if(getTitle().toString().equals("создание роли")){
                        if(editText.getText().length()==0){
                            Utils.ShowSnackBar.show(ChangeRole.this,"название роли не должно быть пустым!",editText);
                            return false;
                        }
                        API.createRole(new ApiListener() {
                            Dialog d;
                            @Override
                            public void onError(JSONObject json) throws JSONException {
                                d.dismiss();
                                createNotification(editText,json.getString("message"));
                            }

                            @Override
                            public void inProcess() {
                                d = openWaiter(ChangeRole.this);
                            }

                            @Override
                            public void onSuccess(JSONObject json) throws JSONException {
                                setResult(1);
                                d.dismiss();
                                finish();
                            }
                        },params);
                    }

                    return true;
                })
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        return super.onPrepareOptionsMenu(menu);
    }

    public int boolToInt(boolean myBoolean){
        return myBoolean ? 1 : 0;
    }
}
