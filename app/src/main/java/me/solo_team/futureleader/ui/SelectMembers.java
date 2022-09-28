package me.solo_team.futureleader.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.ChatMember;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.Field;
import me.solo_team.futureleader.Objects.Surveys;
import me.solo_team.futureleader.Objects.User;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.dialogs.SelectFilter;
import me.solo_team.futureleader.ui.menu.horizontal_menu.surveys.DoSurvey;
import me.solo_team.futureleader.ui.profile.view_prof.ViewProfile;

public class SelectMembers extends AppCompatActivity {

    LinearLayout usersList;
    EditText searchUser;
    TextView noResult;
    int offset = 0;
    int oldOffset;
    int curentSize = 0;
    TextView count;
    LinearLayout menu;
    JSONArray arr;
    JSONArray filteredList;

    boolean needStuff = true;
    boolean checker = false;
    boolean removeSelf = false;

    boolean selectOne = false;
    boolean forChat = false;
    boolean showStat = false;
    int filter_by = 0;
    HashMap<ChatMember,Surveys> surveys = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_members);
        forChat = getIntent().getBooleanExtra("forChat", false);

        if (!forChat && !Constants.user.permission.can_get_users) {
            setResult(-500);
            finish();
        }

        usersList = findViewById(R.id.layout_with_users);
        searchUser = findViewById(R.id.adminig_users_serach);
        noResult = findViewById(R.id.layout_with_users_no_result);
        menu = findViewById(R.id.layout_with_users_result);
        count = findViewById(R.id.layout_with_users_offset);
        TextView back = findViewById(R.id.layout_with_users_btn_back);
        TextView next = findViewById(R.id.layout_with_users_btn_next);

        needStuff = getIntent().getBooleanExtra("needStuff", true);
        checker = getIntent().getBooleanExtra("checker", false);
        removeSelf = getIntent().getBooleanExtra("removeSelf", false);
        showStat = getIntent().getBooleanExtra("showStatistic", false);
        selectOne = getIntent().getBooleanExtra("selectOne", false);
        if(needStuff){
            findViewById(R.id.admining_users_search_filter).setVisibility(View.VISIBLE);
            findViewById(R.id.admining_users_search_filter).setOnClickListener(v -> {
                SelectFilter selectFilter = new SelectFilter(filter_by);
                selectFilter.onChange = type -> {
                    filter_by = type;
                    switch (type){
                        case 0:
                            searchUser.setHint("поиск по имени...");
                            break;
                        case 1:
                            searchUser.setHint("поиск по должности...");
                            break;
                        case 2:
                            searchUser.setHint("поиск по подразделению...");
                            break;
                    }
                };
                selectFilter.show(getSupportFragmentManager(),null);
            });
        }

        if (getIntent().hasExtra("title"))
            setTitle(getIntent().getStringExtra("title"));
        if (showStat && !getIntent().hasExtra("users")) {
            API.getSurveysAdminStaticstic(new ApiListener() {
                Dialog d;

                @Override
                public void onError(JSONObject json) throws JSONException {
                    System.out.println(json);
                    d.dismiss();
                }

                @Override
                public void inProcess() {
                    d = openWaiter(SelectMembers.this);
                }

                @Override
                public void onSuccess(JSONObject json) throws JSONException {
                    JSONArray sur = json.getJSONArray("survey");
                    JSONArray arr_ = new JSONArray();
                    surveys = new HashMap<>();
                    for (int i = 0; i < sur.length(); i++) {
                        JSONObject o = sur.getJSONObject(i);
                        ChatMember chatMember = new ChatMember(o.getJSONObject("user"));
                        Surveys surveys_ = new Surveys(o);
                        surveys.put(chatMember, surveys_);
                        arr_.put(new JSONObject(chatMember.toChatMemder()));
                    }
                    arr = arr_;
                    addUsers(null);
                    d.dismiss();
                    searchUser.addTextChangedListener(new TextWatcher() {
                        @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                try {
                                    if (s.toString().length() == 0) offset = oldOffset;
                                    else {
                                        oldOffset = offset;
                                        offset = 0;
                                    }
                                    addUsers(s.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    });
                }
            }, new CustomString("token", Constants.user.token), new CustomString("id", getIntent().getStringExtra("id")));
        } else if (!getIntent().hasExtra("users"))
            API.getUsers(new ApiListener() {
                             Dialog d;

                             @Override
                             public void onError(JSONObject json) {
                                 try {
                                     createNotification(findViewById(R.id.admining_users_layout), json.getString("message"));
                                 } catch (JSONException e) {
                                     e.printStackTrace();
                                 }
                             }

                             @Override
                             public void inProcess() {
                                 d = openWaiter(SelectMembers.this);
                             }

                             @Override
                             public void onSuccess(JSONObject json) {
                                 try {
                                     arr = json.getJSONArray("users");

                                     addUsers(null);
                                     d.dismiss();
                                     searchUser.addTextChangedListener(new TextWatcher() {
                                         @Override
                                         public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                         }

                                         @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                try {
                                    if (s.toString().length() == 0) offset = oldOffset;
                                    else {
                                        oldOffset = offset;
                                        offset = 0;
                                    }
                                    addUsers(s.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                         }

                                         @Override
                                         public void afterTextChanged(Editable s) {
                                         }
                                     });
                                 } catch (JSONException e) {
                                     e.printStackTrace();
                                 }

                             }
                         }, new CustomString("token", Constants.user.token),
                    new CustomString("for_chat", String.valueOf(forChat)));
        if (getIntent().hasExtra("users")) {
            try {
                arr = new JSONArray(getIntent().getStringExtra("users"));
                addUsers(null);
                searchUser.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        try {
                            if (s.toString().length() == 0) offset = oldOffset;
                            else {
                                oldOffset = offset;
                                offset = 0;
                            }
                            addUsers(s.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        back.setOnClickListener(v -> {
            if (offset == 0) Snackbar.make(v, "Это начало списка", Snackbar.LENGTH_LONG)
                    .show();
            else {
                offset--;
                try {
                    String text = null;
                    if (searchUser.getText().toString().length() != 0)
                        text = searchUser.getText().toString();
                    addUsers(text);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        next.setOnClickListener(v -> {
            if (offset == curentSize / 10)
                Snackbar.make(v, "Это конец списка", Snackbar.LENGTH_LONG)
                        .show();
            else {
                offset++;
                try {
                    String text = null;
                    if (searchUser.getText().toString().length() != 0)
                        text = searchUser.getText().toString();
                    addUsers(text);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void copy() {
        filteredList = arr;
        curentSize = filteredList.length();
    }

    private void filterArray(String filterName) throws JSONException {
        if (filterName == null) {
            copy();
            return;
        }
        if (filterName.equals("")) {
            copy();
            return;
        }
        filteredList = new JSONArray();
        if (filter_by == 0) {
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);
                System.out.println(o);

                if (!(o.getString("first_name") + " " + o.getString("last_name")).toLowerCase().contains(filterName.toLowerCase()))
                    continue;
                if (removeSelf && o.getInt("id") == Constants.user.id)
                    continue;
                filteredList.put(o);
            }
        }

        curentSize = filteredList.length();
    }

    List<User> checkedUsers = new ArrayList<>();
    List<ChatMember> checkedMembers = new ArrayList<>();


    private void addCkeduser(User user) {
        for (User u : checkedUsers) {
            if (u.id == user.id)
                break;
        }
        checkedUsers.add(user);
    }

    private void removeUser(User user) {
        for (User u : checkedUsers) {
            if (u.id == user.id) {
                checkedUsers.remove(u);
                break;
            }
        }
    }


    private boolean containsUser(User user) {
        boolean con = false;
        for(User u : checkedUsers)
            if(u.id==user.id){
                con=true;
                break;
            }
        return con;
    }

    private void addUsers(String filterName) throws JSONException {
        runOnUiThread(() -> usersList.removeAllViews());
        int count = 0;
        int lastIndex = 0;
        filterArray(filterName);
        int curSize = (10 * offset) + 10;
        if(arr.length()<10)
            curSize = arr.length();
        for (int i = (10 * offset); i < curSize; i++) {
            if (i >= curentSize) continue;
            JSONObject o = filteredList.getJSONObject(i);
            if(o.getInt("id")==Constants.user.id && !showStat && removeSelf)
                continue;
            lastIndex = i + 1;
            System.out.println(o);

            if(showStat){
                ChatMember member = new ChatMember(o);
                ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.admining_user_content_layout, null);
                ((TextView) constraintLayout.findViewById(R.id.admining_user_content_name)).setText(member.firstName + " " + member.lastName);
                ((TextView) constraintLayout.findViewById(R.id.admining_user_content_email)).setVisibility(View.GONE);

                constraintLayout.findViewById(R.id.admining_user_content_huina).setVisibility(View.GONE);
                constraintLayout.findViewById(R.id.admining_user_content_huina).setVisibility(View.GONE);
                Constants.cache.addPhoto(o.getString("profile_picture"), constraintLayout.findViewById(R.id.admining_user_content_logo), this);
                runOnUiThread(() -> usersList.addView(constraintLayout));
                if(getIntent().getBooleanExtra("needShowSurveyStat",false))
                    constraintLayout.setOnClickListener(v -> {
                        Intent intent = new Intent(this, DoSurvey.class);
                        intent.putExtra("type", "check");
                        intent.putExtra("custom_check", true);
                        for (Map.Entry<ChatMember, Surveys> surveys : surveys.entrySet()) {
                            if (surveys.getKey().userId == member.userId) {
                                Constants.surveysCache.currentSurvey = surveys.getValue();
                                break;
                            }
                        }
                        startActivity(intent);
                    });
            }
            else {

                User user = new User(o);

                user.enums = Constants.user.enums;
                String division = "Отсутствует";
                String post = "Отсутствует";

                for (Field field : user.fields) {
                    if (field.name.equals("division"))
                        division = field.value;
                    if (field.name.equals("post"))
                        post = field.value;
                }

                ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.admining_user_content_layout, null);
                ((TextView) constraintLayout.findViewById(R.id.admining_user_content_name)).setText(user.firstName + " " + user.lastName);
                if(o.has("email"))
                    ((TextView) constraintLayout.findViewById(R.id.admining_user_content_email)).setText("email: " + o.getString("email"));

                else
                    ((TextView) constraintLayout.findViewById(R.id.admining_user_content_email)).setText("");

                TextView div = constraintLayout.findViewById(R.id.admining_user_content_division);
                TextView pot = constraintLayout.findViewById(R.id.admining_user_content_post);
                CheckBox check = constraintLayout.findViewById(R.id.admining_user_content_check);
                div.setText(division);
                pot.setText(post);

                if (checker) {
                    constraintLayout.findViewById(R.id.admining_user_content_huina).setVisibility(View.GONE);
                    check.setVisibility(View.VISIBLE);
                    check.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        if (isChecked) {
                            addCkeduser(user);
                        } else
                            removeUser(user);
                    });
                    if (containsUser(user) && !check.isChecked())
                        check.setChecked(true);
                }
                if (!needStuff) {
                    constraintLayout.findViewById(R.id.admining_user_content_huina).setVisibility(View.GONE);
                }
                Constants.cache.addPhoto(o.getString("profile_picture"), constraintLayout.findViewById(R.id.admining_user_content_logo), this);
                runOnUiThread(() -> usersList.addView(constraintLayout));
                if (!checker) {
                    if (!showStat && !selectOne)
                        constraintLayout.setOnClickListener(v -> {
                            Intent intent = new Intent(this, ViewProfile.class);
                            Constants.currentUser = user;
                            startActivity(intent);
                        });
                    if (selectOne) {
                        if(getIntent().getBooleanExtra("needUserObject",false))
                            constraintLayout.setOnClickListener(v -> {
                                Intent data = new Intent();
                                data.putExtra("user",user.toChatMemder());
                                setResult(1,data);
                                finish();
                            });
                        else {
                            constraintLayout.setOnClickListener(v -> {
                                Intent data = new Intent();
                                data.putExtra("user_id", user.id);
                                setResult(1, data);
                                finish();
                            });
                        }
                    }
                }
            }
            count++;

        }
        int finalCount = count;
        int finalLastIndex = lastIndex;
        runOnUiThread(() -> {
            if (finalCount == 0) {
                menu.setVisibility(View.GONE);
                noResult.setText("По вашему запросу \"" + filterName + "\" нет результатов :(");
                noResult.setVisibility(View.VISIBLE);
            } else {
                noResult.setVisibility(View.GONE);
                menu.setVisibility(View.VISIBLE);
                String text_;
                if (offset == 0) text_ = "1";
                else text_ = String.valueOf((10 * offset));
                this.count.setText("Показаны пользователи с " + text_ + " по " + finalLastIndex + " (из " + (curentSize) + " )");
            }
        });
    }


    @SuppressLint("ResourceType")
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(checker)
            menu.add(0, 1, 0, "")
                .setIcon(R.drawable.done)
                .setOnMenuItemClickListener(item -> {
                    Intent data = new Intent();
                    List<String> usersInfo = new ArrayList<>();
                    for (User user : checkedUsers) {
                        usersInfo.add(user.toChatMemder());
                    }
                    String[] stockArr = new String[usersInfo.size()];

                    usersInfo.toArray(stockArr);

                    data.putExtra("users",stockArr);
                    setResult(100,data);
                    finish();
                    return true;
                })
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(showStat)
            setResult(1);
    }
}
