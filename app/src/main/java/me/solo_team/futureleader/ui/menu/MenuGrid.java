package me.solo_team.futureleader.ui.menu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import me.solo_team.futureleader.R;

public class MenuGrid {

    private final GridLayout gridLayout;
    private final Context context;
    private int offest = 0;
    private final WindowManager windowManager;

    public MenuGrid(GridLayout gridLayout, Context context, WindowManager windowManager) {
        this.gridLayout = gridLayout;
        this.context = context;
        gridLayout.setColumnCount(2);
        gridLayout.setRowCount(15);
        this.windowManager = windowManager;
    }

    public View addElement(String urlPhoto, String name, String textSize, boolean onAllColumn) {
        Point size = new Point();
        windowManager.getDefaultDisplay().getSize(size);
        int halfScreenWidth = (int) (size.x * 0.5) - 20;
        int column = 0;
        if (offest % 2 != 0)
            column = 1;
        int row = offest / 2;

        GridLayout.Spec col_;
        if (onAllColumn) col_ = GridLayout.spec(column, 2);
        else col_ = GridLayout.spec(column);
        GridLayout.Spec row_ = GridLayout.spec(row);
        GridLayout.LayoutParams lp = new GridLayout.LayoutParams(row_, col_);
        if (onAllColumn) {
            offest += 2;
            lp.width = halfScreenWidth * 2 + 5;
        } else {
            offest++;
            lp.width = halfScreenWidth;
        }
        TextView text = new TextView(context);

        lp.height = halfScreenWidth;
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.bottomMargin = 5;
        lp.topMargin = 5;
        text.setLayoutParams(lp);
        text.setGravity(Gravity.CENTER);
        text.setBackgroundColor(Color.WHITE);
        text.setText(name);
        if (textSize != null) text.setTextSize(Float.parseFloat(textSize));
        text.setTextAppearance(context, android.R.style.TextAppearance_Large_Inverse);
        if (urlPhoto != null) Picasso.get().load(Uri.parse(urlPhoto)).into(new Target() {
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
        text.setTextColor(context.getColor(R.color.text_color1));
        text.setBackground(context.getDrawable(R.drawable.gray_gradient_with_corners));
        gridLayout.addView(text, lp);
        return text;
    }

    public void addElements(String[] urls, String[] names) {
        for (int i = 0; i < names.length; i++) {
            addElement(urls[i], names[i], null, false);
        }
    }
}
