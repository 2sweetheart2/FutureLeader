package me.solo_team.futureleader.stuff;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import me.solo_team.futureleader.R;

public class FullScreenPhoto extends AppCompatActivity {
    String name;
    NavController navController;
    FullScrennPhotoFragment fragment;
    boolean mode = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_photo);
        String url = getIntent().getStringExtra("url");
        name = getIntent().getStringExtra("name");

        System.out.println(url.replace("\\", ""));


        navController = Navigation.findNavController(this, R.id.my_image_fragment);
        fragment = FullScrennPhotoFragment.newInstance(url);
        getSupportFragmentManager().beginTransaction().replace(R.id.my_image_fragment, fragment).commit();
        setTitle("");
        fragment.touch = new FullScrennPhotoFragment.onToucn() {
            @Override
            public void onTouch(boolean mode) {
                FullScreenPhoto.this.mode = mode;
                if (!mode)
                    supportInvalidateOptionsMenu();
                else
                    supportInvalidateOptionsMenu();
            }
        };


    }

    public void share_bitMap_to_Apps(Bitmap bitmap) {
        Intent i = new Intent(Intent.ACTION_SEND);

        i.setType("image/*");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
    /*compress(Bitmap.CompressFormat.PNG, 100, stream);
    byte[] bytes = stream.toByteArray();*/

        i.putExtra(Intent.EXTRA_STREAM, getImageUri(this, bitmap));
        try {
            startActivity(Intent.createChooser(i, "Поделиться фото"));
        } catch (android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, name, null);
        return Uri.parse(path);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    @SuppressLint("ResourceType")
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(mode);
        if(mode)
            menu.add(0, 1, 0, "")
                    .setIcon(R.drawable.share_black)
                    .setOnMenuItemClickListener(item -> {
                        if (!checkPermissionREAD_EXTERNAL_STORAGE(this)) {
                            return false;
                        }
                        if(fragment.bitmap==null)
                            return false;
                        save(fragment.bitmap);
                        return true;
                    })
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return super.onPrepareOptionsMenu(menu);
    }

    public static Bitmap getBitmapFromView(View view) {
        // Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        // Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        // Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            // has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            // does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        // return the bitmap
        return returnedBitmap;
    }


    private void save(Bitmap icon) {
        share_bitMap_to_Apps(icon);
    }

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    public void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                (dialog, which) -> ActivityCompat.requestPermissions((Activity) context,
                        new String[]{permission},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE));
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // do your stuff
                } else {
                    Toast.makeText(FullScreenPhoto.this, "GET_ACCOUNTS Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }
}
