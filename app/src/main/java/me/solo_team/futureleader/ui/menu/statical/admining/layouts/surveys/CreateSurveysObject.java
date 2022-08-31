package me.solo_team.futureleader.ui.menu.statical.admining.layouts.surveys;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;

public class CreateSurveysObject extends AppCompatActivity {

    LinearLayout list;
    EditText editText;
    LinearLayout.LayoutParams lp;
    Button button;
    CheckBox checkBox1;
    CheckBox checkBox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_surveys_obj);
        list = findViewById(R.id.list);
        lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        checkBox = findViewById(R.id.create_surveys_text);
        checkBox1 = findViewById(R.id.create_surveys_one_of);

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkBox1.setChecked(false);
                addText();
            }
        });
        checkBox1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkBox.setChecked(false);
                addOneOf();
            }
        });
        checkBox.setChecked(true);


        button = findViewById(R.id.create_surveys_btn);
        button.setOnClickListener(v -> {
            if (!Utils.checkPerm(CreateSurveysObject.this, Manifest.permission.READ_EXTERNAL_STORAGE, this))
                return;
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            //Тип получаемых объектов - image:
            photoPickerIntent.setType("image/*");
            //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
            startActivityForResult(Intent.createChooser(photoPickerIntent, "Выбeрите изображение"), 1);
        });
    }

    private void addText() {
        list.removeAllViews();
        editText = new EditText(CreateSurveysObject.this);
        editText.setHint("текст вопроса");
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.setLayoutParams(lp);
        editText.setMaxLines(1);
        list.addView(editText);
        if (imageUrl != null) {
            ImageView imageView = new ImageView(CreateSurveysObject.this);
            imageView.setLayoutParams(lp);
            Constants.cache.addPhoto(imageUrl, imageView, this);
            list.addView(imageView);
        }
    }

    EditText editTextSwith;

    private void addOneOf() {
        list.removeAllViews();
        editText = new EditText(CreateSurveysObject.this);
        editText.setHint("текст вопроса");
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.setLayoutParams(lp);
        editText.setMaxLines(1);
        list.addView(editText);
        editTextSwith = new EditText(CreateSurveysObject.this);
        editTextSwith.setLayoutParams(lp);
        editTextSwith.setHint("для разделения вариантов ответа, поставте между ними знак &");
        list.addView(editTextSwith);
        if (imageUrl != null) {
            ImageView imageView = new ImageView(CreateSurveysObject.this);
            imageView.setLayoutParams(lp);
            Constants.cache.addPhoto(imageUrl, imageView, this);
            list.addView(imageView);
        }
    }

    String imageUrl;

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "")
                .setIcon(R.drawable.done)
                .setOnMenuItemClickListener(item -> {
                    if (editText.getText().toString().length() <= 0) {
                        Utils.ShowSnackBar.show(CreateSurveysObject.this, "Текст вопроса пустой!", list);
                        return false;
                    }
                    Intent data = new Intent();
                    if (checkBox1.isChecked()) {
                        if (editTextSwith.getText().toString().length() == 0 || editText.getText().toString().length() == 0) {
                            Utils.ShowSnackBar.show(CreateSurveysObject.this, "текст пустой", list);
                            return false;
                        }
                        data.putExtra("type", "one_of");
                        data.putExtra("extra", editTextSwith.getText().toString());
                        data.putExtra("q", editText.getText().toString());
                    } else {
                        if (editText.getText().toString().length() == 0) {
                            Utils.ShowSnackBar.show(CreateSurveysObject.this, "текст пустой", list);
                            return false;
                        }
                        data.putExtra("type", "text");
                        data.putExtra("q", editText.getText().toString());
                    }
                    if (imageUrl != null)
                        data.putExtra("image", imageUrl);
                    setResult(1, data);
                    finish();
                    return true;
                })
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(requestCode + " " + requestCode);
        if (data != null) {

            Uri uri = data.getData();
            try {
                Bitmap image = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                API.uploadImageNotResized(new ApiListener() {
                    Dialog d;

                    @Override
                    public void onError(JSONObject json) {
                        System.out.println(json);
                        d.dismiss();
                    }

                    @Override
                    public void inProcess() {
                        d = openWaiter(CreateSurveysObject.this);
                    }

                    @Override
                    public void onSuccess(JSONObject json) throws JSONException {
                        System.out.println(json);
                        imageUrl = json.getString("url");
                        d.dismiss();
                        runOnUiThread(() -> {
                            if (checkBox.isChecked())
                                addText();
                            else
                                addOneOf();
                        });
                    }
                }, image, new CustomString("token", Constants.user.token));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            Utils.ShowSnackBar.show(CreateSurveysObject.this, "Не удалось получить изображение!", list);
        }
    }
}
