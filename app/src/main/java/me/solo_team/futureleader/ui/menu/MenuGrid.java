package me.solo_team.futureleader.ui.menu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import me.solo_team.futureleader.R;

public class MenuGrid {

    private final GridLayout gridLayout;
    private final Context context;
    private int offest =0;
    private final WindowManager windowManager;
    public MenuGrid(GridLayout gridLayout, Context context, WindowManager windowManager){
        this.gridLayout = gridLayout;
        this.context =context;
        gridLayout.setColumnCount(2);
        gridLayout.setRowCount(15);
        this.windowManager = windowManager;
    }

    public void addElement(String urlPhoto,String name){
        Point size = new Point();
        windowManager.getDefaultDisplay().getSize(size);
        int halfScreenWidth = (int) (size.x * 0.5);
        int column = 0;
        if (offest % 2 != 0)
            column=1;
        int row = offest / 2;
        offest++;
        GridLayout.Spec col_ = GridLayout.spec(column);
        GridLayout.Spec row_ = GridLayout.spec(row);
        GridLayout.LayoutParams lp = new GridLayout.LayoutParams(row_, col_);
        TextView text = new TextView(context);
        lp.width = halfScreenWidth;
        lp.height = lp.width;
        text.setLayoutParams(lp);
        text.setGravity(Gravity.CENTER);
        text.setBackgroundColor(Color.WHITE);
        text.setText(name);
        text.setTextAppearance(context, android.R.style.TextAppearance_Large_Inverse);
        if(urlPhoto!=null) Picasso.get().load(Uri.parse(urlPhoto)).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                text.setBackground(new BitmapDrawable(bitmap));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
        text.setBackground(context.getDrawable(R.drawable.gray_gradiernt));
        gridLayout.addView(text, lp);
    }

    public void addElements(String[] urls,String[] names){
        for(int i = 0;i<names.length;i++){
            addElement(urls[i],names[i]);
        }
    }
}
