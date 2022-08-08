package me.solo_team.futureleader.ui.menu.horizontal_menu.messanger;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.imageview.ShapeableImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.Chat;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;
import me.solo_team.futureleader.ui.news.open_news.EditNews;

public class ChatsView extends Her {
    LinearLayout list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Диалоги");
        setContentView(R.layout.chats);
        list = findViewById(R.id.chat_list);

        API.getChats(new ApiListener() {
                         Dialog d;

                         @Override
                         public void onError(JSONObject json) throws JSONException {
                             d.dismiss();
                             finish();
                         }

                         @Override
                         public void inProcess() {
                             d = openWaiter(ChatsView.this);
                         }

                         @Override
                         public void onSuccess(JSONObject json) throws JSONException {
                             JSONArray chats = json.getJSONArray("results");
                             for (int i = 0; i < chats.length(); i++) {
                                 Chat chat = new Chat(chats.getJSONObject(i));
                                 Constants.chatsCache.addChat(chat);

                             }
                             runOnUiThread(() -> render());
                             d.dismiss();
                         }
                     },
                new CustomString("token", Constants.user.token)
        );

        Constants.chatListeners.chatInviteCallback = () -> runOnUiThread(this::render);
    }

    @SuppressLint("SetTextI18n")
    private void render() {
        list.removeAllViews();
        for (Chat chat : Constants.chatsCache.chats) {
            @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.obj_chat, null);
            Utils.getBitmapFromURL(chat.photo, bitmap -> {
                if (bitmap != null) {
                    runOnUiThread(() -> ((ShapeableImageView) view.findViewById(R.id.obj_chat_image)).setImageBitmap(bitmap));
                }
            });
            ((TextView) view.findViewById(R.id.obj_chat_name)).setText(chat.name);
            TextView fromMessage = view.findViewById(R.id.obj_chat_last_message);
            if(chat.isPrivate())
                view.findViewById(R.id.obj_chat_private).setVisibility(View.VISIBLE);
            if(chat.lastMessage!=null)
                if (chat.lastMessage.authorId != Constants.user.id)
                    fromMessage.setText(chat.lastMessage.author.firstName + ": " + chat.lastMessage.text);
                else
                    fromMessage.setText(chat.lastMessage.text);
            view.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), CurrentChatView2.class);
                intent.putExtra("peerId",chat.peerId);
                startActivity(intent);
            });
            list.addView(view);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        render();
        Constants.chatListeners.chatInviteCallback = () -> runOnUiThread(this::render);
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "")
                .setIcon(R.drawable.plus)
                .setOnMenuItemClickListener(item -> {
                            Intent intent = new Intent(this, CreateChat.class);
                            startActivity(intent);
                            return true;
                        })
                        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constants.chatListeners.chatInviteCallback = null;
    }
}
