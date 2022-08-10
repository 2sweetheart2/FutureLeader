package me.solo_team.futureleader.dialogs;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.HashMap;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.Chat;
import me.solo_team.futureleader.Objects.ChatMember;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;

public class ChatInfodialog extends AppCompatDialogFragment {

    Chat chat;
    AppCompatActivity activity;

    public ChatInfodialog(Chat chat, AppCompatActivity appCompatActivity) {
        this.chat = chat;
        activity = appCompatActivity;
    }

    private ImageView logo;
    private EditText text;
    private ListView list;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.chat_info_dialog, null);
        builder.setView(view);
        // Остальной код

        logo = view.findViewById(R.id.chat_info_dialog_image);
        text = view.findViewById(R.id.chat_info_dialog_name);
        list = view.findViewById(R.id.chat_info_members);
        Constants.cache.addPhoto(chat.photo, true, logo, activity);
        text.setText(chat.name);
        MemberAdapter adapter = new MemberAdapter(getContext(), getActivity(),chat.ownerId);
        adapter.addAll(chat.members);
        list.setAdapter(adapter);
        logo.setOnClickListener(v -> {
            if (!checkPerm(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) return;
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            //Тип получаемых объектов - image:
            photoPickerIntent.setType("image/*");
            //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
            startActivityForResult(Intent.createChooser(photoPickerIntent, "Выбирите изображение"), 1);
        });
        return builder.create();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && data != null) {
            Uri selectedImage = data.getData();
            try {
                Bitmap image = BitmapFactory.decodeStream(requireActivity().getContentResolver().openInputStream(selectedImage));
                API.updateChatImage(new ApiListener() {
                                        Dialog d;

                                        @Override
                                        public void onError(JSONObject json) throws JSONException {
                                            d.dismiss();
                                        }

                                        @Override
                                        public void inProcess() {
                                            d = openWaiter(getContext());
                                        }

                                        @Override
                                        public void onSuccess(JSONObject json) throws JSONException {
                                            String url = json.getString("url");
                                            Utils.getBitmapFromURL(url, bitmap -> getActivity().runOnUiThread(() -> logo.setImageBitmap(bitmap)));
                                            chat.photo = url;
                                            d.dismiss();
                                        }
                                    },
                        image,
                        new CustomString("token", Constants.user.token),
                        new CustomString("peer_id", String.valueOf(chat.peerId))
                );
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(!text.getText().toString().equals(chat.name)){
            API.changeChatTitle(new ApiListener() {
                @Override
                public void onError(JSONObject json) throws JSONException {

                }

                @Override
                public void inProcess() {

                }

                @Override
                public void onSuccess(JSONObject json) throws JSONException {
                    chat.name = text.getText().toString();
                }
            },
                    new CustomString("token",Constants.user.token),
                    new CustomString("peer_id",String.valueOf(chat.peerId)),
                    new CustomString("title",text.getText().toString())
                    );
        }
    }

    public static class MemberAdapter extends ArrayAdapter<ChatMember> {

        Context context;
        Activity activity;
        int ownerId;
        public MemberAdapter(Context context, Activity activity, int ownerId) {
            super(context, R.layout.member, R.id.member_name);
            this.context = context;
            this.activity = activity;
            this.ownerId = ownerId;
        }

        @SuppressLint("ViewHolder")
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, ViewGroup parent) {

            View v;
            ChatMember member = getItem(position);

            LayoutInflater vi = LayoutInflater.from(context);
            v = vi.inflate(R.layout.member, null);
            if (member != null) {
                if(ownerId==member.userId)
                    v.findViewById(R.id.member_owner).setVisibility(View.VISIBLE);
                ImageView image = v.findViewById(R.id.member_photo);
                Constants.cache.addPhoto(member.profilePicture, true, image, activity);
                ((TextView) v.findViewById(R.id.member_name)).setText(member.firstName + " " + member.lastName);
                if(customUtilBtn!=null)
                    ((ImageButton) v.findViewById(R.id.member_btn)).setImageDrawable(customUtilBtn);
                ((ImageButton) v.findViewById(R.id.member_btn)).setOnClickListener(v1 -> {
                    if(clicks==null)
                        Utils.ShowSnackBar.show(getContext(), "нет всзаимодействий!", v1);
                    else
                        clicks.click(member);
                });


                image.setOnLongClickListener(v1 -> {
                    Utils.ShowSnackBar.show(context, "open profile", v1);
                    return true;
                });
            }
            ViewHeight+=v.getHeight();
            if(oneViewHeight==0)
                oneViewHeight=v.getHeight();
            return v;
        }

        private UtilButtonClick clicks = null;
        private Drawable customUtilBtn = null;

        public void addUtilBtn(UtilButtonClick click){
            clicks=click;
        }

        int count = 0;

        @Override
        public void add(@Nullable ChatMember object) {
            super.add(object);
            count++;
        }

        @Override
        public void addAll(ChatMember... items) {
            super.addAll(items);
            count += items.length;
        }

        @Override
        public void clear() {
            super.clear();
            count=0;
        }

        @Override
        public void remove(@Nullable ChatMember object) {
            super.remove(object);
            count--;
        }

        private int ViewHeight =0;
        private int oneViewHeight = 0;

        public int getViewsHeight(){
            return ViewHeight;
        }
        public int getOneViewHeight(){
            return oneViewHeight;
        }

        @Override
        public int getViewTypeCount() {
            if (count == 0)
                return super.getViewTypeCount();
            else
                return count;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        public void setCustomImageForUtilBtn(Drawable drawable) {
            this.customUtilBtn = drawable;
        }

        public interface UtilButtonClick{
            void click(ChatMember member);
        }
    }
}