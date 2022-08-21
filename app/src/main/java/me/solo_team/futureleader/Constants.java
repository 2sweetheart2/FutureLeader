package me.solo_team.futureleader;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    public static class AudioCache {
        public List<View> yourMusicsViews = new ArrayList<>();
        public List<View> popMusicsViews = new ArrayList<>();
        public List<Audio> yourMusics = new ArrayList<>();
        public List<Audio> popMusics = new ArrayList<>();

        public List<Audio> seaechedAudios = new ArrayList<>();

        public Audio curAudio = null;

        List<Audio> currentPlayList = null;

        private int pos = -1;
        public int curList = 0;
        private int oldCurList = -1;

        public void setCurrentAudio(int playList, Audio audio) {
            curList = playList;
            oldCurList = playList;
            checkPlayList();
            pos = currentPlayList.indexOf(audio);
        }

        public Audio next(){
            checkPlayList();
            if(curAudio!=null) return curAudio;
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
            if(curAudio!=null) return curAudio;
            if(playListCanged())
                pos = 0;
            else
                pos--;
            if(pos<0)
                pos = currentPlayList.size()-1;
            return currentPlayList.get(pos);
        }

        public Audio getCurrentAudio() {
            if (curAudio != null)
                return curAudio;
            checkPlayList();
            return currentPlayList.get(pos);
        }

        private void checkPlayList(){
            switch (curList){
                case 1:
                    currentPlayList =  popMusics;
                    break;
                case 0:
                    currentPlayList =  yourMusics;
                    break;
                case 2:
                    currentPlayList = seaechedAudios;
                    break;
            }
        }

        private boolean playListCanged(){
            if(oldCurList==-1){
                oldCurList = curList;
                return false;
            }
            else return oldCurList != curList;
        }

        public boolean checkHas(Audio audio) {
            for(Audio a : yourMusics){
                if(a.url.equals(audio.url))
                    return true;
            }
            return false;
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
        private HashMap<String, Bitmap> UIDcache = new HashMap<>();

        public void addPhoto(String url, ImageView imageView, Activity activity) {
            try {
                if(url==null){
                    activity.runOnUiThread(() -> imageView.setImageResource(R.drawable.resize_300x0));
                    return;
                }
                if (UIDcache.containsKey(url)) {
                    activity.runOnUiThread(() -> imageView.setImageBitmap(UIDcache.get(url)));
                } else {
                    Utils.getBitmapFromURL(url, bitmap -> {
                        if (bitmap == null)
                        {
                            activity.runOnUiThread(() -> imageView.setImageResource(R.drawable.resize_300x0));
                            return;
                        }
                        bitmap = Utils.getRoundedCornerBitmap(bitmap, 25);
                        UIDcache.put(url, bitmap);
                        Bitmap finalBitmap = bitmap;
                        activity.runOnUiThread(() -> imageView.setImageBitmap(finalBitmap));
                    });
                }
            } catch (Exception ignored) {
            }
        }

        public void addPhoto(String url, ImageView imageView, Fragment fragment) {
            try {
                if(url==null){
                    fragment.getActivity().runOnUiThread(() -> imageView.setImageResource(R.drawable.resize_300x0));
                    return;
                }
                if (UIDcache.containsKey(url)) {
                    fragment.getActivity().runOnUiThread(() -> imageView.setImageBitmap(UIDcache.get(url)));
                } else {
                    Utils.getBitmapFromURL(url, bitmap -> {
                        if (bitmap == null)
                        {
                            fragment.getActivity().runOnUiThread(() -> imageView.setImageResource(R.drawable.resize_300x0));
                            return;
                        }
                        bitmap = Utils.getRoundedCornerBitmap(bitmap, 25);
                        UIDcache.put(url, bitmap);
                        Bitmap finalBitmap = bitmap;
                        fragment.getActivity().runOnUiThread(() -> imageView.setImageBitmap(finalBitmap));
                    });
                }
            } catch (Exception ignored) {
            }
        }

        public interface ImageBitmaps {
            void result(Bitmap bitmap);
        }

    }


}
