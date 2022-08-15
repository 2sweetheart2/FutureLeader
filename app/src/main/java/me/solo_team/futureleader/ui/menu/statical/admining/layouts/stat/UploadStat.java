package me.solo_team.futureleader.ui.menu.statical.admining.layouts.stat;

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
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.CDateTime;
import me.solo_team.futureleader.Objects.ChatMember;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class UploadStat extends Her {

    LinearLayout list;
    Button button;
    List<File> files = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Журнал добавления файлов");
        setContentView(R.layout.stat);
        list = findViewById(R.id.list);
        button = findViewById(R.id.btn);
        button.setOnClickListener(v -> {offset+=25;render();});
        API.getFilesLogs(new ApiListener() {
            Dialog d;
            @Override
            public void onError(JSONObject json) throws JSONException {
                System.out.println(json);
                d.dismiss();
                finish();
            }

            @Override
            public void inProcess() {
                d = openWaiter(UploadStat.this);
            }

            @Override
            public void onSuccess(JSONObject json) throws JSONException {
                JSONArray ar = json.getJSONArray("files");
                for(int i=0;i<ar.length();i++){
                    files.add(new File(ar.getJSONObject(i)));
                }
                runOnUiThread(()->{render();d.dismiss();});
            }
        },new CustomString("token", Constants.user.token));
    }

    int offset = 25;

    public void render(){
        int of;
        System.out.println(files.size());
        if(files.size()<offset)
            of = files.size();
        else
            of = offset;
        list.removeAllViews();
        for(int i=0;i<of;i++){
            File file = files.get(i);
            View view = getLayoutInflater().inflate(R.layout.admin_unverified_user,null);
            ((TextView)view.findViewById(R.id.unverifi_name)).setText(file.creator.getFullName());
            ((TextView)view.findViewById(R.id.unverifi_phone)).setText(file.dateTime.toString(true));
            ((TextView) view.findViewById(R.id.unverifi_date)).setText("формат файла: "+file.format);
            TextView textView = view.findViewById(R.id.unverifi_email);
            SpannableString ss = new SpannableString("ссылка на файл здесь");
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View textView) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(file.url));
                    startActivity(browserIntent);
                }
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);
                }
            };
            ss.setSpan(clickableSpan, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            textView.setText(ss);
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            textView.setHighlightColor(Color.TRANSPARENT);

            list.addView(view);
        }
    }




    static class File{
        public String url;
        public CDateTime dateTime;
        public String format;
        public ChatMember creator;

        public File(JSONObject payload){
            try{
                url = payload.getString("file");
                dateTime = new CDateTime(payload.getString("date"),true);
                format = payload.getString("type");
                creator = new ChatMember(payload);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
