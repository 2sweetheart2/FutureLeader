package me.solo_team.futureleader.ui.menu.statical.shop;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.ShopItem;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class ShopView extends Her {

    LinearLayout list;
    TextView info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);

        setTitle("Корпоративный магазин");

        list = findViewById(R.id.shop_list);
        info = findViewById(R.id.shop_your_info);

        info.setText("ваш баланс: " + Constants.user.currency + " FBL");
        currency = Constants.user.currency;
        get();
    }

    private void get() {
        API.getShop(new ApiListener() {
            Dialog d;

            @Override
            public void onError(JSONObject json) throws JSONException {
                d.dismiss();
            }

            @Override
            public void inProcess() {
                d = openWaiter(ShopView.this);
            }

            @Override
            public void onSuccess(JSONObject json) throws JSONException {
                JSONArray shop = json.getJSONArray("shop");
                List<ShopItem> items = new ArrayList<>();
                for (int i = 0; i < shop.length(); i++) {
                    items.add(new ShopItem(shop.getJSONObject(i)));
                }
                runOnUiThread(() -> render(items));
                d.dismiss();
            }


        }, new CustomString("token", Constants.user.token));
    }

    int currency;

    private void render(List<ShopItem> items) {
        list.removeAllViews();
        for (ShopItem item : items) {
            View view = getLayoutInflater().inflate(R.layout.obj_shop, null);
            ((TextView) view.findViewById(R.id.obj_shop_name)).setText(item.name);
            ((TextView) view.findViewById(R.id.obj_shop_cost)).setText("Стоимость: " + item.cost + " FBL");
            ((TextView) view.findViewById(R.id.obj_shop_count)).setText("Осталось: " + item.count);
            ((TextView) view.findViewById(R.id.obj_shop_description)).setText(item.description);
            Constants.cache.addPhoto(item.photo, true, view.findViewById(R.id.obj_shop_photo), this);
            ImageView plus = view.findViewById(R.id.obj_shop_added);
            plus.setVisibility(View.GONE);
            if (item.count > 0)
                view.setOnClickListener(v -> {
                    if (Constants.shopChache.corzina.contains(item)) {
                        view.setBackground(null);
                        Constants.shopChache.corzina.remove(item);
                        currency = currency + item.cost;

                    } else {
                        currency = currency - item.cost;
                        view.setBackground(getDrawable(R.drawable.gray_gradient_with_corners));
                        Constants.shopChache.corzina.add(item);
                    }
                    updateCorzina();
                });
            list.addView(view);
        }
    }

    private void updateCorzina() {
        info.clearComposingText();
        info.setText("ваш баланс: " + Constants.user.currency + " FBL (Остаток: "+currency+" FBL)");
        if (currency < 0)
            info.setTextColor(Color.RED);
        else
            info.setTextColor(Color.BLACK);
        invalidateOptionsMenu();
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menu.size() == 0)
            menu.add(0, 1, 0, "")
                    .setIcon(R.drawable.backet)
                    .setOnMenuItemClickListener(item -> {
                            Intent intent = new Intent(this, CorzinaView.class);
                            startActivity(intent);
                        return true;
                    })
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        if (Constants.shopChache.corzina.size() > 0 && currency >= 0) {
            menu.getItem(0).setEnabled(true);
            menu.getItem(0).getIcon().mutate().setColorFilter(Color.argb(255,255,255,255),PorterDuff.Mode.SRC_IN);

        } else {
            menu.getItem(0).setEnabled(false);
            menu.getItem(0).getIcon().mutate().setColorFilter(Color.argb(255,170,170,170),PorterDuff.Mode.SRC_IN);
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateCorzina();
    }
}
