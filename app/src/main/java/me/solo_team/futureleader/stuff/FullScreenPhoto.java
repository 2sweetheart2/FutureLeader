package me.solo_team.futureleader.stuff;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.R;

public class FullScreenPhoto extends AppCompatActivity {
    String name;
    NavController navController;
    FullScrennPhotoFragment fragment;

    SubsamplingScaleImageView image;
    Bitmap bitmap;
    public FullScrennPhotoFragment.onToucn touch;
    boolean mode = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_photo_fragment);
        String url = getIntent().getStringExtra("url");
        name = getIntent().getStringExtra("name");

        System.out.println(url.replace("\\", ""));


//        navController = Navigation.findNavController(this, R.id.my_image_fragment);
//        fragment = FullScrennPhotoFragment.newInstance(url);
//        getSupportFragmentManager().beginTransaction().replace(R.id.my_image_fragment, fragment).commit();
        setTitle("");
//        fragment.touch = mode -> {
//            FullScreenPhoto.this.mode = mode;
//            if (!mode)
//                supportInvalidateOptionsMenu();
//            else
//                supportInvalidateOptionsMenu();
//        };
        onTouch = v -> {
            mode = !mode;
            touch.onTouch(mode);
        };

        image = findViewById(R.id.my_image);
        Constants.cache.getAndSavePhoto(url, bitmap -> runOnUiThread(() -> {
            image.setImage(ImageSource.bitmap(bitmap));
            this.bitmap = bitmap;
        }), this);

    }

    View.OnClickListener onTouch;

    public void share_bitMap_to_Apps(Bitmap bitmap) {
        Intent i = new Intent(Intent.ACTION_SEND);

        i.setType("image/*");

        i.putExtra(Intent.EXTRA_STREAM, getImageUri(this, bitmap));
        System.out.println("CREATE INTENT");
        try {
            startActivity(Intent.createChooser(i, "Поделиться фото"));
            System.out.println("TRY TO OPEN INTENT");

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
                            System.out.println("PERMISSION ERROR");
                            return false;
                        }
                        if (bitmap == null)
                            return false;
                        System.out.println("TRY TO SAVE");
                        save(bitmap);
                        return true;
                    })
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return super.onPrepareOptionsMenu(menu);
    }


    private void save(Bitmap icon) {
        share_bitMap_to_Apps(icon);
    }

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.O) {
            if (checkSelfPermission(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE);

                } else {
                    requestPermissions(
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                if(shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_EXTERNAL_STORAGE
                )){
                    showDialog("Read extrenal storage",context,Manifest.permission.READ_EXTERNAL_STORAGE);
                }else{
                    requestPermissions(
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
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
