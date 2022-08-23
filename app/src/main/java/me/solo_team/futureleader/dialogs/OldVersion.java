package me.solo_team.futureleader.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import me.solo_team.futureleader.R;

public class OldVersion extends AppCompatDialogFragment {

    Activity activity;

    public OldVersion(Activity activity) {
        this.activity = activity;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.only_linearlayout, null);
        builder.setView(view);
        // Остальной код
        TextView textView = new TextView(activity);
        SpannableString ss = new SpannableString("Простите, но данная версия программы устарела!\nСкачайте новую");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://future-leaders.ru/update"));
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan, 47, 61, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setHighlightColor(Color.TRANSPARENT);
        ((LinearLayout) view.findViewById(R.id.list)).addView(textView);
        builder.setCancelable(false);
        return builder.create();
    }

}
