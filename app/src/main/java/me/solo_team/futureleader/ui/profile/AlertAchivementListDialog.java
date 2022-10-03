package me.solo_team.futureleader.ui.profile;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.List;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.Achievement;
import me.solo_team.futureleader.R;

public class AlertAchivementListDialog extends AppCompatDialogFragment {

    private FragmentManager fragmentManager;

    Context context;

    Activity activity;
    Fragment fragment;
    LayoutInflater layouInflater;
    public AlertAchivementListDialog(FragmentManager fragmentManager, Context context, Fragment fragment, LayoutInflater inflater){
        this.fragmentManager = fragmentManager;
        this.context = context;
        this.layouInflater = inflater;
        this.fragment =fragment;
    }
    public AlertAchivementListDialog(FragmentManager fragmentManager, Activity activity, LayoutInflater inflater){
        this.fragmentManager = fragmentManager;
        this.activity = activity;
        this.layouInflater = inflater;
        context = activity.getApplicationContext();
    }


    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = layouInflater;
        View view = inflater.inflate(R.layout.profile_alert_dialog_list, null);
        builder.setView(view);
        RecyclerView recyclerView = view.findViewById(R.id.achivement_list);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);
        System.out.println("ACHIEVEMTNS SIZE: "+Constants.currentUser.achievements.size());
        RecycleAdapter2 adapter;
        if(activity==null)
            adapter = new RecycleAdapter2(fragmentManager, Constants.currentUser.achievements,null,fragment,layouInflater);
        else
            adapter = new RecycleAdapter2(fragmentManager, Constants.currentUser.achievements,null,activity );

        recyclerView.setAdapter(adapter);


        return builder.create();
    }
}
