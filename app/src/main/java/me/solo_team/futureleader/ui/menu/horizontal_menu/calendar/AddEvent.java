package me.solo_team.futureleader.ui.menu.horizontal_menu.calendar;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.Date;
import me.solo_team.futureleader.Objects.Time;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.Utils;
import me.solo_team.futureleader.dialogs.SelectTimeDialog;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class AddEvent extends Her {

    Date currentDate;
    EditText label;
    TextView selectedDate;
    TextView timeFrom;
    TextView timeFromButton;
    TextView timeTo;
    TextView timeToButton;
    EditText maxPeople;
    Spinner eventType;
    ImageView logo;
    EditText description;
    Button save;

    Time timeT = new Time(12, 0);
    Time timeF = new Time(0, 0);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentDate = new Date(getIntent().getStringExtra("date"));
        setContentView(R.layout.add_event);
        getObjects();
        setTitle("Добавить событие");
        selectedDate.setText("выбрана дата: " + currentDate.toVisibleStr());

        timeToButton.setOnClickListener(v -> {
            SelectTimeDialog selectTimeDialog = new SelectTimeDialog(time -> {
                timeT = time;
                timeTo.setText("конец: " + time.toStr());
            });
            selectTimeDialog.show(getSupportFragmentManager(), null);
        });

        timeFromButton.setOnClickListener(v -> {
            SelectTimeDialog selectTimeDialog = new SelectTimeDialog(time -> {
                timeF = time;
                timeFrom.setText("начало: " + time.toStr());
            });
            selectTimeDialog.show(getSupportFragmentManager(), null);
        });

        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(this, R.array.add_event_type,
                        android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventType.setAdapter(adapter);

        logo.setOnClickListener(v -> {
            if (!checkPerm(this, Manifest.permission.READ_EXTERNAL_STORAGE)) return;
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            //Тип получаемых объектов - image:
            photoPickerIntent.setType("image/*");
            //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
            startActivityForResult(Intent.createChooser(photoPickerIntent, "Выбирите изображение"), 1);
        });

        save.setOnClickListener(v -> {
            if (!check()) return;

            API.addEvents(new ApiListener() {
                              Dialog d;

                              @Override
                              public void onError(JSONObject json) throws JSONException {
                                    d.dismiss();
                              }

                              @Override
                              public void inProcess() {
                                  d = openWaiter(AddEvent.this);
                              }

                              @Override
                              public void onSuccess(JSONObject json) throws JSONException {
                                  d.dismiss();
                                  finish();
                              }
                          },
                    new CustomString("date", currentDate.toStr()),
                    new CustomString("name", label.getText().toString()),
                    new CustomString("time_from", timeF.toStr()),
                    new CustomString("time_to", timeT.toStr()),
                    new CustomString("max_members", maxPeople.getText().toString()),
                    new CustomString("image",url),
                    new CustomString("type",eventType.getSelectedItem().toString()),
                    new CustomString("description",description.getText().toString()),
                    new CustomString("token", Constants.user.token)
            );
        });
    }

    private boolean check() {
        View view = findViewById(R.id.add_event);
        if (label.getText().length() == 0) {
            Utils.ShowSnackBar.show(this, "Название не должно быть пустым", view);
            return false;
        }
        if (description.getText().length() == 0) {
            Utils.ShowSnackBar.show(this, "Описание не должно быть пустым", view);
            return false;
        }
        if (maxPeople.getText().length() == 0 || maxPeople.getText().toString().equals("0")) {
            Utils.ShowSnackBar.show(this, "Кол-во участников не должно быть пустым или равно 0", view);
            return false;
        }
        if(url==null){
            Utils.ShowSnackBar.show(this, "Отсутствует изображение", view);
            return false;
        }
        return true;
    }

    String url = null;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null) {
            Uri selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                API.uploadImage(new ApiListener() {
                    Dialog d;

                    @Override
                    public void onError(JSONObject json) {
                        d.dismiss();
                    }

                    @Override
                    public void inProcess() {
                        d = openWaiter(AddEvent.this);
                    }

                    @Override
                    public void onSuccess(JSONObject json) throws JSONException {
                        url = json.getString("url");
                        Constants.cache.addPhoto(url, true, logo, AddEvent.this);
                        d.dismiss();
                    }
                }, bitmap, new CustomString("token", Constants.user.token));
                //picture.setImageBitmap(image);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public boolean checkPerm(Context context, String permission) {
        int permissionStatus = ContextCompat.checkSelfPermission(context, permission);

        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
            return false;
        }
    }

    private void getObjects() {
        label = findViewById(R.id.add_event_label);
        selectedDate = findViewById(R.id.add_event_date);
        timeFrom = findViewById(R.id.add_event_from);
        timeFromButton = findViewById(R.id.add_event_button_edit_from);
        timeTo = findViewById(R.id.add_event_to);
        timeToButton = findViewById(R.id.add_event_button_edit_to);
        maxPeople = findViewById(R.id.add_event_max_people);
        eventType = findViewById(R.id.add_event_ebanyi_spiner);
        logo = findViewById(R.id.add_event_image);
        description = findViewById(R.id.add_event_description);
        save = findViewById(R.id.add_event_complete_button);
    }
}
