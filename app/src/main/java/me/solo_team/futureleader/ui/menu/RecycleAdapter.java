package me.solo_team.futureleader.ui.menu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import me.solo_team.futureleader.Objects.IdeasStuff;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.menu.horizontal_menu.calendar.Calendar;
import me.solo_team.futureleader.ui.menu.horizontal_menu.idea.Idea;
import me.solo_team.futureleader.ui.menu.horizontal_menu.surveys.SurveysView;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<String> names;
    private final List<String> values;
    Fragment fragment;

    public RecycleAdapter(Fragment fragment) {
        this.inflater = fragment.getLayoutInflater();
        this.fragment = fragment;
        values = Arrays.asList( "календарь",  "идеи", "опросы", "связь","кс");
        names = Arrays.asList( "\uD83D\uDCC5", "\uD83D\uDCA1", "\uD83D\uDCE2", "\uD83D\uDCDE","хз чё");
    }


    @NotNull
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.menu_fragment_piace, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.logoNameView.setText(names.get(position));
        View.OnClickListener click = null;
        switch (position) {
            case 0:
                click = v -> fragment.startActivity(new Intent(fragment.requireContext(),Calendar.class));
                break;
            case 1:
                click = v -> fragment.startActivity(new Intent(fragment.requireContext(), Idea.class));
                break;
            case 2:
                click = v -> fragment.startActivity(new Intent(fragment.requireContext(), SurveysView.class));
                break;
        }
        holder.nameView.setText(values.get(position));
        if(click!=null)
            holder.rootView.setOnClickListener(click);
    }


    @Override
    public int getItemCount() {
        return names.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView flagView;
        final TextView logoNameView;
        final TextView nameView;
        final View rootView;

        @RequiresApi(api = Build.VERSION_CODES.O)
        ViewHolder(View view) {
            super(view);
            flagView = view.findViewById(R.id.obj__image);
            logoNameView = view.findViewById(R.id.obj__text);
            nameView = view.findViewById(R.id.obj__text2);
            rootView = view;
        }
    }
}