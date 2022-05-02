package me.solo_team.futureleader.ui.menu.statical;

import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import me.solo_team.futureleader.R;

public class FounderLayout extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.founder_layout);
        captilaze(findViewById(R.id.back__),getWindowManager());
    }

    private void captilaze(GridLayout gridLayout, WindowManager windowManager){
        Point size = new Point();
        windowManager.getDefaultDisplay().getSize(size);
        int halfScreenWidth = (int) (size.x * 0.5) - 20;
        for(int i =0;i<gridLayout.getChildCount();i++){
            gridLayout.getChildAt(i).getLayoutParams().width = halfScreenWidth;
            gridLayout.getChildAt(i).getLayoutParams().height = halfScreenWidth;
        }
    }
}
