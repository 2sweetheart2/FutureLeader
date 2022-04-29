package me.solo_team.futureleader.ui.profile;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.Achievement;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.Utils;

public class RecycleAchivementsAdapter extends RecyclerView.Adapter<RecycleAchivementsAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final Fragment fragment;
    private final List<Achievement> achievements;

    public RecycleAchivementsAdapter(Fragment fragment, List<Achievement> achievements) {
        this.inflater = fragment.getLayoutInflater();
        this.achievements = achievements;
        this.fragment = fragment;
    }

    @NotNull
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.profile_menu_achivement, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
        Bitmap bitmap = achievements.get(position).imageBitMap;
        holder.itemView.setOnClickListener(v -> {
            AlertAchivementDialog alc = new AlertAchivementDialog(achievements.get(position));
            alc.show(fragment.getFragmentManager(),null);
//            Intent intent = new Intent(requireContext(), OpenNewsFragment.class);
//            intent.putExtra("title", "Первый этап выборов Председателя Координационного Совета в самом разгаре!");
//            intent.putExtra("text", text);
//            startActivity(intent);
        });
        if (bitmap != null)
            holder.image.setImageBitmap(Utils.getRoundedCornerBitmap(bitmap, 10));
        else {

            Utils.getBitmapFromURL(achievements.get(position).image_url, bitmap1 -> {
                if (bitmap1 == null) {
                    return;
                }
                Constants.ab.bitmaps.put(position, bitmap1);
                achievements.get(position).imageBitMap = bitmap1;
                fragment.requireActivity().runOnUiThread(() -> holder.image.setImageBitmap(bitmap1));
            });
        }

    }


    @Override
    public int getItemCount() {
        return achievements.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView image;

        @RequiresApi(api = Build.VERSION_CODES.O)
        ViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.achivement_obj);
            image.getLayoutParams().height = 75;
            image.getLayoutParams().width = 75;
        }
    }
}