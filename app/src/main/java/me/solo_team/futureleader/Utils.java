package me.solo_team.futureleader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Utils {

    // пост запрос для получения картинки и вывода её в Bitmap через interface(callback)
    // зачем интерфейс? а потому что мы не можем отправлять запросы из основного потока,
    // а поскольку не можем отправлять из основного потока, то создаём новый,
    // и вот как раз делать return и переменная ответа не получиться, так как мы в другом потоке
    // а через interface, после окончания получения, у нас он вернётся
    public static void getBitmapFromURL(String src, CallBackBitMap callback) {
        new Thread(() -> {
            try {

                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                callback.process(myBitmap);

            } catch (IOException e) {

                callback.process(null);
            }
        }).start();
    }

    // округление картинки в битмап
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        if (bitmap == null) return null;
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static void resizeItemInGridLayout(GridLayout gridLayout, AppCompatActivity activity, List<String> links) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        int halfScreenWidth = (int) (size.x * 0.5) - 20;
        for (int i = 0; i < gridLayout.getChildCount(); i++) {

            View v = gridLayout.getChildAt(i);
            gridLayout.removeView(v);
            v.getLayoutParams().height = halfScreenWidth;
            if (v.getTag() != null) {
                v.getLayoutParams().width = halfScreenWidth * 2 + 5;
                v.getLayoutParams().height = halfScreenWidth;
                GridLayout.LayoutParams lp = new GridLayout.LayoutParams(GridLayout.spec(i / 2), GridLayout.spec(0, 2));
                gridLayout.addView(v, i, lp);
            } else {
                v.getLayoutParams().width = halfScreenWidth;
                gridLayout.addView(v, i);
            }
            int finalI = i;
            ((ImageView) gridLayout.getChildAt(finalI)).setImageDrawable(activity.getDrawable(R.drawable.gray_gradient_with_corners));
            Utils.getBitmapFromURL(links.get(i), bitmap -> {
                if (bitmap != null) {
                    activity.runOnUiThread(() -> ((ImageView) gridLayout.getChildAt(finalI)).setImageBitmap(bitmap));
                }
            });

        }
    }

    public interface CallBackBitMap {
        void process(Bitmap bitmap);
    }
}
