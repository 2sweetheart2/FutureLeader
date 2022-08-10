package me.solo_team.futureleader;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.material.imageview.ShapeableImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.solo_team.futureleader.Objects.Audio;
import me.solo_team.futureleader.Objects.Chat;
import me.solo_team.futureleader.Objects.Message;
import me.solo_team.futureleader.Objects.ShopItem;
import me.solo_team.futureleader.Objects.Surveys;
import me.solo_team.futureleader.Objects.User;
import me.solo_team.futureleader.stuff.Utils;


public class Constants {
    // TODO: ТУТ ВСЕ КОНСТАНТЫ И НЕ ТОЛЬКО
    public static User user;
    public static User currentUser;
    public static NewsCache newsCache = new NewsCache();
    public static MainActivity mainActivity;
    public static Resources res;
    public static CachePhoto cache = new CachePhoto();

    public static ShopChache shopChache = new ShopChache();

    public static ExoPlayer exoPlayer;

    public static SurveysCache surveysCache = new SurveysCache();

    public static AudioCache audioCache = new AudioCache();
    public static Context MainContext;

    public static class ShopChache{
        public List<ShopItem> corzina = new ArrayList<>();
    }

    public static ChatsCache chatsCache = new ChatsCache();

    public static ChatListeners chatListeners = new ChatListeners();

    public static class ChatListeners{
        public interface messageCallbakcEmiter{
            public void onMessage(Message message);
        }

        public interface ChatInvateCallbakc{
            void chatInvite();
        }

        public interface ChatTitleChange{
            void titleChange(String title);
        }

        public HashMap<Integer,messageCallbakcEmiter> messageCallbacks = new HashMap<>();

        public ChatTitleChange chatTitleChange = null;

        public ChatInvateCallbakc chatInviteCallback = null;
    }

    public static class ChatsCache{
        public List<Chat> chats = new ArrayList<>();
        public HashMap<Integer, List<Message>> messages = new HashMap<>();

        public void addMessage(int peerId, Message message){
            if(messages.containsKey(peerId)) {
                List<Message> messageList = messages.get(peerId);
                messageList.add(0,message);
                messages.put(peerId,messageList);
            }
            else{
                List<Message> messages1 = new ArrayList<>();
                messages1.add(message);
                this.messages.put(message.peerId,messages1);
            }
        }

        public void addChat(Chat chat){
            boolean has = false;
            for(Chat chat1 : chats){
                if(chat1.peerId == chat.peerId) {
                    has = true;
                    break;
                }
            }
            if(!has)
                chats.add(chat);
        }

        public Chat getChatByPeerId(int peerId){
            for(Chat chat : chats){
                if(chat.peerId == peerId)
                    return chat;
            }
            return null;
        }
    }

    public static SocketCache socketCache = new SocketCache();

    public static class SocketCache{
        public List<Chat> invitedChat = new ArrayList<>();
        public List<Message> newMessages = new ArrayList<>();
    }

    public static class AudioCache{
        public List<View> yourMusicsViews = new ArrayList<>();
        public List<View> popMusicsViews = new ArrayList<>();
        public List<Audio> yourMusics = new ArrayList<>();
        public List<Audio> popMusics = new ArrayList<>();

        List<Audio> currentPlayList;

        private int pos = -1;
        public int curList = 0;
        private int oldCurList = -1;

        public void setCurrentAudio(int playList, Audio audio){
            curList = playList;
            oldCurList = playList;
            checkPlayList();
            pos = currentPlayList.indexOf(audio);
        }

        public Audio next(){
            checkPlayList();
            if(playListCanged())
                pos = 0;
            else
                pos++;
            if(currentPlayList.size()==pos)
                pos=0;
            return currentPlayList.get(pos);
        }

        public Audio previous(){
            checkPlayList();
            if(playListCanged())
                pos = 0;
            else
                pos--;
            if(pos<0)
                pos = currentPlayList.size()-1;
            return currentPlayList.get(pos);
        }

        public Audio getCurrentAudio(){
            checkPlayList();
            return currentPlayList.get(pos);
        }

        private void checkPlayList(){
            switch (curList){
                case 1:
                    currentPlayList =  popMusics;
                case 0:
                    currentPlayList =  yourMusics;
            }
        }

        private boolean playListCanged(){
            if(oldCurList==-1){
                oldCurList = curList;
                return false;
            }
            else return oldCurList != curList;
        }

    }

    public static class SurveysCache{
        public List<Surveys> surveysForUser;
        public List<Surveys>  surveysForAll;
        public List<Surveys> completeSurveys;
        public List<Surveys> allSurveys = new ArrayList<>();
        public Surveys currentSurvey;

        public Surveys getMeById(int id){
            for(Surveys sur : surveysForUser){
                if(sur.id==id)
                    return sur;
            }
            return null;
        }

        public Surveys getAllById(int id){
            for(Surveys sur : surveysForAll){
                if(sur.id==id)
                    return sur;
            }
            return null;
        }

