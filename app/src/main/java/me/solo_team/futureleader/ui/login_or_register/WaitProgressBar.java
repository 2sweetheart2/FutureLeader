package me.solo_team.futureleader.ui.login_or_register;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import me.solo_team.futureleader.R;

public class WaitProgressBar extends ProgressDialog {

    LayoutInflater inflater;
    public WaitProgressBar(Context context, LayoutInflater inflater) {
        super(context);
        this.inflater = inflater;
    }

    @SuppressLint("ResourceType")
    @Override
    public View onCreatePanelView(int featureId) {
        return inflater.inflate(R.id.wait_layout,null);
    }
}
