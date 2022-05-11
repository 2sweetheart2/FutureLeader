package me.solo_team.futureleader;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;

public class Constants {
    // классы для кешерирования картинок в меню ( что по 100 миллионов раз не зугружать их по новой)
    public static Resources res;
    public static CachePhoto cache = new CachePhoto();

    public static class CachePhoto {
        private HashMap<ImageView, Bitmap> cache = new HashMap<>();

        public void addPhoto(String url,boolean needRoundCorners, ImageView v, AppCompatActivity c) {
            if(cache.containsKey(v)) {
                c.runOnUiThread(() -> v.setImageBitmap(cache.get(v)));
                return;
            }
            Utils.getBitmapFromURL(url, bitmap -> {
                if(bitmap==null){
                    c.runOnUiThread(()->v.setImageBitmap(BitmapFactory.decodeResource(res, R.drawable.resize_300x0)));
                    return;
                }
                if(needRoundCorners) bitmap = Utils.getRoundedCornerBitmap(bitmap,10);
                cache.put(v, bitmap);
                Bitmap finalBitmap = bitmap;
                c.runOnUiThread(() -> v.setImageBitmap(finalBitmap));
            });
        }

        public void addPhoto(String url,boolean needRoundCorners, ImageView v, Fragment c) {
            if(cache.containsKey(v)) {
                c.requireActivity().runOnUiThread(() -> v.setImageBitmap(cache.get(v)));
                return;
            }
            Utils.getBitmapFromURL(url, bitmap -> {
                if(bitmap==null){
                    c.requireActivity().runOnUiThread(()->v.setImageBitmap(BitmapFactory.decodeResource(res, R.drawable.resize_300x0)));
                    return;
                }
                if(needRoundCorners) bitmap = Utils.getRoundedCornerBitmap(bitmap,10);
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


        public interface ImageBitmaps{
            void result(Bitmap bitmap);
        }

    }
}
