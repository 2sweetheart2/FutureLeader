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

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.menu.statical.Founder.FounderLayout;
import me.solo_team.futureleader.ui.menu.statical.Media.PopularMusic;
import me.solo_team.futureleader.ui.menu.statical.admining.AdminingLayout;
import me.solo_team.futureleader.ui.menu.statical.applications.ApplicationsView;
import me.solo_team.futureleader.ui.menu.statical.dr.DrView;
import me.solo_team.futureleader.ui.menu.statical.programs.Endayment;
import me.solo_team.futureleader.ui.menu.statical.programs.FondCommands;
import me.solo_team.futureleader.ui.menu.statical.programs.ProgramsLayout;
import me.solo_team.futureleader.ui.menu.statical.shop.ShopView;

public class MenuFragment extends Fragment {
    View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        requireActivity().invalidateOptionsMenu();
        root = inflater.inflate(R.layout.fragment_menu, container, false);
        //создание настроек для сетки
        MenuGrid grid = new MenuGrid(root.findViewById(R.id.grid), root.getContext(), requireActivity().getWindowManager());

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = root.findViewById(R.id.menu_piace);
        recyclerView.setLayoutManager(layoutManager);
        RecycleAdapter rcd = new RecycleAdapter(this);
        recyclerView.setAdapter(rcd);

        //динамическое добавление элементов в сетку

        grid.addElement(null, "заявки", "16", false).setOnClickListener(v -> startActivity(new Intent(requireContext(), ApplicationsView.class)));
        grid.addElement(null, "магазин", "16", false).setOnClickListener(v -> startActivity(new Intent(requireContext(), ShopView.class)));
        grid.addElement(null, "именниники", "16", false).setOnClickListener(v -> startActivity(new Intent(requireContext(), DrView.class)));
        grid.addElement(null, "медиа", "16", false).setOnClickListener(v -> startActivity(new Intent(requireContext(), PopularMusic.class)));
        if (Constants.user.permission.can_view_admin_panel)
            grid.addElement(null, "администрирование", "16", true)
                    .setOnClickListener(v -> startActivity(new Intent(requireContext(), AdminingLayout.class)));

        ConstraintLayout cn = root.findViewById(R.id.menu_header);
        cn.getChildAt(0).setOnClickListener(v -> startActivity(new Intent(requireContext(), FounderLayout.class)));
        cn.getChildAt(2).setOnClickListener(v -> startActivity(new Intent(requireContext(), ProgramsLayout.class)));
        cn.getChildAt(4).setOnClickListener(v -> Utils.ShowSnackBar.show(requireContext(), "страница не доступна!", v));
        cn.getChildAt(6).setOnClickListener(v -> startActivity(new Intent(requireContext(), Endayment.class)));
        cn.getChildAt(8).setOnClickListener(v -> startActivity(new Intent(requireContext(), FondCommands.class)));
        return root;
    }

}