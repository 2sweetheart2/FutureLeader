package me.solo_team.futureleader.ui.dashboard;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import me.solo_team.futureleader.R;

public class DashboardFragment extends Fragment {
    View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_dashboard, container, false);
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


    public void addElement(Bitmap bitmap, String text_) {
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
        System.out.println("ADD ELEMT IN " + column + " " + row + " (" + offest + ")");
        offest++;
        GridLayout.Spec col_ = GridLayout.spec(column);
        GridLayout.Spec row_ = GridLayout.spec(row);
        GridLayout.LayoutParams lp = new GridLayout.LayoutParams(row_, col_);
        TextView text = new TextView(root.getContext());
        lp.width = halfScreenWidth;
        text.setLayoutParams(lp);
        text.setGravity(Gravity.CENTER);
        text.setBackgroundColor(Color.WHITE);
        text.setText(text_);
        text.setTextAppearance(root.getContext(), android.R.style.TextAppearance_Large_Inverse);
        text.setBackground(requireContext().getDrawable(R.drawable.ic_launcher_background));
        gridLayout.addView(text, lp);
    }
}