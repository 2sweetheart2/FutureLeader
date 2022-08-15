package me.solo_team.futureleader.ui.menu.statical.admining.layouts.moderation;

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
import me.solo_team.futureleader.Objects.CDateTime;
import me.solo_team.futureleader.Objects.ChatMember;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.ShopItem;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class GetShopRequests extends Her {
    List<AdminShopRequest> requests = new ArrayList<>();
    LinearLayout list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("заявки магизне");
        setContentView(R.layout.only_linearlayout);
        list = findViewById(R.id.list);
        API.getShopRequests(new ApiListener() {
            Dialog d;

            @Override
            public void onError(JSONObject json) throws JSONException {
                d.dismiss();
                setResult(-1);
                finish();
            }

            @Override
            public void inProcess() {
                d = openWaiter(GetShopRequests.this);
            }

            @Override
            public void onSuccess(JSONObject json) throws JSONException {
                JSONArray ar = json.getJSONArray("shop_request");
                for (int i = 0; i < ar.length(); i++) {
                    AdminShopRequest adminShopRequest = new AdminShopRequest(ar.getJSONObject(i));
                    requests.add(adminShopRequest);
                }
                runOnUiThread(() -> {
                    render();
                });
                d.dismiss();

            }
        }, new CustomString("token", Constants.user.token));
    }

    public void render() {
        list.removeAllViews();
        for (int i = 0; i < requests.size(); i++) {
            AdminShopRequest adminShopRequest = requests.get(i);
            View view = getLayoutInflater().inflate(R.layout.obj_shop, null);
            view.findViewById(R.id.obj_shop_added).setVisibility(View.GONE);
            Constants.cache.addPhoto(adminShopRequest.item.photo, "GSR" + i, view.findViewById(R.id.obj_shop_photo), this);
            ((TextView) view.findViewById(R.id.obj_shop_name)).setText(adminShopRequest.item.name);
            ((TextView) view.findViewById(R.id.obj_shop_cost)).setText("");
            ((TextView) view.findViewById(R.id.obj_shop_count)).setText("  остаток: " + adminShopRequest.item.count);
            ((TextView) view.findViewById(R.id.obj_shop_description)).setText(adminShopRequest.chatMember.getFullName()+"\n"+adminShopRequest.dateTime.toString(true));
            view.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(GetShopRequests.this);
                builder.setTitle("подтвердить заказ?");  // заголовок
                builder.setMessage("после подтверждения заказа, покупателю прийдёт уведомление"); // сообщение
                builder.setPositiveButton("подтвердить", (dialog, id) -> req(true, adminShopRequest.item.id, adminShopRequest.chatMember.userId));
                builder.setNegativeButton("отменить заказ", (dialog, id) -> req(false, adminShopRequest.item.id, adminShopRequest.chatMember.userId));
                builder.setCancelable(true);
                builder.show();
            });
            list.addView(view);
        }
    }

    public void req(boolean stat, int sell_id, int userId) {
        API.setShopRequests(new ApiListener() {
                                Dialog d;

                                @Override
                                public void onError(JSONObject json) throws JSONException {
                                    d.dismiss();
                                    createNotification(list, json.getString("message"));
                                }

                                @Override
                                public void inProcess() {
                                    d = openWaiter(GetShopRequests.this);
                                }

                                @Override
                                public void onSuccess(JSONObject json) throws JSONException {
                                    runOnUiThread(() -> {
                                        for (AdminShopRequest adminShopRequest : requests) {
                                            if (adminShopRequest.item.id == sell_id && adminShopRequest.chatMember.userId == userId) {
                                                requests.remove(adminShopRequest);
                                                runOnUiThread(() -> render());
                                            }
                                        }
                                    });
                                    Utils.ShowSnackBar.show(GetShopRequests.this, "готово!", list);
                                    d.dismiss();
                                }
                            },
                new CustomString("sell_id", String.valueOf(sell_id)),
                new CustomString("user_id", String.valueOf(userId)),
                new CustomString("status", String.valueOf(stat)),
                new CustomString("token", Constants.user.token)

        );
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "")
                .setIcon(R.drawable.menu_white)
                .setOnMenuItemClickListener(item -> {
                    startActivity(new Intent(GetShopRequests.this, GetOldShopRequest.class));
                    return true;
                })
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return super.onPrepareOptionsMenu(menu);
    }

    static class AdminShopRequest {
        public ShopItem item;
        public ChatMember chatMember;
        public CDateTime dateTime;
        public boolean status = false;

        public AdminShopRequest(JSONObject payload) {
            try {
                item = new ShopItem(payload);
                chatMember = new ChatMember(payload);
                dateTime = new CDateTime(payload.getString("datetime"), true);
                if(payload.has("status"))
                    status = payload.getInt("status") != 0;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
