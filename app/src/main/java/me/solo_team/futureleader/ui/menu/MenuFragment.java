package me.solo_team.futureleader.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.menu.statical.StaticalGridLayout;

public class MenuFragment extends Fragment {
    View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_menu, container, false);
        //создание настроек для сетки
        MenuGrid grid = new MenuGrid(root.findViewById(R.id.grid), root.getContext(), requireActivity().getWindowManager());

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = root.findViewById(R.id.menu_piace);
        recyclerView.setLayoutManager(layoutManager);
        RecycleAdapter rcd = new RecycleAdapter(this);
        recyclerView.setAdapter(rcd);

        //динамическое добавление элементов в сетку
        Intent intent = new Intent(requireContext(), StaticalGridLayout.class);
        grid.addElement(null, "заявки","16",false);
        grid.addElement(null, "магазин","16",false);
        grid.addElement(null, "FAQ","16",false);
        grid.addElement(null, "заявки","16",false);
        grid.addElement(null, "именниники","16",true);

        ConstraintLayout cn = root.findViewById(R.id.menu_header);
        cn.getChildAt(0).setOnClickListener(v -> {
            intent.putExtra("title","основатели");
            intent.putExtra("obj","" +
                    "приветсвие=https://futureleaders.hrbox.io/file/resize/800x800/01debe8d-5716-4c76-98ba-54950ac7aa6f.jpg][" +
                    "биография=https://futureleaders.hrbox.io/file/resize/800x800/f10e0221-d698-4383-8c16-ea2b5d426046.jpg][" +
                    "соцсети=https://futureleaders.hrbox.io/file/resize/800x800/786669e1-1db3-421f-a864-47ec1c713a9e.jpg");
            startActivity(intent);
        });
        return root;
    }

}