package me.solo_team.futureleader.ui.profile;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import me.solo_team.futureleader.Objects.Achievement;
import me.solo_team.futureleader.R;

public class AlertAchivementDialog extends AppCompatDialogFragment {

    private final Achievement achievement;

    public AlertAchivementDialog(Achievement achievement){
        this.achievement = achievement;
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.profile_alert_dialog, null);
        builder.setView(view);
        ImageView image = (ImageView)view.findViewById(R.id.profile_alert_image);
        image.setImageBitmap(achievement.imageBitMap);
        image.getLayoutParams().width=75;
        image.getLayoutParams().height=75;
        ((TextView)view.findViewById(R.id.profile_alert_title)).setText(achievement.name);
        ((TextView)view.findViewById(R.id.profile_alert_description)).setText(achievement.description);
        ((TextView)view.findViewById(R.id.profile_alert_cost)).setText("монет: "+achievement.coins);
        return builder.create();
    }
}
