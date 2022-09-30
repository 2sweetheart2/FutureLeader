package me.solo_team.futureleader.ui.profile.view_prof;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.Achievement;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.Field;
import me.solo_team.futureleader.Objects.User;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.dialogs.EditFieldsDialog;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;
import me.solo_team.futureleader.ui.profile.AddFieldLayout;
import me.solo_team.futureleader.ui.profile.EditFieldsLayout;
import me.solo_team.futureleader.ui.profile.ProfileInfoGrid;
import me.solo_team.futureleader.ui.profile.RecycleAdapter2;

public class ViewProfile extends Her {

    private ImageView picture;
    private TextView name;
    private TextView description;
    private TextView mentors;
    TableLayout tableLayout;
    ProfileInfoGrid grid;
    LinkedHashMap<String, String> notAddedItems = new LinkedHashMap<>();
    Button button;
    Button openChat;
    View root;
    User currentUser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);
        root = findViewById(R.id.profile_main);
        tableLayout = root.findViewById(R.id.profile_table_layout);
        grid = new ProfileInfoGrid(tableLayout, root.getContext(), getLayoutInflater());
        picture = root.findViewById(R.id.profile_picture);
        name = root.findViewById(R.id.profile_name);
        description = root.findViewById(R.id.profile_description);
        mentors = root.findViewById(R.id.profile_mentors);
        button = root.findViewById(R.id.profile_add_field_btn);
        openChat = root.findViewById(R.id.profile_open_chat);
        String s = "SELECT * FROM table WHERE id=0";

        if(currentUser==null)
            currentUser = Constants.currentUser;
        if(currentUser.id==Constants.user.id)
            openChat.setVisibility(View.GONE);
        else
            openChat.setOnClickListener(view -> {
                //TODO: сделать метод на сере о проверке созданного чата, и если есть, то открывать, иначе создать и открыть. (сделать удаление истории чата у конкретного пользователя, если же ему или он написал в этот чат, то показывать его, инае не показывать)
            });

        boolean removeSelf = true;
        if(getIntent().hasExtra("removeSelf"))
            removeSelf = getIntent().getBooleanExtra("removeSelf",true);
        if(Constants.currentUser.id==Constants.user.id && removeSelf)
            finish();
        Conresume();
    }

    public void Conresume(){
        Button achivement_btn = root.findViewById(R.id.achiviment_btn);

        name.setText(Constants.currentUser.firstName + " " + Constants.currentUser.lastName);
        description.setText(Constants.currentUser.status);
        Constants.cache.addPhoto(Constants.currentUser.profilePictureLink, picture, this);
        if(Constants.currentUser.mentors.size()!=0){
            if(Constants.currentUser.mentors.size()>1) {
                mentors.setText("Наставники: " + Constants.currentUser.mentors.get(0).getFullName() + "...");
            }
            else{
                mentors.setText("Наставник: " + Constants.currentUser.mentors.get(0).getFullName());
                mentors.setOnClickListener(v -> {
                    API.getUser(new ApiListener() {
                        Dialog d;

                        @Override
                        public void onError(JSONObject json) throws JSONException {
                            d.dismiss();
                            createNotification(v, json.getString("message"));
                        }

                        @Override
                        public void inProcess() {
                            d = openWaiter(ViewProfile.this);
                        }

                        @Override
                        public void onSuccess(JSONObject json) throws JSONException {
                            User user = new User(json.getJSONObject("user"));
                            d.dismiss();
                            runOnUiThread(() -> {
                                Constants.currentUser = user;
                                Intent intent = new Intent(ViewProfile.this, ViewProfile.class);
                                intent.putExtra("removeSelf", false);
                                startActivity(intent);
                            });
                        }
                    }, new CustomString("token", Constants.user.token), new CustomString("id", String.valueOf(Constants.currentUser.mentors.get(0).userId)));
                });
            }
        }else mentors.setVisibility(View.GONE);
        picture.requestLayout();
        updateGrid(grid);
        if(Constants.user.permission.can_edit_profile) {
            picture.setOnClickListener(v -> Utils.ShowSnackBar.show(ViewProfile.this,"Только владелец может менять себе авотарку!",root));
            if (notAddedItems.size() == 0)
                button.setVisibility(View.GONE);
            button.setOnClickListener(v -> {
                Intent intent = new Intent(ViewProfile.this, AddFieldLayout.class);
                startActivity(intent);
            });
        }else button.setVisibility(View.GONE);



        achivement_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                List<Integer> ids = new ArrayList<>();
                if (Constants.currentUser.achievementsIds != null) {
                    for (String s : Constants.currentUser.achievementsIds.split(",")) {
                        if (!s.equals("null"))
                            ids.add(Integer.parseInt(s));
                    }
                }
                API.getAchievements(new ApiListener() {
                    Dialog d;

                    @Override
                    public void onError(JSONObject json) {
                        try {
                            this.createNotification(root, json.getString("message"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void inProcess() {
                        d = this.openWaiter(ViewProfile.this);
                    }

                    @Override
                    public void onSuccess(JSONObject json) {
                        try {
                            d.dismiss();
                            JSONArray ar = json.getJSONArray("achievement");
                            Constants.currentUser.achievements.clear();
                            for (int i = 0; i < ar.length(); i++) {
                                Constants.currentUser.achievements.add(new Achievement(ar.getJSONObject(i), false));
                            }
                            runOnUiThread(() -> {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ViewProfile.this);
                                View view = getLayoutInflater().inflate(R.layout.profile_alert_dialog_list, null);
                                builder.setView(view);
                                RecyclerView recyclerView = view.findViewById(R.id.achivement_list);

                                recyclerView.setLayoutManager(new LinearLayoutManager(ViewProfile.this));
                                System.out.println("ACHIEVEMTNS SIZE: " + Constants.currentUser.achievements.size());
                                RecycleAdapter2 adapter = new RecycleAdapter2(getSupportFragmentManager(), Constants.currentUser.achievements, ViewProfile.this);
                                recyclerView.setAdapter(adapter);
                                builder.show();
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new CustomString("user_id", String.valueOf(Constants.currentUser.id)), new CustomString("token", Constants.user.token));


            }
        });
    }

    private void updateGrid(ProfileInfoGrid grid) {
        tableLayout.removeAllViews();
        notAddedItems.clear();
        List<Field> fields = Constants.currentUser.fields;
        for (Field field : fields) {
            if (field.name.equals("line")) {
                if (field.visualName.length() == 0)
                    grid.addRow();
                else grid.addRow(field.visualName);
                continue;
            }
            String name = field.visualName.substring(0, 1).toUpperCase() + field.visualName.substring(1);
            if (field.name.equals("birthday"))
                grid.addElement(name, Utils.parseDateBirthday(field.value));
            else {
                View v = grid.addElement(name, field.value);
                if(field.canEdit && Constants.user.permission.can_edit_profile)
                    v.setOnLongClickListener(v_ -> cr(field.visualName, field.value));
            }
        }
        if(Constants.currentUser.fieldsStuff.maxSize!=fields.size() && Constants.user.permission.can_edit_profile){
            button.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Constants.currentUser = currentUser;
        Conresume();
        updateGrid(grid);
    }

    private boolean cr(String value, String type) {
        EditFieldsDialog cl = new EditFieldsDialog(result -> {
            if (!result) return;
            Intent intent = new Intent(ViewProfile.this, EditFieldsLayout.class);
            intent.putExtra("type", type);
            intent.putExtra("name", value);
            intent.putExtra("forUser",Constants.currentUser.id);
            startActivity(intent);
        }, value, null);
        cl.show(getSupportFragmentManager(), "myDialog");
        return true;
    }


}
