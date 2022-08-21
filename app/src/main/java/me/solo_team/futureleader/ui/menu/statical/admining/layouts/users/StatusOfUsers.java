package me.solo_team.futureleader.ui.menu.statical.admining.layouts.users;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.ChatMember;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.Permission;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.SelectMembers;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;
import me.solo_team.futureleader.ui.menu.statical.admining.layouts.gameifecation.ChangeRole;
import me.solo_team.futureleader.ui.news.open_news.EditNews;

public class StatusOfUsers extends Her {
    LinearLayout listView;
    List<Permission> permissionList = new ArrayList<>();
    boolean selectOne = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Constants.user.permission.can_get_admin_roles) {
            setResult(-500);
            finish();
        }
        selectOne = getIntent().getBooleanExtra("selectOne",false);
        setTitle("Статусы пользователей");
        setContentView(R.layout.only_linearlayout);
        listView = findViewById(R.id.list);
        API.getUsersRole(new ApiListener() {
                             Dialog d;

                             @Override
                             public void onError(JSONObject json) throws JSONException {
                                 d.dismiss();
                                 createNotification(listView, json.getString("message"));
                             }

                             @Override
                             public void inProcess() {
                                 d = openWaiter(StatusOfUsers.this);
                             }

                             @Override
                             public void onSuccess(JSONObject json) throws JSONException {
                                 JSONArray ar = json.getJSONArray("roles");
                                 for (int i = 0; i < ar.length(); i++) {
                                     permissionList.add(new Permission(ar.getJSONObject(i)));
                                 }
                                 runOnUiThread(() -> render());
                                 d.dismiss();
                             }
                         }, new CustomString("token", Constants.user.token)
        );
    }

    public void render() {
        listView.removeAllViews();
        for (Permission perm : permissionList) {
            View v = getLayoutInflater().inflate(R.layout.obj_perm, null);
            ((TextView) v.findViewById(R.id.obj_perm_name)).setText(perm.name);
            ((TextView) v.findViewById(R.id.obj_perm_uid)).setText("UID: " + perm.id);
            v.setOnClickListener(v1 -> {
                if(selectOne){
                    Intent data = new Intent();
                    data.putExtra("id",perm.id);
                    setResult(1,data);
                    finish();
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(StatusOfUsers.this);
                builder.setTitle("выбор действия");  // заголовок
                builder.setPositiveButton("изменить", (dialog, id) -> {
                    if(!Constants.user.permission.can_update_role){
                        Utils.ShowSnackBar.show(StatusOfUsers.this, "отказано в доступе!", listView);
                        return;
                    }
                    Intent intent = new Intent(StatusOfUsers.this, ChangeRole.class);
                    intent.putExtra("perm", perm);
                    startActivityIfNeeded(intent, 100);
                });
                builder.setNegativeButton("удалить", (dialog, id) -> {
                    if (!Constants.user.permission.can_remove_role) {
                        Utils.ShowSnackBar.show(StatusOfUsers.this, "отказано в доступе!", listView);
                        return;
                    }
                    API.removeRole(new ApiListener() {
                                       Dialog d;

                                       @Override
                                       public void onError(JSONObject json) throws JSONException {
                                           d.dismiss();
                                           createNotification(listView, json.getString("message"));
                                       }

                                       @Override
                                       public void inProcess() {
                                           d = openWaiter(StatusOfUsers.this);
                                       }

                                       @Override
                                       public void onSuccess(JSONObject json) throws JSONException {
                                           permissionList.remove(perm);
                                           runOnUiThread(() -> render());
                                           d.dismiss();
                                       }
                                   },
                            new CustomString("id", String.valueOf(perm.id)),
                            new CustomString("token", Constants.user.token)
                    );
                });
                builder.setNeutralButton("назначить/кто имеет", (dialog, which) -> {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(StatusOfUsers.this);
                    builder1.setTitle("выбор действия");  // заголовок
                    builder1.setPositiveButton("назначить", (dialog1, which1) -> {
                        if (!Constants.user.permission.can_chage_admin_role) {
                            Utils.ShowSnackBar.show(StatusOfUsers.this, "отказано в доступе!", listView);
                            return;
                        }
                        currentPermisison = perm;
                        Intent intent = new Intent(this, SelectMembers.class);
                        intent.putExtra("needStuff", false);
                        intent.putExtra("checker", true);
                        intent.putExtra("removeSelf", true);
                        intent.putExtra("forChat", false);
                        startActivityIfNeeded(intent, 150);
                    });
                    builder1.setNegativeButton("кто имеет", (dialog1, which1) -> {
                        if (!Constants.user.permission.can_get_who_has_this_role) {
                            Utils.ShowSnackBar.show(StatusOfUsers.this, "отказано в доступе!", listView);
                            return;
                        }
                        API.getWhoHasThisRole(new ApiListener() {
                                                  Dialog d;

                                                  @Override
                                                  public void onError(JSONObject json) throws JSONException {
                                                      d.dismiss();
                                                      createNotification(listView, json.getString("message"));
                                                  }

                                                  @Override
                                                  public void inProcess() {
                                                      d = openWaiter(StatusOfUsers.this);
                                                  }

                                                  @Override
                                                  public void onSuccess(JSONObject json) throws JSONException {
                                                      d.dismiss();
                                                      runOnUiThread(() -> {
                                                          Intent intent = new Intent(StatusOfUsers.this, SelectMembers.class);
                                                          intent.putExtra("showStatistic", false);
                                                          intent.putExtra("needStuff", false);
                                                          try {
                                                              intent.putExtra("users", String.valueOf(json.getJSONArray("users")));
                                                          } catch (JSONException e) {
                                                              e.printStackTrace();
                                                          }
                                                          startActivityIfNeeded(intent, 101);
                                                      });
                                                  }
                                              }, new CustomString("token", Constants.user.token),new CustomString("id",String.valueOf(perm.id))
                        );
                    });
                    builder1.show();
                });
                builder.show();
            });
            listView.addView(v);
        }
    }

    Permission currentPermisison;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==2)
            Utils.ShowSnackBar.show(StatusOfUsers.this,"роль успешно обнавлена!",listView);
        if(resultCode==1)
            Utils.ShowSnackBar.show(StatusOfUsers.this,"роль успешно создана!",listView);
        if(resultCode==100 && requestCode==150){
            List<ChatMember> members = new ArrayList<>();
            for(String s : data.getStringArrayExtra("users")){
                try {
                    members.add(new ChatMember(new JSONObject(s)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            API.setUserRole(new ApiListener() {
                Dialog d;
                @Override
                public void onError(JSONObject json) throws JSONException {
                    d.dismiss();
                    createNotification(listView,json.getString("message"));
                }

                @Override
                public void inProcess() {
                    d = openWaiter(StatusOfUsers.this);
                }

                @Override
                public void onSuccess(JSONObject json) throws JSONException {
                    d.dismiss();
                    Utils.ShowSnackBar.show(StatusOfUsers.this,"успешно назначено!",listView);
                    runOnUiThread(()->currentPermisison=null);
                }
            },new CustomString("token",Constants.user.token),new CustomString("role_id",String.valueOf(currentPermisison.id)),new CustomString("user_ids",getUserIds(members)));
        }
        if(resultCode==-500){
            Utils.ShowSnackBar.show(StatusOfUsers.this,"отказано в доступе!",listView);
        }
    }

    private String getUserIds(List<ChatMember> members) {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < members.size(); i++) {
            if (i != members.size() - 1)
                text.append(members.get(i).userId).append(",");
            else
                text.append(members.get(i).userId);
        }

        return text.toString();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "")
                .setIcon(R.drawable.plus)
                .setOnMenuItemClickListener(item -> {
                    Intent intent = new Intent(this, ChangeRole.class);
                    startActivity(intent);
                    return true;
                })
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        return super.onPrepareOptionsMenu(menu);
    }
}