        public Surveys getAllByIdAdmin(int id){
            for(Surveys sur : allSurveys){
                if(sur.id==id){
                    return sur;
                }
            }
            return null;
        }

        public boolean checkComplete(Surveys surveys){
            for(Surveys s : completeSurveys){
                if(s.id==surveys.id)
                    return true;
            }
            return false;
        }

        public Surveys getCompleteById(int id){
            for(Surveys s : completeSurveys){
                if(s.id == id)
                    return s;
            }
            return null;
        }

        public void addCompleteFromMe(int id){
            for(Surveys sur : surveysForUser){
                if(sur.id==id){
                    completeSurveys.add(sur);
                    surveysForUser.remove(sur);
                    break;
                }
            }
        }

        public void addCompleteFromAll(int id){
            for(Surveys sur : surveysForAll){
                if(sur.id==id){
                    completeSurveys.add(sur);
                    surveysForAll.remove(sur);
                    break;
                }
            }
        }

        public void deleteSurvey(int id) {
            allSurveys.remove(getAllByIdAdmin(id));
        }
    }


    public static class NewsCache {
        public JSONArray news = new JSONArray();
        public JSONObject curentNew;

        public void updObjects() {
            try {
                for (int i = 0; i < news.length(); i++) {
                    if (news.getJSONObject(i).getInt("id") == curentNew.getInt("id")) {
                        news.getJSONObject(i).put("objects", curentNew.getJSONArray("objects"));
                        return;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static class CachePhoto {
        private HashMap<ImageView, Bitmap> cache = new HashMap<>();
        /**
         * Динамическое получени фото по URL  и кешерировани его в HashMap по URL и подстваление в {@link ImageView}
         *
         * @param url              ссылка на фото, тип: {@link String}
         * @param needRoundCorners нужно ли закруглять фото, тип: {@link Boolean}
         * @param v                {@link ImageView}
         * @param c                {@link AppCompatActivity} для {@link AppCompatActivity#runOnUiThread}
         */
        public void addPhoto(String url, boolean needRoundCorners, ImageView v, Activity c) {
            if (cache.containsKey(v)) {
                c.runOnUiThread(() -> v.setImageBitmap(cache.get(v)));
                return;
            }
            Utils.getBitmapFromURL(url, bitmap -> {
                if (bitmap == null) {
                    c.runOnUiThread(() -> v.setImageBitmap(BitmapFactory.decodeResource(res, R.drawable.resize_300x0)));
                    return;
                }
                Bitmap bitmap_ = bitmap;
                if (needRoundCorners) bitmap_ = Utils.getRoundedCornerBitmap(bitmap, 25);
                cache.put(v, bitmap_);
                Bitmap finalBitmap = bitmap_;
                c.runOnUiThread(() -> v.setImageBitmap(finalBitmap));
            });
        }

        /**
         * Динамическое получени фото по URL  и кешерировани его в HashMap по URL и подстваление в {@link ImageView}
         *
         * @param url              ссылка на фото, тип: {@link String}
         * @param needRoundCorners нужно ли закруглять фото, тип: {@link Boolean}
         * @param v                {@link ImageView}
         * @param c                {@link Fragment} для {@link Fragment#requireActivity}
         */
        public void addPhoto(String url, boolean needRoundCorners, ImageView v, Fragment c) {
            if (cache.containsKey(v)) {
                c.requireActivity().runOnUiThread(() -> v.setImageBitmap(cache.get(v)));
                return;
            }
            Utils.getBitmapFromURL(url, bitmap -> {
                if (bitmap == null) {
                    c.requireActivity().runOnUiThread(() -> v.setImageBitmap(BitmapFactory.decodeResource(res, R.drawable.resize_300x0)));
                    return;
                }
                if (needRoundCorners) bitmap = Utils.getRoundedCornerBitmap(bitmap, 10);
                cache.put(v, bitmap);
                Bitmap finalBitmap = bitmap;
                c.requireActivity().runOnUiThread(() -> v.setImageBitmap(finalBitmap));
            });
        }

        public Bitmap getPhoto(ImageView v) {
            if (!cache.containsKey(v))
                return BitmapFactory.decodeResource(res, R.drawable.resize_300x0);
            return cache.get(v);
        }

        public void addPhoto(Bitmap bitmap, boolean needRoundCorners, ImageView v) {
            if (needRoundCorners) bitmap = Utils.getRoundedCornerBitmap(bitmap, 10);
            cache.put(v, bitmap);
            v.setImageBitmap(bitmap);
        }

        public void addPhoto(String profilePicture, ShapeableImageView viewById,Activity c) {
            Utils.getBitmapFromURL(profilePicture, bitmap -> {
                if (bitmap == null) {
                    c.runOnUiThread(() -> viewById.setImageBitmap(BitmapFactory.decodeResource(res, R.drawable.resize_300x0)));
                    return;
                }
                c.runOnUiThread(() -> viewById.setImageBitmap(bitmap));
            });
        }

        public interface ImageBitmaps {
            void result(Bitmap bitmap);
        }

    }


}
