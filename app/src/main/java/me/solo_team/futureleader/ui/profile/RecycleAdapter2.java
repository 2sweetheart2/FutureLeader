package me.solo_team.futureleader.ui.profile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.Achievement;
import me.solo_team.futureleader.Objects.Postal;
import me.solo_team.futureleader.R;

public class RecycleAdapter2 extends RecyclerView.Adapter<RecycleAdapter2.ViewHolder> {

    private final List<Achievement> achievements;
    private final List<Postal> postals;
    androidx.fragment.app.FragmentManager fragmentManager2;
    Activity activity;
    LayoutInflater inflater;
    Fragment fragment;

    public RecycleAdapter2(FragmentManager fragmentManager2, List<Achievement> achievements, List<Postal> postals, Fragment fragment, LayoutInflater inflater) {
        this.fragmentManager2 = fragmentManager2;
        this.achievements = achievements;
        this.postals = postals;
        this.inflater = inflater;
        this.fragment =fragment;
    }

    public RecycleAdapter2(FragmentManager fragmentManager, List<Achievement> achievements, List<Postal> postals, Activity activity) {
        fragmentManager2 = fragmentManager;
        this.achievements = achievements;
        this.postals = postals;
        this.activity = activity;
        this.inflater = activity.getLayoutInflater();
    }



    @NotNull
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycle_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(postals==null) {
            holder.text.setText(achievements.get(position).name);
            if (activity == null) {
                Constants.cache.addPhoto(achievements.get(position).image_url, holder.image, fragment);
                System.out.println("SET NAME: " + achievements.get(position));
                holder.image.setOnClickListener(view -> {
                    AlertAchivementDialog alertAchivementDialog = new AlertAchivementDialog(Constants.currentUser.achievements.get(position));
                    alertAchivementDialog.show(fragmentManager2, null);
                });
            } else {
                Constants.cache.addPhoto(achievements.get(position).image_url, holder.image, activity);
                System.out.println("SET NAME: " + achievements.get(position));
                holder.image.setOnClickListener(view -> {
                    AlertAchivementDialog alertAchivementDialog = new AlertAchivementDialog(Constants.currentUser.achievements.get(position));
                    alertAchivementDialog.show(fragmentManager2, null);
                });
            }
        }else{
            if(postals.get(position).text!=null) {
                String text = postals.get(position).text.split(" ")[0];
                if(text.split(" ").length>=2) {
                    text += ' ' + postals.get(position).text.split(" ")[1];
                    holder.text.setText(text + ' ' + postals.get(position).text.split(" ")[2] + "...");
                }
                else
                    holder.text.setText(text+"...");
            }
            if (activity == null) {
                Constants.cache.addPhoto(postals.get(position).photoUrl, holder.image, fragment);
                System.out.println("SET NAME: " + postals.get(position));
                holder.image.setOnClickListener(view -> {
                    AlertAchivementDialog alertAchivementDialog = new AlertAchivementDialog(postals.get(position));
                    alertAchivementDialog.show(fragmentManager2, null);
                });
            } else {
                Constants.cache.addPhoto(postals.get(position).photoUrl, holder.image, activity);
                System.out.println("SET NAME: " + postals.get(position));
                holder.image.setOnClickListener(view -> {
                    AlertAchivementDialog alertAchivementDialog = new AlertAchivementDialog(postals.get(position));
                    alertAchivementDialog.show(fragmentManager2, null);
                });
            }
        }
    }


    @Override
    public int getItemCount() {
        if(postals!=null)
            return postals.size();
        return achievements.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView image;
        final TextView text;

        @RequiresApi(api = Build.VERSION_CODES.O)
        ViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.achivement_list_image);
            text = view.findViewById(R.id.achivement_list_name);
        }
    }
}