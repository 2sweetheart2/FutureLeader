package me.solo_team.futureleader.ui.profile;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.Achievement;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.Field;
import me.solo_team.futureleader.Objects.Postal;
import me.solo_team.futureleader.Objects.User;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.dialogs.EditFieldsDialog;
import me.solo_team.futureleader.ui.EditStatus;
import me.solo_team.futureleader.ui.menu.statical.admining.layouts.moderation.Nastavniki;
import me.solo_team.futureleader.ui.menu.statical.dr.DrView;
import me.solo_team.futureleader.ui.profile.view_prof.ViewProfile;

public class ProfileFragment extends Fragment {

    public ImageView picture;
    private TextView name;
    private TextView mentors;
    public TextView description;
    TableLayout tableLayout;
    ProfileInfoGrid grid;
    LinkedHashMap<String, String> notAddedItems = new LinkedHashMap<>();
    Button button;
    Button postals;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        requireActivity().invalidateOptionsMenu();
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        root.findViewById(R.id.profile_open_chat).setVisibility(View.GONE);
        postals = root.findViewById(R.id.postals_btn);
        tableLayout = root.findViewById(R.id.profile_table_layout);
        grid = new ProfileInfoGrid(tableLayout, root.getContext(), inflater, container);
        Button achivement_btn = root.findViewById(R.id.achiviment_btn);
        picture = root.findViewById(R.id.profile_picture);
        mentors = root.findViewById(R.id.profile_mentors);
        name = root.findViewById(R.id.profile_name);
        description = root.findViewById(R.id.profile_description);
        button = root.findViewById(R.id.profile_add_field_btn);
        Constants.currentUser = Constants.user;

        if(Constants.user.mentors.size()!=0){
            if(Constants.user.mentors.size()>1) {
                mentors.setText("Наставники: " + Constants.user.mentors.get(0).getFullName() + "...");
            }
            else{
                mentors.setText("Наставник: " + Constants.user.mentors.get(0).getFullName());
                mentors.setOnClickListener(v -> {
                    API.getUser(new ApiListener() {
                        Dialog d;

                        @Override
                        public void onError(JSONObject json) throws JSONException {
                            d.dismiss();
                            createNotification(v, json.getString("message"));
                        }

                        @Override
                        public void inProcess() {
                            d = openWaiter(requireContext());
                        }

                        @Override
                        public void onSuccess(JSONObject json) throws JSONException {
                            User user = new User(json.getJSONObject("user"));
                            d.dismiss();
                            requireActivity().runOnUiThread(() -> {
                                Constants.currentUser = user;
                                Intent intent = new Intent(requireContext(), ViewProfile.class);
                                intent.putExtra("removeSelf", false);
                                startActivity(intent);
                            });
                        }
                    }, new CustomString("token", Constants.user.token), new CustomString("id", String.valueOf(Constants.user.mentors.get(0).userId)));
                });
            }
        }else mentors.setVisibility(View.GONE);

        postals.setOnClickListener(v -> {
            API.getPostals(new ApiListener() {
                Dialog d;
                @Override
                public void onError(JSONObject json) throws JSONException {
                    d.dismiss();
                    createNotification(picture,json.getString("message"));
                }

                @Override
                public void inProcess() {
                    d = openWaiter(requireContext());
                }

                @Override
                public void onSuccess(JSONObject json) throws JSONException {
                    JSONArray post = json.getJSONArray("postals");
                    List<Postal> postals = new ArrayList<>();
                    for(int i=0;i<post.length();i++){
                        postals.add(new Postal(post.getJSONObject(i)));
                    }

                    requireActivity().runOnUiThread(()->{
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(requireContext());
                        View view = getLayoutInflater().inflate(R.layout.profile_alert_dialog_list, null);
                        builder.setView(view);
                        RecyclerView recyclerView = view.findViewById(R.id.achivement_list);

                        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                        RecycleAdapter2 adapter = new RecycleAdapter2(requireActivity().getSupportFragmentManager(), null,postals, requireActivity());
                        recyclerView.setAdapter(adapter);
                        builder.show();
                    });
                    d.dismiss();
                }
            },new CustomString("token",Constants.user.token),new CustomString("user_id",Constants.user.id));
        });

        description.setOnClickListener(v -> {
            AlertDialog.Builder obj = new AlertDialog.Builder(requireContext());
            obj.setTitle("изменить статус?" );
            obj.setIcon(R.drawable.resize_300x0);
            obj.setPositiveButton("да", (dialog, which) -> {
                Intent intent = new Intent(requireContext(), EditStatus.class);
                intent.putExtra("title","Изменение статуса");
                intent.putExtra("hint","текст...");
                startActivityForResult(intent,100);
            });
            obj.setNegativeButton("нет", null);
            obj.show();
        });
        name.setText(Constants.currentUser.firstName + " " + Constants.currentUser.lastName);
        description.setText(Constants.currentUser.status);
        if(Constants.currentUser.status.length()==1 && Constants.currentUser.status.equals(" "))
        {
            description.setText("*установить статус*");
            description.setTextColor(Color.GRAY);
            description.setTypeface(null, Typeface.ITALIC);
        }
        Constants.cache.addPhoto(Constants.currentUser.profilePictureLink, picture, this);

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
            startActivityForResult(Intent.createChooser(photoPickerIntent, "Выберите изображение"), 1);
        });



        button.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), AddFieldLayout.class);
            startActivity(intent);
        });
        achivement_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                API.getAchievements(new ApiListener() {
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
                            Constants.currentUser.achievements.clear();
                            JSONArray ar = json.getJSONArray("achievement");
                            for(int i=0;i<ar.length();i++){
                                Constants.currentUser.achievements.add(new Achievement(ar.getJSONObject(i),false));
                            }
                            System.out.println(json);
                            requireActivity().runOnUiThread(()->{
                            AlertAchivementListDialog alertAchivementListDialog = new AlertAchivementListDialog(ProfileFragment.this.getParentFragmentManager(),requireContext(),ProfileFragment.this,getLayoutInflater());
                            alertAchivementListDialog.show(getParentFragmentManager(), null);
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new CustomString("token", Constants.user.token),new CustomString("user_id",String.valueOf(Constants.user.id)));


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
        if(requestCode==100 && resultCode==1){
            String text = data.getStringExtra("text");
            API.setStatus(new ApiListener() {
                Dialog d;
                @Override
                public void onError(JSONObject json) throws JSONException {
                    d.dismiss();
                    createNotification(picture,json.getString("message"));
                }

                @Override
                public void inProcess() {
                    d = openWaiter(requireContext());
                }

                @Override
                public void onSuccess(JSONObject json) throws JSONException {
                    d.dismiss();
                    requireActivity().runOnUiThread(()->{
                        description.setText(text);
                        Constants.user.status = text;
                        Constants.currentUser.status = text;
                    });
                }
            },new CustomString("token",Constants.user.token), new CustomString("status",text));
        }
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
                            Constants.cache.addPhoto(json.getString("url"), picture, ProfileFragment.this);
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
        Constants.currentUser = Constants.user;
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