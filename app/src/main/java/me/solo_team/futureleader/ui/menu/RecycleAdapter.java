package me.solo_team.futureleader.ui.menu;

import android.annotation.SuppressLint;
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

import me.solo_team.futureleader.R;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<String> names;
    private final List<String> values;

    public RecycleAdapter(Fragment fragment) {
        this.inflater = fragment.getLayoutInflater();
        values = Arrays.asList("обучение", "календарь", "избранное", "идеи", "опросы", "связь","кс");
        names = Arrays.asList("\uD83C\uDF93", "\uD83D\uDCC5", "\u2B50", "\uD83D\uDCA1", "\uD83D\uDCE2", "\uD83D\uDCDE","хз чё");
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
        holder.nameView.setText(values.get(position));
    }


    @Override
    public int getItemCount() {
        return names.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView flagView;
        final TextView logoNameView;
        final TextView nameView;

        @RequiresApi(api = Build.VERSION_CODES.O)
        ViewHolder(View view) {
            super(view);
            flagView = view.findViewById(R.id.obj__image);
            logoNameView = view.findViewById(R.id.obj__text);
            nameView = view.findViewById(R.id.obj__text2);
        }
    }
}