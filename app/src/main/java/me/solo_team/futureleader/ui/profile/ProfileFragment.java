package me.solo_team.futureleader.ui.profile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.API.FullApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.Achievement;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.dialogs.EditFieldsDialog;

public class ProfileFragment extends Fragment {

    private ImageView picture;
    private TextView name;
    private TextView description;
    TableLayout tableLayout;
    ProfileInfoGrid grid;
    List<String> notAddedItems = new ArrayList<>();
    Button button;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        requireActivity().invalidateOptionsMenu();
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        tableLayout = root.findViewById(R.id.profile_table_layout);
        grid = new ProfileInfoGrid(tableLayout, root.getContext(), inflater, container);
        RecyclerView recyclerView = root.findViewById(R.id.profile_achievement);
        picture = root.findViewById(R.id.profile_picture);
        name = root.findViewById(R.id.profile_name);
        description = root.findViewById(R.id.profile_description);
        button = root.findViewById(R.id.profile_add_field_btn);

        String imageGledUrl = "https://sun9-63.userapi.com/s/v1/if2/ndSILCx38FMurteLrqMWT9JT5SsHSc7aUxRmX1_0kX2rb_MikptV6bznqP2Z3qU060QDdUZaPVkB6RpLXoIfeHX6.jpg?size=300x400&quality=96&type=album";
        name.setText("Глеб Росин");
        description.setText("Aut viam inveniam, aut faciam.");

        picture.requestLayout();
        updateGrid(grid);
        if(notAddedItems.size()==0)
            button.setVisibility(View.GONE);




        String url = "https://futureleaders.hrbox.io/file/resize/400x400/5b2cb39e-f75a-4a81-b3a2-4c9efd8c892f.png";
        Achievement ach = new Achievement();
        ach.coins = 15;
        ach.description = "Награда самому активному участнику по мнению спикера образовательного мероприятия!";
        ach.name = "Активное участие на мероприятии";
        ach.image_url = url;
        ach.id = 0;
        RecycleAchivementsAdapter adapter = new RecycleAchivementsAdapter(this,
                Collections.singletonList(ach)
        );

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkPerm(getContext(),Manifest.permission.READ_EXTERNAL_STORAGE)) return;
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                //Тип получаемых объектов - image:
                photoPickerIntent.setType("image/*");
                //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
                startActivityForResult(Intent.createChooser(photoPickerIntent, "Выбирите изображение"), 1);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        /**
         * тут крч в мы создаём новый прослушиватель евента, и когда мы отправляем запрос, то прослушиваем ответ который прийдёт
         * если в ответе SUCCESS==false, то сработает метод onError в котором будет response
         * если же иначе SUCCESS==true, то сработает метод onSuccess в котором будет response
         */
        API.loginUser(new ApiListener() {
            @Override
            public void onError(JSONObject json) {
                try {
                    this.createNotification(ProfileFragment.this.getView(), json.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(JSONObject json) {

            }
        }, new CustomString("test", "test"));

        API.getNews(new FullApiListener() {
            @Override
            public void inProgress() {
                System.out.println("[GET NEWS] -> IN PROGRESS");
            }

            @Override
            public void onFinish() {
                System.out.println("[GET NEWS] -> FINISHED...GETTING RESULT");
            }

            @Override
            public void onError(JSONObject json) {
                System.out.println("[GET NEWS] -> ERROR");
            }

            @Override
            public void onSuccess(JSONObject json) {
                System.out.println("[GET NEWS] -> SUCCESS");
            }
        }, new CustomString("test", "test"));
        button.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(),AddFieldLayout.class);
            intent.putExtra("values",String.join(",",notAddedItems));
            startActivity(intent);
        });
        return root;
    }

    private void updateGrid(ProfileInfoGrid grid) {
        tableLayout.removeAllViews();
        notAddedItems.clear();
        for(Map.Entry<String, String> s: Constants.user.enums.entrySet()){
            try {
                if(!Constants.user.info_fields.has(s.getValue()) && !s.getKey().contains("line")){
                    notAddedItems.add(s.getKey());
                    continue;
                }
                if(s.getKey().contains("line")){
                    String name = s.getValue();
                    if(name==null) grid.addRow();
                    else grid.addRow(name);
                }
                else {
                    String value = s.getValue();

                    if(!Constants.user.editedFieldsTypes.containsKey(s.getKey()))
                        grid.addElement(s.getKey().substring(0, 1).toUpperCase() + s.getKey().substring(1), Constants.user.info_fields.getString(value));
                    else
                        grid.addElement(s.getKey().substring(0, 1).toUpperCase() + s.getKey().substring(1), Constants.user.info_fields.getString(value))
                                .setOnLongClickListener(v -> cr(s.getKey(),Constants.user.editedFieldsTypes.get(s.getKey())));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(notAddedItems.size()==0)
            button.setVisibility(View.GONE);
        else
            button.setVisibility(View.VISIBLE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && data!=null){
            Uri selectedImage = data.getData();
            try {
                Bitmap image = BitmapFactory.decodeStream(requireActivity().getContentResolver().openInputStream(selectedImage));
                picture.setImageBitmap(image);
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

    private boolean cr(String value, String type){
        EditFieldsDialog cl = new EditFieldsDialog(result -> {
            if(!result) return;
            Intent intent = new Intent(requireContext(),EditFieldsLayout.class);
            intent.putExtra("type",type);
            intent.putExtra("name",value);
            startActivity(intent);
        },value,null);
        cl.show(getFragmentManager(),"myDialog");
        return true;
    }

    public boolean checkPerm(Context context, String permission){
        int permissionStatus = ContextCompat.checkSelfPermission(context, permission);

        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
            return false;
        }
    }



}