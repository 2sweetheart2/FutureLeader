package me.solo_team.futureleader;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.HashMap;

import me.solo_team.futureleader.Objects.User;


public class Constants {
    // TODO: ТУТ ВСЕ КОНСТАНТЫ И НЕ ТОЛЬКО
    public static User user = new User();
    public static MainActivity mainActivity;
    public static Resources res;
    public static CachePhoto cache = new CachePhoto();

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
        public void addPhoto(String url, boolean needRoundCorners, ImageView v, AppCompatActivity c) {
            if (cache.containsKey(v)) {
                c.runOnUiThread(() -> v.setImageBitmap(cache.get(v)));
                return;
            }
            Utils.getBitmapFromURL(url, bitmap -> {
                if (bitmap == null) {
                    c.runOnUiThread(() -> v.setImageBitmap(BitmapFactory.decodeResource(res, R.drawable.resize_300x0)));
                    return;
                }
                if (needRoundCorners) bitmap = Utils.getRoundedCornerBitmap(bitmap, 10);
                cache.put(v, bitmap);
                Bitmap finalBitmap = bitmap;
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
            if(needRoundCorners) bitmap = Utils.getRoundedCornerBitmap(bitmap,10);
            cache.put(v,bitmap);
            v.setImageBitmap(bitmap);
        }

        public interface ImageBitmaps {
            void result(Bitmap bitmap);
        }

    }
}
