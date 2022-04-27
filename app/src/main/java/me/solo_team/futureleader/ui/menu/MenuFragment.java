package me.solo_team.futureleader.ui.menu;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import me.solo_team.futureleader.R;

public class MenuFragment extends Fragment {
    View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_menu, container, false);
        //создание настроек для сетки
        gridSettings();
        //динамическое добавление элементов в сетку
        for (int i = 0; i < 10; i++) {
            addElement(null, String.valueOf(i));
        }
        return root;
    }

    private GridLayout gridLayout;
    private int offest = 0;

    private void gridSettings() {
        gridLayout = root.findViewById(R.id.grid);
        gridLayout.setColumnCount(2);
        gridLayout.setRowCount(15);
    }


    public void addElement(String urlPhoto, String text_) {
        Point size = new Point();
        requireActivity().getWindowManager().getDefaultDisplay().getSize(size);
        int halfScreenWidth = (int) (size.x * 0.5);
        int column = 0;
        int row = 0;
        if (offest % 2 == 0) {
            column = 0;
            row = (int) offest / 2;
        } else {
            column = 1;
            row = (int) offest / 2;
        }
        offest++;
        GridLayout.Spec col_ = GridLayout.spec(column);
        GridLayout.Spec row_ = GridLayout.spec(row);
        GridLayout.LayoutParams lp = new GridLayout.LayoutParams(row_, col_);
        TextView text = new TextView(root.getContext());
        lp.width = halfScreenWidth;
        lp.height = lp.width;
        text.setLayoutParams(lp);
        text.setGravity(Gravity.CENTER);
        text.setBackgroundColor(Color.WHITE);
        text.setText(text_);
        text.setTextAppearance(root.getContext(), android.R.style.TextAppearance_Large_Inverse);
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
        text.setBackground(requireContext().getDrawable(R.drawable.gray_gradiernt));
        gridLayout.addView(text, lp);
    }
}