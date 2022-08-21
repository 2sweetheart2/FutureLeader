package me.solo_team.futureleader.ui.menu.statical.admining.layouts.moderation;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.imageview.ShapeableImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.SelectMembers;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;
import me.solo_team.futureleader.ui.menu.statical.admining.layouts.surveys.Ideas;

public class GetOldShopRequest extends Her {

    List<GetShopRequests.AdminShopRequest> requests = new ArrayList<>();
    LinearLayout list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("История заявок магазина");
        setContentView(R.layout.only_linearlayout);
        list = findViewById(R.id.list);
        Intent intent = new Intent(this, SelectMembers.class);
        intent.putExtra("needStuff", false);
        intent.putExtra("checker", false);
        intent.putExtra("removeSelf", false);
        intent.putExtra("selectOne",true);
        intent.putExtra("title","Заявки пользователей");
        startActivityIfNeeded(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            if(resultCode==1){
                get(data.getIntExtra("user_id",-1));
            }
            else
                if(resultCode==-500)
                    Utils.ShowSnackBar.show(GetOldShopRequest.this,"Отказано в доступе!",list);
                else
                    finish();
        }


    }

    public void get(int user_id){
        API.getShopHistory(new ApiListener() {
            Dialog d;

            @Override
            public void onError(JSONObject json) throws JSONException {
                d.dismiss();
                System.out.println(json);
                setResult(-1);
                finish();
            }

            @Override
            public void inProcess() {
                d = openWaiter(GetOldShopRequest.this);
            }

            @Override
            public void onSuccess(JSONObject json) throws JSONException {
                JSONArray ar = json.getJSONArray("requests");
                for (int i = 0; i < ar.length(); i++) {
                    GetShopRequests.AdminShopRequest adminShopRequest = new GetShopRequests.AdminShopRequest(ar.getJSONObject(i));
                    requests.add(adminShopRequest);
                }
                runOnUiThread(() -> {
                    render();
                });
                d.dismiss();

            }
        }, new CustomString("token", Constants.user.token),new CustomString("user_id",String.valueOf(user_id)));
    }

    public void render() {
        list.removeAllViews();
        for (int i = 0; i < requests.size(); i++) {
            GetShopRequests.AdminShopRequest adminShopRequest = requests.get(i);
            View view = getLayoutInflater().inflate(R.layout.obj_shop, null);
            view.findViewById(R.id.obj_shop_added).setVisibility(View.GONE);
            Constants.cache.addPhoto(adminShopRequest.item.photo,view.findViewById(R.id.obj_shop_photo), this);
            ((TextView) view.findViewById(R.id.obj_shop_name)).setText(adminShopRequest.item.name);
            ((TextView) view.findViewById(R.id.obj_shop_cost)).setText("");
            String stat;
            if(!adminShopRequest.status)
                stat = "отклонена";
            else
                stat = "подтверждена";
            ((TextView) view.findViewById(R.id.obj_shop_count)).setText("  статус: " + stat);
            ((TextView) view.findViewById(R.id.obj_shop_description)).setText(adminShopRequest.chatMember.getFullName()+"\n"+adminShopRequest.dateTime.toString(true));
            list.addView(view);
        }
    }
}
