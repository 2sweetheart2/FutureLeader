package me.solo_team.futureleader;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Utils {


    public static class ShowSnackBar {
        public static void show(Context context, String message, View view) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
        }
    }

    public static String parseDateBirthday(String date){
        System.out.println(date);
        String[] nums = date.split("/");
        StringBuilder text = new StringBuilder();
        text.append(nums[0]).append(' ');
        switch (nums[1]){
            case "01":
                text.append("января");
                break;
            case "02":
                text.append("февраля");
                break;
            case "03":
                text.append("марта");
                break;
            case "04":
                text.append("апреля");
                break;
            case "05":
                text.append("мая");
                break;
            case "06":
                text.append("июня");
                break;
            case "07":
                text.append("июля");
                break;
            case "08":
                text.append("августа");
                break;
            case "09":
                text.append("сентября");
                break;
            case "10":
                text.append("октября");
                break;
            case "11":
                text.append("ноября");
                break;
            case "12":
                text.append("декабря");
                break;
        }
        text.append(' ').append(nums[2]).append("г.");
        return text.toString();
    }

    // пост запрос для получения картинки и вывода её в Bitmap через interface(callback)
    // зачем интерфейс? а потому что мы не можем отправлять запросы из основного потока,
    // а поскольку не можем отправлять из основного потока, то создаём новый,
    // и вот как раз делать return и переменная ответа не получиться, так как мы в другом потоке
    // а через interface, после окончания получения, у нас он вернётся
    public static void getBitmapFromURL(String src, CallBackBitMap callback) {
        new Thread(() -> {
            try {
                URL url = new URL(src);
                Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                callback.process(image);
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.setRequestMethod("POST");
//                connection.setDoInput(true);
//                connection.connect();
//                InputStream input = connection.getInputStream();
//                Bitmap myBitmap = BitmapFactory.decodeStream(input);
//                callback.process(myBitmap);

            } catch (IOException e) {
                e.printStackTrace();
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

    /**
     * Просто не вникай, я сам не понимаю
     */
    public static class ScalingImage extends androidx.appcompat.widget.AppCompatImageView {
        Matrix matrix;
        // We can be in one of these 3 states
        static final int NONE = 0;
        static final int DRAG = 1;
        static final int ZOOM = 2;

        int mode = NONE;

        // Remember some things for zooming
        PointF last = new PointF();
        PointF start = new PointF();
        float minScale = 1f;
        float maxScale = 4f;
        float[] m;
        int viewWidth, viewHeight;
        static final int CLICK = 3;
        float saveScale = 1f;
        protected float origWidth, origHeight;
        int oldMeasuredWidth, oldMeasuredHeight;
        ScaleGestureDetector mScaleDetector;
        Context context;

        public ScalingImage(Context context) {
            super(context);
            sharedConstructing(context);
        }

        public ScalingImage(Context context, AttributeSet attrs) {
            super(context, attrs);
            sharedConstructing(context);
        }

        private void sharedConstructing(Context context) {
            super.setClickable(true);
            this.context = context;
            mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
            matrix = new Matrix();
            m = new float[9];
            setImageMatrix(matrix);
            setScaleType(ScaleType.MATRIX);
            setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mScaleDetector.onTouchEvent(event);
                    PointF curr = new PointF(event.getX(), event.getY());
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            last.set(curr);
                            start.set(last);
                            mode = DRAG;
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if (mode == DRAG) {
                                float deltaX = curr.x - last.x;
                                float deltaY = curr.y - last.y;
                                float fixTransX = getFixDragTrans(deltaX, viewWidth, origWidth * saveScale);
                                float fixTransY = getFixDragTrans(deltaY, viewHeight, origHeight * saveScale);
                                matrix.postTranslate(fixTransX, fixTransY);
                                fixTrans();
                                last.set(curr.x, curr.y);
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            mode = NONE;
                            int xDiff = (int) Math.abs(curr.x - start.x);
                            int yDiff = (int) Math.abs(curr.y - start.y);
                            if (xDiff < CLICK && yDiff < CLICK)
                                performClick();
                            break;
                        case MotionEvent.ACTION_POINTER_UP:
                            mode = NONE;
                            break;
                    }
                    setImageMatrix(matrix);
                    invalidate();
                    return true; // indicate event was handled
                }
            });
        }

        public void setMaxZoom(float x) {
            maxScale = x;
        }

        private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                mode = ZOOM;
                return true;
            }

            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float mScaleFactor = detector.getScaleFactor();
                float origScale = saveScale;
                saveScale *= mScaleFactor;
                if (saveScale > maxScale) {
                    saveScale = maxScale;
                    mScaleFactor = maxScale / origScale;
                } else if (saveScale < minScale) {
                    saveScale = minScale;
                    mScaleFactor = minScale / origScale;
                }
                if (origWidth * saveScale <= viewWidth || origHeight * saveScale <= viewHeight)
                    matrix.postScale(mScaleFactor, mScaleFactor, viewWidth / 2, viewHeight / 2);
                else
                    matrix.postScale(mScaleFactor, mScaleFactor, detector.getFocusX(), detector.getFocusY());
                fixTrans();
                return true;
            }
        }

        void fixTrans() {
            matrix.getValues(m);
            float transX = m[Matrix.MTRANS_X];
            float transY = m[Matrix.MTRANS_Y];
            float fixTransX = getFixTrans(transX, viewWidth, origWidth * saveScale);
            float fixTransY = getFixTrans(transY, viewHeight, origHeight * saveScale);
            if (fixTransX != 0 || fixTransY != 0)
                matrix.postTranslate(fixTransX, fixTransY);
        }

        float getFixTrans(float trans, float viewSize, float contentSize) {
            float minTrans, maxTrans;
            if (contentSize <= viewSize) {
                minTrans = 0;
                maxTrans = viewSize - contentSize;
            } else {
                minTrans = viewSize - contentSize;
                maxTrans = 0;
            }
            if (trans < minTrans)
                return -trans + minTrans;
            if (trans > maxTrans)
                return -trans + maxTrans;
            return 0;
        }

        float getFixDragTrans(float delta, float viewSize, float contentSize) {
            if (contentSize <= viewSize) {
                return 0;
            }
            return delta;
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            viewWidth = MeasureSpec.getSize(widthMeasureSpec);
            viewHeight = MeasureSpec.getSize(heightMeasureSpec);
            //
            // Rescales image on rotation
            //
            if (oldMeasuredHeight == viewWidth && oldMeasuredHeight == viewHeight
                    || viewWidth == 0 || viewHeight == 0)
                return;
            oldMeasuredHeight = viewHeight;
            oldMeasuredWidth = viewWidth;
            if (saveScale == 1) {
                //Fit to screen.
                float scale;
                Drawable drawable = getDrawable();
                if (drawable == null || drawable.getIntrinsicWidth() == 0 || drawable.getIntrinsicHeight() == 0)
                    return;
                int bmWidth = drawable.getIntrinsicWidth();
                int bmHeight = drawable.getIntrinsicHeight();
                Log.d("bmSize", "bmWidth: " + bmWidth + " bmHeight : " + bmHeight);
                float scaleX = (float) viewWidth / (float) bmWidth;
                float scaleY = (float) viewHeight / (float) bmHeight;
                scale = Math.min(scaleX, scaleY);
                matrix.setScale(scale, scale);
                // Center the image
                float redundantYSpace = (float) viewHeight - (scale * (float) bmHeight);
                float redundantXSpace = (float) viewWidth - (scale * (float) bmWidth);
                redundantYSpace /= (float) 2;
                redundantXSpace /= (float) 2;
                matrix.postTranslate(redundantXSpace, redundantYSpace);
                origWidth = viewWidth - 2 * redundantXSpace;
                origHeight = viewHeight - 2 * redundantYSpace;
                setImageMatrix(matrix);
            }
            fixTrans();
        }

    }

}
