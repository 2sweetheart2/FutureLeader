package me.solo_team.futureleader.ui.menu.horizontal_menu.messanger;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import io.socket.client.Ack;
import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.API.websocket.WebScoketClient;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.Chat;
import me.solo_team.futureleader.Objects.ChatMember;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.Message;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.dialogs.ChatInfodialog;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class CurrentChatView2 extends AppCompatActivity {

    EditText text;
    ImageView send;

    TextView chatName;
    TextView chatMembers;
    ImageButton settings;
    ListView list;

    ChatAdapter chatAdapter = null;

    Chat chat;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chat = Constants.chatsCache.getChatByPeerId(getIntent().getIntExtra("peerId",-1));
        Constants.chatListeners.messageCallbacks.put(chat.peerId, message -> {
            runOnUiThread(()->addMessage(message));
        });
        Constants.chatListeners.chatTitleChange = title -> {
            chat.name = title;
            runOnUiThread(()->chatName.setText(title));
        };
        if(chat==null) finish();
        assert chat != null;


        setContentView(R.layout.activity_current_chat_view2);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setTitle("");
        setSupportActionBar(toolbar);
        text = findViewById(R.id.chat_view_text);
        text.setFocusable(true);
        send = findViewById(R.id.chat_view_send);
        chatMembers = findViewById(R.id.chat_view_members);
        chatName = findViewById(R.id.chat_view_name);
        settings = findViewById(R.id.chat_view_settings);
        list = findViewById(R.id.chat_view_list);

        API.getChat(new ApiListener() {
            @Override
            public void onError(JSONObject json) throws JSONException {
                System.out.println(json);
                finish();
            }

            @Override
            public void inProcess() {

            }

            @Override
            public void onSuccess(JSONObject json) throws JSONException {
                System.out.println(json);
                JSONArray mesages = json.getJSONArray("messages");
                Constants.chatsCache.messages.remove(chat.peerId);
                for(int i =0;i<mesages.length();i++){
                    Constants.chatsCache.addMessage(chat.peerId,new Message(mesages.getJSONObject(i)));
                }
                runOnUiThread(()->render());
            }
        },
                new CustomString("token",Constants.user.token),
                new CustomString("peer_id",String.valueOf(chat.peerId))
                );

        chatName.setText(chat.name);
        if(chat.isPrivate())
            for(ChatMember chatMember : chat.members)
            {
                if(chatMember.userId!=Constants.user.id)
                {
                    chatMembers.setText(chatMember.firstName+" "+chatMember.lastName);
                    break;
                }
            }
        else
            chatMembers.setText(chat.members.size()+ " участников");
        send.setOnClickListener(v -> {
            System.out.println(text.getText());
            if(text.getText().length()>0)
                sendMessage(text.getText().toString());
            text.setText("");

        });

        settings.setOnClickListener(v -> {
            ChatInfodialog dialog = new ChatInfodialog(chat,CurrentChatView2.this);
            dialog.show(getSupportFragmentManager(),"dialog");
        });

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    private void addMessage(Message message) {
        if(chatAdapter!=null) {
            chatAdapter.add(message);
        }
    }

    public void sendMessage(String message){
        JSONObject jsonInput = new JSONObject();
        try {
            jsonInput.put("message", message);
            jsonInput.put("peer_id", chat.peerId);
            jsonInput.put("token", Constants.user.token);
            jsonInput.put("mobile_token",Constants.user.mobileToken);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        WebScoketClient.mSocket.emit("send_message", jsonInput, emitterCallback);
    }

    public static Ack emitterCallback = args -> {System.out.println("EMIT: "+ Arrays.toString(args));};



    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void render(){
        if(disable)return;
        if(chatAdapter==null) {
            double d = list.getWidth()/1.5;
            chatAdapter = new ChatAdapter(this, chat.isPrivate(), Integer.parseInt(String.valueOf(d).split("\\.")[0]));
        }
        chatAdapter.addAll(Constants.chatsCache.messages.get(chat.peerId));
        list.setAdapter(chatAdapter);
    }

    private boolean disable = false;

    @Override
    protected void onStop() {
        super.onStop();
        Constants.chatListeners.messageCallbacks.remove(chat.peerId);
        Constants.chatListeners.chatTitleChange = null;
        disable = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constants.chatListeners.messageCallbacks.remove(chat.peerId);
        Constants.chatListeners.chatTitleChange = null;
        disable = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Constants.chatListeners.messageCallbacks.put(chat.peerId, this::addMessage);
        Constants.chatListeners.chatTitleChange = title -> {
            chat.name = title;
            runOnUiThread(()->chatName.setText(title));
        };
        chatName.setText(chat.name);
    }
}