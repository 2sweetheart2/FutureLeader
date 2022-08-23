package me.solo_team.futureleader.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.ChatMember;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.User;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.profile.view_prof.ViewProfile;

public class MemberAdapter extends ArrayAdapter<ChatMember> {

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
            if (ownerId == member.userId)
                v.findViewById(R.id.member_owner).setVisibility(View.VISIBLE);
            ImageView image = v.findViewById(R.id.member_photo);
            Constants.cache.addPhoto(member.profilePicture, image, activity);
            ((TextView) v.findViewById(R.id.member_name)).setText(member.firstName + " " + member.lastName);
            if (customUtilBtn != null)
                ((ImageButton) v.findViewById(R.id.member_btn)).setImageDrawable(customUtilBtn);
            if (!needCustomButton)
                v.findViewById(R.id.member_btn).setVisibility(View.GONE);
            if (clicks != null)
                ((ImageButton) v.findViewById(R.id.member_btn)).setOnClickListener((View.OnClickListener) v12 -> clicks.click(member));
            else
                ((ImageView) v.findViewById(R.id.member_photo)).setOnClickListener(v1 -> {
                    if (!Constants.user.permission.can_get_user) {
                        Utils.ShowSnackBar.show(activity.getApplicationContext(), "отказано в доступе!", v);
                        return;
                    }
                    API.getUser(new ApiListener() {
                        Dialog d;

                        @Override
                        public void onError(JSONObject json) throws JSONException {
                            d.dismiss();
                            createNotification(v, json.getString("message"));
                        }

                        @Override
                        public void inProcess() {
                            d = openWaiter(activity.getApplicationContext());
                        }

                        @Override
                        public void onSuccess(JSONObject json) throws JSONException {
                            User user = new User(json.getJSONObject("user"));
                            d.dismiss();
                            activity.runOnUiThread(() -> {
                                Constants.currentUser = user;
                                Intent intent = new Intent(activity.getApplicationContext(), ViewProfile.class);
                                intent.putExtra("removeSelf", false);
                                activity.startActivity(intent);
                            });
                        }
                    }, new CustomString("token", Constants.user.token), new CustomString("id", String.valueOf(member.userId)));

                });


            image.setOnLongClickListener(v1 -> {
                Utils.ShowSnackBar.show(context, "open profile", v1);
                return true;
            });
        }
        ViewHeight += v.getHeight();
        if (oneViewHeight == 0)
            oneViewHeight = v.getHeight();
        return v;
    }

    public View.OnClickListener onViewClickListener = null;
    private UtilButtonClick clicks = null;
    private Drawable customUtilBtn = null;

    public void addUtilBtn(UtilButtonClick click){
        clicks=click;
    }

    int count = 0;
    public boolean needCustomButton = true;

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


}