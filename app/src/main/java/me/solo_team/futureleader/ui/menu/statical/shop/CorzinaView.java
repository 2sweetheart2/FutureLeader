package me.solo_team.futureleader.ui.menu.statical.shop;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

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

public class CorzinaView extends Her {


    LinearLayout list;
    TextView orderTerxt;
    Button orderComplete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Корзина");

        setContentView(R.layout.corzina);

        list = findViewById(R.id.corzina_list);
        orderTerxt = findViewById(R.id.corzina_order_info);
        orderComplete = findViewById(R.id.corzina_order_button);

        list.removeAllViews();

        int allCost = 0;
        List<String> ids = new ArrayList<>();
        for (ShopItem item : Constants.shopChache.corzina) {
            ids.add(String.valueOf(item.id));
            View view = getLayoutInflater().inflate(R.layout.obj_shop, null);
            ((TextView) view.findViewById(R.id.obj_shop_name)).setText(item.name);
            ((TextView) view.findViewById(R.id.obj_shop_cost)).setText("Стоимость: " + item.cost + " FBL");
            ((TextView) view.findViewById(R.id.obj_shop_count)).setText("Осталось: " + item.count);
            ((TextView) view.findViewById(R.id.obj_shop_description)).setText(item.description);
            Constants.cache.addPhoto(item.photo, true, view.findViewById(R.id.obj_shop_photo), this);
            ImageView plus = view.findViewById(R.id.obj_shop_added);
            plus.setVisibility(View.GONE);
            list.addView(view);
            allCost+=item.cost;
        }


        orderTerxt.setText("Итог: "+allCost+" FBL\nОстаток: "+(Constants.user.currency-allCost)+" FBL");
        orderComplete.setOnClickListener(v -> {
            System.out.println(ids);
            for(String id : ids){
                createOrder(id);
            }
            new Thread(()->{
                while (complete.size()!=ids.size()){
                }
                runOnUiThread(()->{
                    Constants.shopChache.corzina.clear();
                    d.dismiss();
                    System.out.println("END");
                    CorzinaView.this.finish();
                });
            }).start();


        });
    }
    List<Boolean> complete = new ArrayList<>();
    Dialog d;

    private void createOrder(String id){
        API.addShopOrder(new ApiListener() {
                             @Override
                             public void onError(JSONObject json) throws JSONException {
                                 System.out.println(json);
                                 complete.add(false);
                             }

                             @Override
                             public void inProcess() {
                                 if(d==null)
                                    d = openWaiter(CorzinaView.this);
                             }

                             @Override
                             public void onSuccess(JSONObject json) throws JSONException {
                                 System.out.println(json);
                                 Constants.user.currency = json.getInt("currency");
                                 complete.add(true);
                             }
                         },
                new CustomString("token",Constants.user.token),
                new CustomString("id",id)
        );
    }


}
