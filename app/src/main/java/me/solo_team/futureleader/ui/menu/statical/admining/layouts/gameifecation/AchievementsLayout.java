package me.solo_team.futureleader.ui.menu.statical.admining.layouts.gameifecation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.Achievement;
import me.solo_team.futureleader.Objects.ChatMember;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.SelectMembers;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;
import me.solo_team.futureleader.ui.menu.statical.admining.layouts.surveys.Ideas;

public class AchievementsLayout extends Her {

    LinearLayout list;
    List<Achievement> achievements = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Достижения");
        setContentView(R.layout.only_linearlayout);
        list = findViewById(R.id.list);
        API.getAchievements(new ApiListener() {
                                Dialog d;

                                @Override
                                public void onError(JSONObject json) throws JSONException {
                                    d.dismiss();
                                    createNotification(list, json.getString("message"));
                                }

                                @Override
                                public void inProcess() {
                                    d = openWaiter(AchievementsLayout.this);
                                }

                                @Override
                                public void onSuccess(JSONObject json) throws JSONException {
                                    JSONArray ar = json.getJSONArray("achievement");
                                    for (int i = 0; i < ar.length(); i++) {
                                        Achievement achievement = new Achievement(ar.getJSONObject(i), false);
                                        achievements.add(achievement);
                                    }
                                    runOnUiThread(() -> render());
                                    d.dismiss();
                                }
                            },
                new CustomString("token", Constants.user.token),
                new CustomString("admin", String.valueOf(true))

        );
    }

    public void render() {
        list.removeAllViews();
        for (int i = 0; i < achievements.size(); i++) {
            View view = getLayoutInflater().inflate(R.layout.obj_achievement, null);
            Achievement achievement = achievements.get(i);

            Constants.cache.addPhoto(achievement.image_url, view.findViewById(R.id.obj_achievement_photo), this);
            ((TextView) view.findViewById(R.id.obj_achievement_name)).setText(achievement.name);
            ((TextView) view.findViewById(R.id.obj_achievement_description)).setText(achievement.description);
            if(achievement.limit!=-1)
                ((TextView) view.findViewById(R.id.obj_achievement_cost)).setText("FBL: "+achievement.coins + " лимит: " + achievement.limit);
            else
                ((TextView) view.findViewById(R.id.obj_achievement_cost)).setText("FBL: "+achievement.coins);

            view.setOnClickListener(v -> {
                AlertDialog.Builder obj = new AlertDialog.Builder(AchievementsLayout.this);
                obj.setTitle("выбор действия");
                obj.setIcon(R.drawable.resize_300x0);
                obj.setPositiveButton("удалить", (dialog, which) -> Cremove(achievement.id));
                obj.setNegativeButton("выдать участникам", (dialog, which) -> {
                    currentId = achievement.id;
                    System.out.println(currentId);

                    Cadd();
                });
                obj.show();
            });
            list.addView(view);
        }
    }

    public void Cremove(int id) {
        AlertDialog.Builder obj2 = new AlertDialog.Builder(AchievementsLayout.this);
        obj2.setTitle("Вы точно хотите удалить достижение?");
        obj2.setIcon(R.drawable.resize_300x0);
        obj2.setPositiveButton("да", (dialog2, which2) -> {
            AlertDialog.Builder obj = new AlertDialog.Builder(AchievementsLayout.this);
            obj.setTitle("Убрать FBL?");
            obj.setIcon(R.drawable.resize_300x0);
            obj.setPositiveButton("да", (dialog, which) -> Rremove(true, id));
            obj.setNegativeButton("нет", (dialog, which) -> Rremove(false, id));
            obj.setMessage("убрать FBL у тех, кто имеет это достижение?");
            obj.show();
        });
        obj2.setNegativeButton("нет", null);
        obj2.show();
    }

    public void Cadd() {
        if(!Constants.user.permission.can_add_achievement_user){
            Utils.ShowSnackBar.show(AchievementsLayout.this,"Отказано в доступе!",list);
            return;
        }
        Intent intent = new Intent(this, SelectMembers.class);
        intent.putExtra("needStuff", false);
        intent.putExtra("checker", true);
        intent.putExtra("removeSelf", false);
        intent.putExtra("showStatistic", false);
        intent.putExtra("selectOne", false);
        startActivityIfNeeded(intent, 101);
    }

    public void Rremove(boolean removeFBL, int id) {
        if(!Constants.user.permission.can_remove_achievement){
            Utils.ShowSnackBar.show(AchievementsLayout.this,"Отказано в доступе!",list);
            return;
        }
        API.removeAchievement(new ApiListener() {
                                  Dialog d;

                                  @Override
                                  public void onError(JSONObject json) throws JSONException {
                                      d.dismiss();
                                      createNotification(list, json.getString("message"));
                                  }

                                  @Override
                                  public void inProcess() {
                                      d = openWaiter(AchievementsLayout.this);
                                  }

                                  @Override
                                  public void onSuccess(JSONObject json) throws JSONException {
                                      runOnUiThread(() -> {
                                                  for (Achievement achievement : achievements) {
                                                      if (achievement.id == id) {
                                                          achievements.remove(achievement);
                                                          break;
                                                      }
                                                  }
                                                  render();
                                              }
                                      );
                                      d.dismiss();
                                      Utils.ShowSnackBar.show(AchievementsLayout.this, "готово!", list);
                                  }
                              },
                new CustomString("id", String.valueOf(id)),
                new CustomString("remove_coins", String.valueOf(removeFBL)),
                new CustomString("token",Constants.user.token)

        );
    }

    List<ChatMember> members = new ArrayList<>();

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "")
                .setIcon(R.drawable.plus)
                .setOnMenuItemClickListener(item -> {
                    Intent intent = new Intent(this, CereateAchievement.class);
                    startActivityIfNeeded(intent, 100);
                    return true;
                })
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return super.onPrepareOptionsMenu(menu);
    }

    int currentId;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 1)
            Utils.ShowSnackBar.show(AchievementsLayout.this, "готово!", list);
        if (requestCode == 101 && resultCode == 100) {
            for (String s : data.getStringArrayExtra("users")) {
                try {
                    members.add(new ChatMember(new JSONObject(s)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("CURRENT ID: "+currentId);
            Utils.ShowSnackBar.show(AchievementsLayout.this, "выбрано участников: " + members.size(), list);
            AlertDialog.Builder obj = new AlertDialog.Builder(AchievementsLayout.this);
            obj.setTitle("подтвердите добавление");
            obj.setIcon(R.drawable.resize_300x0);
            obj.setPositiveButton("подтвердить", (dialog, which) -> {
                        API.addUserAchievement(new ApiListener() {
                                                   Dialog d;

                                                   @Override
                                                   public void onError(JSONObject json) throws JSONException {
                                                       d.dismiss();
                                                       createNotification(list, json.getString("message"));
                                                   }

                                                   @Override
                                                   public void inProcess() {
                                                       d = openWaiter(AchievementsLayout.this);
                                                   }

                                                   @Override
                                                   public void onSuccess(JSONObject json) throws JSONException {
                                                       int error = json.getJSONArray("error").length();
                                                       members.clear();
                                                       d.dismiss();
                                                       if (error == 0)
                                                           Utils.ShowSnackBar.show(AchievementsLayout.this, "успешно!", list);
                                                       else
                                                           Utils.ShowSnackBar.show(AchievementsLayout.this, "превышен лимит данного достижения у " + error + " пользователей", list);
                                                       runOnUiThread(() -> currentId = -1);
                                                   }
                                               },
                                new CustomString("id", String.valueOf(currentId)),
                                new CustomString("user_ids", getUserIds()),
                                new CustomString("token",Constants.user.token)
                        );

                    }
            );
            obj.setNegativeButton("отменить", (dialog, which) -> {
                members.clear();
                Utils.ShowSnackBar.show(AchievementsLayout.this, "действие отменено", list);
            });
            obj.show();
        }
        if(resultCode==-500)
            Utils.ShowSnackBar.show(AchievementsLayout.this,"Отказано в доступе!",list);

    }

    private String getUserIds() {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < members.size(); i++) {
            if (i != members.size() - 1)
                text.append(members.get(i).userId).append(",");
            else
                text.append(members.get(i).userId);
        }

        return text.toString();
    }
}
