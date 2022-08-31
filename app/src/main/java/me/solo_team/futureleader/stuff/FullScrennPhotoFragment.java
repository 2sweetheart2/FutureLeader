package me.solo_team.futureleader.stuff;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.R;

public class FullScrennPhotoFragment extends Fragment {

    private static final String ARG_PARAM1 = "url";

    private String url;


    public FullScrennPhotoFragment() {
    }

    public static FullScrennPhotoFragment newInstance(String url) {
        FullScrennPhotoFragment fragment = new FullScrennPhotoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(ARG_PARAM1);
        }
    }

    Utils.ScalingImage.ScaleListener scaleListener;
    View.OnClickListener onTouch;

    public interface onToucn {
        void onTouch(boolean mode);
    }

    SubsamplingScaleImageView image;
    Bitmap bitmap;
    public onToucn touch;
    boolean mode = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.full_screen_photo_fragment, container, false);
        onTouch = v -> {
            mode = !mode;
            touch.onTouch(mode);
        };

        image = root.findViewById(R.id.my_image);
        Constants.cache.getAndSavePhoto(url, bitmap -> requireActivity().runOnUiThread(() -> {
            image.setImage(ImageSource.bitmap(bitmap));
            this.bitmap = bitmap;
        }), this);

        return root;
    }




}
