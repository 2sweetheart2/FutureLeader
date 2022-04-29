package me.solo_team.futureleader;

import android.graphics.Bitmap;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Constants {
    public static MenuBitMap mv = new MenuBitMap();
    public static AchivementsBitmap ab = new AchivementsBitmap();


    public static class MenuBitMap{
        public HashMap<Integer,Bitmap> bitmaps = new HashMap<>();
    }
    public static class AchivementsBitmap{
        public HashMap<Integer,Bitmap> bitmaps = new HashMap<>();
    }
}
