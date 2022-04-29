package me.solo_team.futureleader.ui.menu.statical;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.Utils;
import me.solo_team.futureleader.ui.menu.MenuGrid;

public class StaticalGridLayout extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_statical);
        setTitle(getIntent().getStringExtra("title"));
        HashMap<String,String> hash = new HashMap<>();
        MenuGrid grid = new MenuGrid(findViewById(R.id.menu_statical_grid),getBaseContext(),getWindowManager());
        String[] objs = getIntent().getStringExtra("obj").split("\\]\\[");
        for(int i=0;i<objs.length;i++) {
            String name = objs[i].split("=")[0];
            String url = objs[i].split("=")[1];
            Bitmap bitmap = null;
            if(Constants.cbm.sbm.containsKey(name)) {bitmap = Constants.cbm.sbm.get(name);
            grid.addElement(Utils.getRoundedCornerBitmap(bitmap,10),"",null,false);
            }
            else {
                Utils.getBitmapFromURL(url, bitmap1 -> {
                    if (bitmap1 == null) {
                        return;
                    }
                    Constants.cbm.sbm.put(name, bitmap1);
                    runOnUiThread(()->grid.addElement(Utils.getRoundedCornerBitmap(bitmap1,10),"",null,false));
                });
            }
        }



    }
}
