package me.solo_team.futureleader.ui.menu.horizontal_menu.messanger;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

public class CurrentChatView2 extends AppCompatActivity {

    EditText text;
    ImageView send;

    TextView chatName;
    TextView chatMembers;
    ImageButton settings;
    ListView list;

    ChatAdapter chatAdapter = null;

    Chat chat;
    FloatingActionButton downBtn;
    ImageView newMessageImage;
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


        setContentView(R.layout.chat_view);
        Constants.chatsCache.currentChatId = chat.peerId;


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
        downBtn = findViewById(R.id.btn_down);
        newMessageImage = findViewById(R.id.new_message_dot);

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
                            for (int i = 0; i < mesages.length(); i++) {
                                Constants.chatsCache.addMessage(chat.peerId, new Message(mesages.getJSONObject(i)));
                            }
                            if (chat.members == null) {
                                Constants.chatsCache.setMembers(chat.peerId, json.getJSONObject("chat").getJSONArray("members"));
                                chat.members = Constants.chatsCache.getChatByPeerId(getIntent().getIntExtra("peerId", -1)).members;
                            }
                            if (chat.members.size() == 0) {
                                Constants.chatsCache.setMembers(chat.peerId, json.getJSONObject("chat").getJSONArray("members"));
                                chat.members = Constants.chatsCache.getChatByPeerId(getIntent().getIntExtra("peerId", -1)).members;
                            }
                            runOnUiThread(() -> render());
                        }
                    },
                new CustomString("token",Constants.user.token),
                new CustomString("peer_id",String.valueOf(chat.peerId))
        );

        chatName.setText(chat.name);

        send.setOnClickListener(v -> {
            System.out.println(text.getText());
            if(text.getText().length()>0)
                sendMessage(text.getText().toString());
            text.setText("");

        });

        settings.setOnClickListener(v -> {
            ChatInfodialog dialog = new ChatInfodialog(chat, CurrentChatView2.this);
            dialog.show(getSupportFragmentManager(), "dialog");
        });

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        downBtn.setOnClickListener(v -> {
            if (onBottom) return;
            list.setSelection(Constants.chatsCache.messages.get(chat.peerId).size());
        });
        View v = findViewById(R.id.huinaaaaa);
        v.getViewTreeObserver().addOnGlobalLayoutListener(
                () -> {
                    Rect r = new Rect();
                    v.getWindowVisibleDisplayFrame(r);
                    int screenHeight = v.getRootView().getHeight();
                    int keypadHeight = screenHeight - r.bottom;
                    if (keypadHeight > screenHeight * 0.15) {
                        if (!isKeyboardShowing) {

                            isKeyboardShowing = true;
                            onKeyboardVisibilityChanged(true);
                        }
                    } else {
                        if (isKeyboardShowing) {
                            isKeyboardShowing = false;
                            onKeyboardVisibilityChanged(false);
                        }
                    }
                });
    }
    boolean isKeyboardShowing = false;
    boolean oldOnBototom = false;


    void onKeyboardVisibilityChanged(boolean opened) {
        System.out.println("KEYBGOARD SHOW " + oldOnBototom);
        if (opened && oldOnBototom) {
            list.setSelection(Constants.chatsCache.messages.get(chat.peerId).size());
            oldOnBototom = false;
        }
    }


    private void addMessage(Message message) {
        boolean onBot = onBottom;
        System.out.println("OB BOT: " + onBot);
        if (chatAdapter != null) {
            chatAdapter.addWithAnimation(message);
        }
        Constants.chatsCache.getChatByPeerId(chat.peerId).lastMessage = message;
        List<Message> mes = Constants.chatsCache.messages.get(chat.peerId);
        mes.add(message);
        Constants.chatsCache.messages.put(chat.peerId,mes);
        chat.lastMessage = message;
        if(onBot)
            list.setSelection(mes.size());
        else
            newMessageImage.setVisibility(View.VISIBLE);
    }

    public void sendMessage(String message) {
        JSONObject jsonInput = new JSONObject();
        try {
            jsonInput.put("message", message);
            jsonInput.put("peer_id", chat.peerId);
            jsonInput.put("token", Constants.user.token);
            jsonInput.put("mobile_token", Constants.user.mobileToken);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        WebScoketClient.mSocket.emit("send_message", jsonInput, emitterCallback);
    }

    public static Ack emitterCallback = args -> {
        System.out.println("EMIT: " + Arrays.toString(args));
    };


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void render() {
        if (disable) return;
        if (chatAdapter == null) {
            double d = list.getWidth() / 1.5;
            chatAdapter = new ChatAdapter(this, chat.isPrivate(), Integer.parseInt(String.valueOf(d).split("\\.")[0]));

        }
        if (chat.isPrivate())
            for (ChatMember chatMember : chat.members) {
                if (chatMember.userId != Constants.user.id) {
                    chatMembers.setText(chatMember.firstName + " " + chatMember.lastName);
                    break;
                }
            }
        else
            chatMembers.setText(chat.members.size() + " участников");
        chatAdapter.addAll(Constants.chatsCache.messages.get(chat.peerId));
        list.setAdapter(chatAdapter);
        list.setOnScrollListener(scrollListener);
    }

    public void updateMessages(List<Message> messageList){
        List<Message> curMes = Constants.chatsCache.messages.get(chat.peerId);
        int pos = messageList.size();
        for(int i = messageList.size()-1;i>=0;i--){
            Message m = messageList.get(i);
            chatAdapter.insert(m,0);
            curMes.add(0,m);
        }
        Constants.chatsCache.messages.put(chat.peerId,curMes);
        list.setSelection(pos);


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
            runOnUiThread(() -> chatName.setText(title));
        };
        chatName.setText(chat.name);
    }

    boolean alreadyAdded = true;
    boolean onBottom = false;
    AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (firstVisibleItem == 0) {
                getNewMessage();
            }
            else
                alreadyAdded=false;
            final int lastItem = firstVisibleItem + visibleItemCount;
            if (lastItem == totalItemCount) {
                oldOnBototom = true;
                if(!onBottom) {
                            downBtn.setVisibility(View.GONE);
                            newMessageImage.setVisibility(View.GONE);
                            onBottom = true;
                        }

            }
            else {
                downBtn.setVisibility(View.VISIBLE);
                onBottom = false;
                oldOnBototom = false;
            }
        }
    };
    boolean last = false;
    private void getNewMessage() {
        int offset = Constants.chatsCache.messages.get(chat.peerId).size();
        System.out.println("OFFSET: "+offset);
        if(!alreadyAdded && !last)
            API.getChhatHistory(new ApiListener() {
                                    Dialog d;

                                    @Override
                                    public void onError(JSONObject json) throws JSONException {
                                        d.dismiss();
                                        createNotification(list, json.getString("message"));
                                    }

                                    @Override
                                    public void inProcess() {
                                        d = openWaiter(CurrentChatView2.this);
                                    }

                                    @Override
                                    public void onSuccess(JSONObject json) throws JSONException {
                                        JSONArray mes = json.getJSONArray("messages");
                                        if(mes.length()!=0) {
                                            List<Message> messages = new ArrayList<>();
                                            for (int i = mes.length()-1; i >=0; i--) {

                                                messages.add(new Message(mes.getJSONObject(i)));
                                            }
                                            runOnUiThread(() -> updateMessages(messages));
                                            alreadyAdded = true;
                                        }
                                        else last = true;
                                        d.dismiss();
                                    }
                                },
                    new CustomString("token", Constants.user.token),
                    new CustomString("offset", offset),
                    new CustomString("count", 25),
                    new CustomString("antichronological","true"),
                    new CustomString("peer_id",String.valueOf(chat.peerId))
            );
    }
}