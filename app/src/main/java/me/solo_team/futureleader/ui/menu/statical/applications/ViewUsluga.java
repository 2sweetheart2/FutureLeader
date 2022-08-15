package me.solo_team.futureleader.ui.menu.statical.applications;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.Date;
import me.solo_team.futureleader.Objects.Time;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.DateTimePicker;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class ViewUsluga extends Her {
    List<JSONObject> objects = new ArrayList<>();
    LinearLayout list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getIntent().getStringExtra("title"));
        List<String> ar = getIntent().getStringArrayListExtra("obj");
        for (String a : ar) {
            try {
                objects.add(new JSONObject(a));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        setContentView(R.layout.usluga_view);
        list = findViewById(R.id.usluga_view_list);
        render();
    }

    TextView date;
    TextView time;
    List<EditText> infos = new ArrayList<>();
    TextView fileName;
    LinkedHashMap<JSONObject, View> vies = new LinkedHashMap<>();

    private void render() {
        list.removeAllViews();
        for (JSONObject o : objects) {
            try {
                String name = o.getString("name");
                String type = o.getString("type");
                TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 10, 0, 10);
                switch (type) {
                    case "text":
                        TextView textView = new TextView(ViewUsluga.this);
                        textView.setText(name);
                        textView.setLayoutParams(params);
                        list.addView(textView);
                        EditText editText = new EditText(ViewUsluga.this);
                        editText.setLayoutParams(params);
                        list.addView(editText);
                        infos.add(editText);
                        vies.put(o, editText);
                        break;
                    case "datetime":
                        TextView textView2 = new TextView(ViewUsluga.this);
                        textView2.setText(name);
                        textView2.setLayoutParams(params);
                        list.addView(textView2);
                        date = new TextView(ViewUsluga.this);
                        date.setText("Дата:");
                        date.setLayoutParams(params);
                        list.addView(date);
                        vies.put(o, date);
                        time = new TextView(ViewUsluga.this);
                        time.setText("Время:");
                        time.setLayoutParams(params);
                        list.addView(time);
                        Button selectDateTime = new Button(ViewUsluga.this);
                        selectDateTime.setText("выбрать дату и время");
                        selectDateTime.setLayoutParams(params);
                        selectDateTime.setOnClickListener(v -> startActivityForResult(new Intent(ViewUsluga.this, DateTimePicker.class), 100));
                        list.addView(selectDateTime);
                        break;
                    case "file":
                        TextView textView1 = new TextView(ViewUsluga.this);
                        textView1.setText(name);
                        textView1.setLayoutParams(params);
                        list.addView(textView1);
                        fileName = new TextView(ViewUsluga.this);
                        fileName.setText("имя файла:");
                        fileName.setLayoutParams(params);
                        Button button = new Button(ViewUsluga.this);
                        button.setText("выбрать файл");
                        button.setLayoutParams(params);
                        button.setOnClickListener(v -> {
                            Intent chooseFile;
                            Intent intent;
                            chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                            chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
                            chooseFile.setType("*/*");
                            intent = Intent.createChooser(chooseFile, "Choose a file");
                            startActivityForResult(intent, 10);
                        });
                        list.addView(fileName);
                        list.addView(button);
                        vies.put(o, fileName);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    Uri uri;
    Date date_;
    Time time_;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(requestCode);
        System.out.println(resultCode);
        if (resultCode == 1) {
            if (requestCode == 100) {
                time_ = new Time(data.getStringExtra("time"));
                String[] date = data.getStringExtra("date").split("/");
                date_ = new me.solo_team.futureleader.Objects.Date(date);
                this.date.setText("Дата: " + date_.toVisibleStr());
                this.time.setText("Время: " + time_.toStr());
            }

            if (requestCode == 10) {
                Uri uri = data.getData();
                String fileFormat = Utils.getFileFormat(uri, ViewUsluga.this);
                if (!"docx".equals(fileFormat)) {
                    Utils.ShowSnackBar.show(ViewUsluga.this, "Файл должен быть формата docx!", fileName);
                    return;
                }
                this.uri = uri;
                fileName.setText("имя файла: " + Utils.getFileName(uri, ViewUsluga.this));
            }
        } else {
            if (requestCode == 10 && data != null) {
                Uri uri = data.getData();
                String fileFormat = Utils.getFileFormat(uri, ViewUsluga.this);
                if (!"docx".equals(fileFormat)) {
                    Utils.ShowSnackBar.show(ViewUsluga.this, "Файл должен быть формата docx!", fileName);
                    return;
                }
                this.uri = uri;
                API.addDOCXFile(new ApiListener() {
                    Dialog d;
                    @Override
                    public void onError(JSONObject json) throws JSONException {
                        d.dismiss();
                    }

                    @Override
                    public void inProcess() {
                        d = openWaiter(ViewUsluga.this);
                    }

                    @Override
                    public void onSuccess(JSONObject json) throws JSONException {
                        runOnUiThread(()->{
                            fileName.setText("имя файла: " + Utils.getFileName(uri, ViewUsluga.this));
                            try {
                                docxUrl = json.getString("url");
                                d.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                },getBytesFromUri(uri),decodeString(Utils.getFileName(uri, ViewUsluga.this)),new CustomString("token",Constants.user.token));
            }
        }
    }
    String docxUrl;
    public String decodeString(String input) {
        try {
            String transportString = URLEncoder.encode(input, "UTF-8");
            return URLDecoder.decode(transportString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "")
                .setIcon(R.drawable.done)
                .setOnMenuItemClickListener(item -> {
                    for (EditText t : infos) {
                        System.out.println("INFO: " + t.getText().toString());
                    }
                    if (date != null)
                        System.out.println(date.getText().toString());
                    if (time != null)
                        System.out.println(time.getText().toString());
                    if (uri != null)
                        System.out.println(fileName.getText().toString());

                    JSONObject object = new JSONObject();
                    try {
                        object.put("type", getIntent().getStringExtra("title"));
                        JSONArray array = new JSONArray();
                        ArrayList<JSONObject> keys = new ArrayList<JSONObject>(vies.keySet());
                        for(int i=0;i<keys.size();i++){
                            View val = vies.get(keys.get(i));
                            JSONObject o2 = keys.get(i);
                            if (o2.getString("type").equals("datetime")) {
                                if(date_==null || time_==null){
                                    Utils.ShowSnackBar.show(ViewUsluga.this,"Дата и время не выбраны!",val);
                                    return false;
                                }
                                o2.put("value", date_.toStr() + "[]" + time_.toStr());
                            } else if (o2.getString("type").equals("file")) {
                                if(docxUrl==null){
                                    Utils.ShowSnackBar.show(ViewUsluga.this,"Файл не выбраны!",val);
                                    return false;
                                }
                                o2.put("file", docxUrl);
                            }
                            else if(o2.getString("type").equals("text")){
                                if(((EditText)val).getText().toString().length()==0){
                                    Utils.ShowSnackBar.show(ViewUsluga.this,"Не везде написан текст!",val);
                                    return false;
                                }
                                o2.put("value",((EditText)val).getText().toString());
                            }
                            array.put(o2);
                        }
                        object.put("fields",array);
                        JSONObject n = new JSONObject();
                        n.put("token", Constants.user.token);
                        n.put("params",object);
                        API.uploadApplication(new ApiListener() {
                            Dialog d;
                                @Override
                                public void onError(JSONObject json) throws JSONException {
                                    createNotification(vies.get(keys.get(0)),json.getString("message"));
                                    d.dismiss();
                                }

                                @Override
                                public void inProcess() {
                                    d = openWaiter(ViewUsluga.this);
                                }

                                @Override
                                public void onSuccess(JSONObject json) throws JSONException {
                                    d.dismiss();
                                    runOnUiThread(()->{
                                        setResult(1);
                                        finish();
                                    });
                                }
                            },n
                                    );

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return true;
                })
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return super.onPrepareOptionsMenu(menu);
    }

    private byte[] getBytesFromUri(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            return Utils.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
