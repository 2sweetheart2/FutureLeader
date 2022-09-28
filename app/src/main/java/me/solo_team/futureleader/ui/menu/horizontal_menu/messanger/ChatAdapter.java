package me.solo_team.futureleader.ui.menu.horizontal_menu.messanger;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.Message;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;

public class ChatAdapter extends ArrayAdapter<Message> {

    Context context;
    boolean privateChat;
    int width;

    public ChatAdapter(Context context,  boolean privateChat, int width) {
        super(context, R.layout.obj_message, R.id.obj_message_text);
        this.context = context;
        this.privateChat = privateChat;
        this.width = width;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent) {

        View v;
        Message message = getItem(position);

        LayoutInflater vi = LayoutInflater.from(context);
        if (message.authorId == Constants.user.id)
                v = vi.inflate(R.layout.obj_message_your, null);
        else
                v = vi.inflate(R.layout.obj_message, null);


        if (!privateChat)
            ((TextView) v.findViewById(R.id.obj_message_sender_name)).setText(message.author.firstName + " " + message.author.lastName);
        else
            v.findViewById(R.id.obj_message_sender_name).setVisibility(View.GONE);
        TextView text = v.findViewById(R.id.obj_message_text);
        text.setText(message.text);
        text.setMaxWidth(width);
        v.findViewById(R.id.obj_message_image).setVisibility(View.GONE);


        text.setOnLongClickListener(v1 -> {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("message", text.getText().toString());
            clipboard.setPrimaryClip(clip);
            Utils.ShowSnackBar.show(context,"Текст скопирован!",v1);
            return true;
        });
        if(messageWithAnimation.contains(message)){
            Animation animation;
            if(message.authorId==Constants.user.id){
                animation = AnimationUtils.loadAnimation(context, R.anim.right_to_left);
            }
            else
                animation = AnimationUtils.loadAnimation(context,R.anim.left_ro_right);
            v.startAnimation(animation);
            messageWithAnimation.remove(message);
        }

        return v;
    }

    int count = 0;


    @Override
    public void add(@Nullable Message object) {
        super.add(object);
        count++;
    }

    private List<Message> messageWithAnimation = new ArrayList<>();

    public void addWithAnimation(Message message){
        messageWithAnimation.add(message);
        add(message);
    }

    @Override
    public void addAll(Message... items) {
        super.addAll(items);
        count+=items.length;
    }

    @Override
    public void insert(@Nullable Message object, int index) {
        super.insert(object, index);
        count++;
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
}
