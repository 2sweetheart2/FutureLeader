package me.solo_team.futureleader.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import me.solo_team.futureleader.R;

public class MenuFragment extends Fragment {
    View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_menu, container, false);
        //создание настроек для сетки
        MenuGrid grid = new MenuGrid(root.findViewById(R.id.grid), root.getContext(), requireActivity().getWindowManager());
        //динамическое добавление элементов в сетку
        for (int i = 0; i < 10; i++) {
            grid.addElement(null, String.valueOf(i));
        }
        return root;
    }

}