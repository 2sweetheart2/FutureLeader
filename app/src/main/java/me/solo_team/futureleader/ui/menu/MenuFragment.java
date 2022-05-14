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
import me.solo_team.futureleader.ui.menu.statical.Founder.FounderLayout;

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
        Intent intent = new Intent(requireContext(), FounderLayout.class);
        grid.addElement(null, "заявки", "16", false);
        grid.addElement(null, "магазин", "16", false);
        grid.addElement(null, "FAQ", "16", false);
        grid.addElement(null, "заявки", "16", false);
        grid.addElement(null, "именниники", "16", true);

        ConstraintLayout cn = root.findViewById(R.id.menu_header);
        cn.getChildAt(0).setOnClickListener(v -> {
//            Constants.gsi.info.put("основатель", Arrays.asList(
//                    new Obj(ObjTypes.PHOTO,"приветственное слово","https://sun9-23.userapi.com/s/v1/if2/tM_Y_sBfisMuQ09vhw82DSAYqDqDp1q5g9JUM57n-S4brhRcMiSYFkmo9_daGjjO_5fgEvfqN7mCo23DASCtxpON.jpg?size=800x800&quality=96&type=album",null,new String[]{"https://futureleaders.hrbox.io/file/open/b94c0001-0a8c-4996-b2a6-dbd118b8094e.jpg"}),
//                    new Obj(ObjTypes.PHOTO,"Биография","https://sun9-43.userapi.com/s/v1/if2/7e1Wwc235L4IEQYbXOleM2ItyonRzOvNOcDaCe0WSSHPQnkXGGHexdr0gUIQEgx7PZlWJ1gSszwXbnHrmlCU1Wnb.jpg?size=800x800&quality=96&type=album",null,new String[]{"https://futureleaders.hrbox.io/file/open/51b0bb44-19d8-4b65-b32b-29fc4fa2ae9d.jpg"}),
//                    new Obj(ObjTypes.BLOCKS,"Социальные сети","https://sun9-55.userapi.com/s/v1/if2/PrkagT_k8KcZVEliaC93v5HYHHxwBH0c2BVmfCZfapEoYV6Ho5A2BYZXNsQJIYadbgUgoWPJN1BFj178vIXNAL5i.jpg?size=800x800&quality=96&type=album",null,null)
//                    ));
            startActivity(intent);
        });
        return root;
    }

}