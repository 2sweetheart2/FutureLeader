package me.solo_team.futureleader.ui.profile;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.Field;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.dialogs.EditFieldsDialog;

public class ProfileFragment extends Fragment {

    private ImageView picture;
    private TextView name;
    private TextView description;
    TableLayout tableLayout;
    ProfileInfoGrid grid;
    LinkedHashMap<String, String> notAddedItems = new LinkedHashMap<>();
    Button button;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        requireActivity().invalidateOptionsMenu();
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        tableLayout = root.findViewById(R.id.profile_table_layout);
        grid = new ProfileInfoGrid(tableLayout, root.getContext(), inflater, container);
        Button achivement_btn = root.findViewById(R.id.achiviment_btn);
        picture = root.findViewById(R.id.profile_picture);
        name = root.findViewById(R.id.profile_name);
        description = root.findViewById(R.id.profile_description);
        button = root.findViewById(R.id.profile_add_field_btn);

        Constants.currentUser = Constants.user;


        name.setText(Constants.currentUser.firstName + " " + Constants.currentUser.lastName);
        description.setText(Constants.currentUser.status);
        Constants.cache.addPhoto(Constants.currentUser.profilePictureLink, true, picture, this);

        picture.requestLayout();
        updateGrid(grid);
        if (notAddedItems.size() == 0)
            button.setVisibility(View.GONE);

        picture.setOnClickListener(v -> {
            if (!checkPerm(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) return;
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            //Тип получаемых объектов - image:
            photoPickerIntent.setType("image/*");
            //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
            startActivityForResult(Intent.createChooser(photoPickerIntent, "Выбирите изображение"), 1);
        });


        button.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), AddFieldLayout.class);
            startActivity(intent);
        });
        achivement_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                List<Integer> ids = new ArrayList<>();
                for (String s : Constants.currentUser.achievementsIds.split(",")) {
                    ids.add(Integer.parseInt(s));
                }
                API.getAchivement(new ApiListener() {
                    Dialog d;

                    @Override
                    public void onError(JSONObject json) {
                        try {
                            this.createNotification(getView(), json.getString("message"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void inProcess() {
                        d = this.openWaiter(requireContext());
                    }

                    @Override
                    public void onSuccess(JSONObject json) {
                        try {
                            d.dismiss();
                            Constants.currentUser.achievements = json.getJSONArray("achievements");
                            AlertAchivementListDialog alertAchivementListDialog = new AlertAchivementListDialog();
                            alertAchivementListDialog.show(getParentFragmentManager(), null);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new CustomString("ids", Constants.currentUser.achievementsIds), new CustomString("token", Constants.user.token));


            }
        });
        return root;
    }

    private void updateGrid(ProfileInfoGrid grid) {
        tableLayout.removeAllViews();
        notAddedItems.clear();
        List<Field> fields = Constants.user.fields;
        for (Field field : fields) {
            if (field.name.equals("line")) {
                if (field.visualName.length() == 0)
                    grid.addRow();
                else grid.addRow(field.visualName);
                continue;
            }
            String name = field.visualName.substring(0, 1).toUpperCase() + field.visualName.substring(1);
            if (field.name.equals("birthday"))
                grid.addElement(name, Utils.parseDateBirthday(field.value));
            else {
                View v = grid.addElement(name, field.value);
                if(field.canEdit)
                    v.setOnLongClickListener(v_ -> cr(field.visualName, field.value));
            }
        }
        if(Constants.user.fieldsStuff.maxSize!=fields.size()){
            button.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && data != null) {
            Uri selectedImage = data.getData();
            try {
                Bitmap image = BitmapFactory.decodeStream(requireActivity().getContentResolver().openInputStream(selectedImage));
                //picture.setImageBitmap(image);
                API.uploadProfilePicture(new ApiListener() {
                    Dialog d;

                    @Override
                    public void onError(JSONObject json) {

                    }

                    @Override
                    public void inProcess() {
                        d = openWaiter(requireContext());
                    }

                    @Override
                    public void onSuccess(JSONObject json) {
                        try {
                            d.dismiss();
                            Constants.currentUser.profilePictureLink = json.getString("url");
                            Constants.cache.addPhoto(json.getString("url"), true, picture, ProfileFragment.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, image, new CustomString("token", Constants.user.token), new CustomString("current_id", String.valueOf(Constants.currentUser.id)));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        updateGrid(grid);
    }

    private boolean cr(String value, String type) {
        EditFieldsDialog cl = new EditFieldsDialog(result -> {
            if (!result) return;
            Intent intent = new Intent(requireContext(), EditFieldsLayout.class);
            intent.putExtra("type", type);
            intent.putExtra("name", value);
            startActivity(intent);
        }, value, null);
        cl.show(getFragmentManager(), "myDialog");
        return true;
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


}