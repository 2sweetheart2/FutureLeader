package me.solo_team.futureleader.ui.menu.statical;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.Utils;
import me.solo_team.futureleader.ui.menu.MenuGrid;

public class StaticalGridLayout extends AppCompatActivity {
    MenuGrid grid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_statical);
        setTitle(getIntent().getStringExtra("title"));
        grid = new MenuGrid(findViewById(R.id.menu_statical_grid),getBaseContext(),getWindowManager());
        String[] objs = getIntent().getStringExtra("obj").split("\\]\\[");
        int column = 0;
        int offest = 0;
        for (String s : objs){
            String name = s.split("==")[0];
            String url = s.split("==")[2];
            String onAllColumn_ = s.split("==")[1];
            boolean onAllColumn = false;
            if(onAllColumn_.equals("1")) onAllColumn = true;
            if (offest % 2 != 0)
                column = 1;
            int row = offest / 2;
            addElemtn(name,url,row,column,onAllColumn);
            System.out.println(row+" "+column);
            if(onAllColumn)offest+=2;
            else offest++;
        }


    }

    private void addElemtn(String name, String url,int row, int column, boolean onAllColumn){
        if (Constants.cbm.sbm.containsKey(name)) {
            grid.addImageElement(Utils.getRoundedCornerBitmap(Constants.cbm.sbm.get(name), 10),onAllColumn ,row,column);
        } else {
            Utils.getBitmapFromURL(url, bitmap1 -> {
                if (bitmap1 == null) {
                    return;
                }
                Constants.cbm.sbm.put(name, bitmap1);
                runOnUiThread(() -> grid.addImageElement(Utils.getRoundedCornerBitmap(bitmap1, 10), onAllColumn,row,column));
            });
        }
    }
}
