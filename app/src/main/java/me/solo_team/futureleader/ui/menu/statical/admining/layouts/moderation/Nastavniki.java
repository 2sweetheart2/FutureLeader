package me.solo_team.futureleader.ui.menu.statical.admining.layouts.moderation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.ChatMember;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.User;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.SelectMembers;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;
import me.solo_team.futureleader.ui.profile.view_prof.ViewProfile;

public class Nastavniki extends Her {
    ExpandableListView expandableListView;
    public HashMap<ChatMember, List<ChatMember>> mentorsWithUsers = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.adminig_layout);
        expandableListView = findViewById(R.id.expListView);

        setTitle("Наставники");
        if (!Constants.user.permission.can_add_mentors) {
            setResult(-500);
            finish();
        }
        refresh();
    }

    public void refresh() {
        API.getMentors(new ApiListener() {
            Dialog d;

            @Override
            public void onError(JSONObject json) throws JSONException {
                d.dismiss();
                createNotification(expandableListView, json.getString("message"));
            }

            @Override
            public void inProcess() {
                d = openWaiter(Nastavniki.this);
            }

            @Override
            public void onSuccess(JSONObject json) throws JSONException {
                JSONArray us = json.getJSONArray("mentors");
                mentorsWithUsers = new HashMap<>();
                for (int i = 0; i < us.length(); i++) {
                    JSONObject json2 = us.getJSONObject(i);
                    ChatMember mentor = new ChatMember(json2.getJSONObject("mentor"));
                    JSONArray users = json2.getJSONArray("users");
                    List<ChatMember> members = new ArrayList<>();
                    for (int l = 0; l < users.length(); l++) {
                        members.add(new ChatMember(users.getJSONObject(l)));
                    }
                    mentorsWithUsers.put(mentor, members);
                }
                runOnUiThread(() -> render());
                d.dismiss();
            }
        }, new CustomString("token", Constants.user.token));
    }

    public void createAdapter() {
        ArrayList<ArrayList<Map<String, String>>> сhildDataList = new ArrayList<>();

        ArrayList<Map<String, String>> groupDataList = new ArrayList<>();
        // заполняем коллекцию групп из массива с названиями групп


        List<ChatMember> groupData = new ArrayList<>();
        for (Map.Entry<ChatMember, List<ChatMember>> s : mentorsWithUsers.entrySet()) {
            сhildDataList.add(addCollection(s.getValue()));
            groupData.add(s.getKey());
        }

        for (ChatMember group : groupData) {
            Map<String, String> map = new HashMap<>();
            map.put("groupName", group.getFullName()); // время года
            groupDataList.add(map);
        }

        String[] childFrom = new String[]{"monthName"};
        int[] childTo = new int[]{android.R.id.text1};

        String[] groupFrom = new String[]{"groupName"};
        int[] groupTo = new int[]{android.R.id.text1};

        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                this, groupDataList,
                android.R.layout.simple_expandable_list_item_1, groupFrom,
                groupTo, сhildDataList, android.R.layout.simple_list_item_1,
                childFrom, childTo);

        expandableListView.setAdapter(adapter);
        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            ChatMember mentor = groupData.get(groupPosition);
            ChatMember user = mentorsWithUsers.get(mentor).get(childPosition);
            AlertDialog.Builder obj = new AlertDialog.Builder(Nastavniki.this);
            obj.setTitle("Выбор действия");
            obj.setIcon(R.drawable.resize_300x0);
            obj.setPositiveButton("просмотреть профиль", (dialog, which) ->
                    API.getUser(new ApiListener() {
                        Dialog d;

                        @Override
                        public void onError(JSONObject json) throws JSONException {
                            d.dismiss();
                            createNotification(v, json.getString("message"));
                        }

                        @Override
                        public void inProcess() {
                            d = openWaiter(Nastavniki.this);
                        }

                        @Override
                        public void onSuccess(JSONObject json) throws JSONException {
                            User user = new User(json.getJSONObject("user"));
                            d.dismiss();
                            runOnUiThread(() -> {
                                Constants.currentUser = user;
                                Intent intent = new Intent(Nastavniki.this, ViewProfile.class);
                                intent.putExtra("removeSelf", false);
                                startActivity(intent);
                            });
                        }
                    }, new CustomString("token", Constants.user.token), new CustomString("id", String.valueOf(user.userId))));
            obj.setNegativeButton("удалить", (dialog, which) ->
                    API.removeUserFromMentor(new ApiListener() {
                                                 Dialog d;

                                                 @Override
                                                 public void onError(JSONObject json) throws JSONException {
                                                     d.dismiss();
                                                     createNotification(expandableListView, json.getString("message"));
                                                 }

                                                 @Override
                                                 public void inProcess() {
                                                     d = openWaiter(Nastavniki.this);
                                                 }

                                                 @Override
                                                 public void onSuccess(JSONObject json) throws JSONException {
                                                     d.dismiss();
                                                     runOnUiThread(() -> refresh());
                                                     Utils.ShowSnackBar.show(Nastavniki.this, "Готово!", expandableListView);
                                                 }
                                             },
                            new CustomString("token", Constants.user.token),
                            new CustomString("user_id", user.userId),
                            new CustomString("mentor_id", mentor.userId)
                    ));
            obj.show();
            return true;
        });
    }

    private ArrayList<Map<String, String>> addCollection(List<ChatMember> arr) {
        ArrayList<Map<String, String>> сhildDataItemList = new ArrayList<>();
        for (ChatMember month : arr) {
            Map<String, String> map = new HashMap<>();
            map.put("monthName", month.getFullName());
            сhildDataItemList.add(map);
        }
        return сhildDataItemList;
    }

    public void render() {
        createAdapter();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "")
                .setIcon(R.drawable.plus)
                .setOnMenuItemClickListener(item -> {
                    Intent intent = new Intent(this, SelectMembers.class);
                    intent.putExtra("needStuff", true);
                    intent.putExtra("forChat", false);
                    intent.putExtra("selectOne", true);
                    intent.putExtra("title", "Выбор наставника");
                    startActivityIfNeeded(intent, 151);
                    return true;
                })
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return super.onPrepareOptionsMenu(menu);

    }

    int mentorId;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1 && requestCode == 150) {
            int id = data.getIntExtra("user_id", -1);
            System.out.println(id);

        }
        if (resultCode == 1 && requestCode == 151) {
            mentorId = data.getIntExtra("user_id", -1);
            Intent intent = new Intent(this, SelectMembers.class);
            intent.putExtra("needStuff", true);
            intent.putExtra("forChat", false);
            intent.putExtra("selectOne", true);
            intent.putExtra("title", "Выбор пользователя");

            startActivityIfNeeded(intent, 152);
        }
        if (resultCode == 1 && requestCode == 152) {
            int userId = data.getIntExtra("user_id", -1);

            if (userId == mentorId) {
                Utils.ShowSnackBar.show(Nastavniki.this, "наставник не может совпадать с пользователем!", expandableListView);
                return;
            }

            API.addMentor(new ApiListener() {
                              Dialog d;

                              @Override
                              public void onError(JSONObject json) throws JSONException {
                                  d.dismiss();
                                  createNotification(expandableListView, json.getString("message"));
                              }

                              @Override
                              public void inProcess() {
                                  d = openWaiter(Nastavniki.this);
                              }

                              @Override
                              public void onSuccess(JSONObject json) throws JSONException {
                                  d.dismiss();
                                  runOnUiThread(() -> refresh());
                                  Utils.ShowSnackBar.show(Nastavniki.this, "готово!", expandableListView);
                              }
                          },
                    new CustomString("token", Constants.user.token),
                    new CustomString("mentor_id", mentorId),
                    new CustomString("user_id", userId)

            );
        }
        if (resultCode == -500) {
            Utils.ShowSnackBar.show(Nastavniki.this, "отказано в доступе!", expandableListView);
        }
    }
}
