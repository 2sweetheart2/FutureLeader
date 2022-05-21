package me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.menu.MenuGrid;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class Layout extends Her {
    List<ImageView> views = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.founder_layout);
        MenuGrid grid = new MenuGrid(findViewById(R.id.back__), this, getWindowManager());
        List<String> urls = Arrays.asList(
                "https://sun9-54.userapi.com/s/v1/if2/4FEl1v_9N9EWGeze7X8jgPyXmEh9O0GIT-DzKgqUn8rCUDvl8IjT6VHyU_0gfZYHhMuP6zb57KEsLKyFGgYH8bGr.jpg?size=800x800&quality=96&type=album",
                "https://sun9-22.userapi.com/s/v1/if2/Y4fRZeViEl7jE-bOtjA5gs3hcjlUeJldXzs8pQnH5K1EHUh8skhC9qdo6K5kKCoI7vmvYZdhez52QbJwRUqfrqot.jpg?size=800x800&quality=96&type=album",
                "https://sun9-48.userapi.com/s/v1/if2/PmCrNWsQdniIMink5IbU-6wLxc9y7vkDqwqHTFXUex3cCk4oz3IVuhQF_S1DsNf4pOhfsrTc3M5PqKR5_Atyt0Ls.jpg?size=800x800&quality=96&type=album"
        );
        for (int i = 0; i < urls.size(); i++) {
            int column = 0;
            if (i % 2 != 0)
                column = 1;
            int row = i / 2;
            boolean onAllColumn = false;
            if(i==2) onAllColumn = true;
            ImageView v = grid.addImageElement(null, onAllColumn, row, column);
            views.add(v);
            Constants.cache.addPhoto(urls.get(i), true, v, this);
        }
        setTitle("Будущие Лидеры");
    }

}
