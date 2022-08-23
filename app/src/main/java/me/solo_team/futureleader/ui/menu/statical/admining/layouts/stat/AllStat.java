package me.solo_team.futureleader.ui.menu.statical.admining.layouts.stat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class AllStat extends Her {

    int all;
    int reg;
    int week;
    int month;
    int allTime;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Статистика");
        if(!Constants.user.permission.can_get_stat){
            setResult(-500);
            finish();
        }
        setContentView(R.layout.all_stat);
        API.getAllStat(new ApiListener() {
            Dialog d;
            @Override
            public void onError(JSONObject json) throws JSONException {
                d.dismiss();
                finish();
            }

            @Override
            public void inProcess() {
                d = openWaiter(AllStat.this);
            }

            @Override
            public void onSuccess(JSONObject json) throws JSONException {
                all = json.getInt("all");
                reg = json.getInt("registered");
                week = json.getInt("week");
                month = json.getInt("month");
                allTime = json.getInt("all_time");
                runOnUiThread(()->{
                    d.dismiss();
                    render();
                });
            }
        }, new CustomString("token", Constants.user.token));


    }
    BarChart mBarChart;
    PieChart mPieChart;
    PieChart mPieChart2;
    public void render(){
        mBarChart = findViewById(R.id.barchart);
        mPieChart = findViewById(R.id.piechart);
        mPieChart2 = findViewById(R.id.piechart2);
        createThread();
    }

    @SuppressLint("NewApi")
    public void createThread(){
        new Thread(()->{
            try {
                runOnUiThread(() -> {
                    mPieChart.addPieSlice(new PieModel("зарегестрированы",all-week , Color.GRAY));
                    mPieChart.addPieSlice(new PieModel("не зарегестрированны", reg, Color.RED));
                    mPieChart.addPieSlice(new PieModel("онлайн", week, getColor(R.color.secondary)));
                    mPieChart.startAnimation();
                });
                Thread.sleep(2000);
                runOnUiThread(() ->{
                    mPieChart2.addPieSlice(new PieModel("зарегестрированы", all-month, Color.GRAY));
                    mPieChart2.addPieSlice(new PieModel("не зарегестрированны", reg, Color.RED));
                    mPieChart2.addPieSlice(new PieModel("онлайн", month, getColor(R.color.secondary)));
                    mPieChart2.startAnimation();});
                Thread.sleep(2000);
                runOnUiThread(() ->{
                    mBarChart.addBar(new BarModel(allTime, getColor(R.color.secondary)));
                    mBarChart.addBar(new BarModel(reg,  Color.RED));
                    mBarChart.addBar(new BarModel(all-allTime, Color.GRAY));
                    mBarChart.startAnimation();});
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
