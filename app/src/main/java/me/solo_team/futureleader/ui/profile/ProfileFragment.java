package me.solo_team.futureleader.ui.profile;

import android.Manifest;
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
import java.util.Map;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.FullApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.Utils;
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
        TextView achivement_btn = root.findViewById(R.id.achiviment_btn);
        picture = root.findViewById(R.id.profile_picture);
        name = root.findViewById(R.id.profile_name);
        description = root.findViewById(R.id.profile_description);
        button = root.findViewById(R.id.profile_add_field_btn);

        name.setText(Constants.user.firstName + " " + Constants.user.lastName);
        description.setText(Constants.user.status);
        Constants.cache.addPhoto(Constants.user.profilePictureLink, true, picture, this);

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
            List<String> arr = new ArrayList<>();
            for(String s : notAddedItems.keySet()){
                if(Constants.user.editedFieldsTypes.containsKey(s))
                    arr.add(s);
            }
            intent.putExtra("values", String.join(",", arr));
            startActivity(intent);
        });
        return root;
    }

    private void updateGrid(ProfileInfoGrid grid) {
        tableLayout.removeAllViews();
        notAddedItems.clear();
        LinkedHashMap<String, Integer> rows = new LinkedHashMap<>();
        int index = 0;
        for (Map.Entry<String, String> s : Constants.user.enums.entrySet()) {
            String key = s.getKey();
            String value = s.getValue();
            if (key.contains("line")) {
                if (value == null) grid.addRow();
                else grid.addRow(value);
                rows.put(s.getKey(), index);
                index++;
                continue;
            }
            if (!Constants.user.user_fields.has(value)) {
                notAddedItems.put(s.getKey(), s.getValue());
                continue;
            }
            try {
                String name = key.substring(0, 1).toUpperCase() + key.substring(1);
                if (!Constants.user.editedFieldsTypes.containsKey(key))
                    grid.addElement(name, Utils.parseDateBirthday(Constants.user.user_fields.getString(value)));
                else
                    grid.addElement(name, Constants.user.user_fields.getString(value))
                            .setOnLongClickListener(v -> cr(key, Constants.user.editedFieldsTypes.get(key)));
                index++;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        if (notAddedItems.size() == 0)
            button.setVisibility(View.GONE);
        else
            button.setVisibility(View.VISIBLE);
        List<String> val = new ArrayList<>(Constants.user.enums.keySet());
        boolean removeFirstLine = true;
        boolean removeSecondLine = true;
        for(int i=val.indexOf("line")+1;i<val.indexOf("line1");i++){
            if(!notAddedItems.containsKey(val.get(i))) removeFirstLine=false;
        }
        for(int i=val.indexOf("line1")+1;i<val.size();i++){
            if(!notAddedItems.containsKey(val.get(i))) removeSecondLine=false;
        }
        if(removeFirstLine) tableLayout.removeViewAt(rows.get("line"));
        if(removeSecondLine) tableLayout.removeViewAt(rows.get("line1")-1);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && data != null) {
            Uri selectedImage = data.getData();
            try {
                Bitmap image = BitmapFactory.decodeStream(requireActivity().getContentResolver().openInputStream(selectedImage));
                //picture.setImageBitmap(image);
                API.uploadProfilePicture(new FullApiListener() {
                    @Override
                    public void inProgress() {

                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void onError(JSONObject json) {

                    }

                    @Override
                    public void onSuccess(JSONObject json) {
                        try {
                            Constants.cache.addPhoto(json.getString("url"), true, picture, ProfileFragment.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, image, new CustomString("token", Constants.user.token));
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