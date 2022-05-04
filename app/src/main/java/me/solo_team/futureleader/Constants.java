package me.solo_team.futureleader;

import android.graphics.Bitmap;

import java.util.HashMap;

public class Constants {
    // классы для кешерирования картинок в меню ( что по 100 миллионов раз не зугружать их по новой)
    public static MenuBitMap mv = new MenuBitMap();
    public static AchivementsBitmap ab = new AchivementsBitmap();
    public static ConstantsBitMaps cbm = new ConstantsBitMaps();
    public static class MenuBitMap{
        public HashMap<Integer,Bitmap> bitmaps = new HashMap<>();
    }
    public static class AchivementsBitmap{
        public HashMap<Integer,Bitmap> bitmaps = new HashMap<>();
    }
    public static class ConstantsBitMaps{
        public HashMap<String, Bitmap> sbm = new HashMap<>();
    }
}
