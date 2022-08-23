package me.solo_team.futureleader.stuff;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.R;

public class FullScrennPhotoFragment extends Fragment {

    private static final String ARG_PARAM1 = "url";

    private String url;

    public Utils.ScalingImage image;

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

    public interface onToucn{
        void onTouch(boolean mode);
    }

    public onToucn touch;
    boolean mode = true;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.full_screen_photo_fragment, container, false);
        onTouch = v -> {mode = !mode;touch.onTouch(mode);};
        image = new Utils.ScalingImage(requireContext(),null,onTouch);
        scaleListener = image.scaleListener;
        Constants.cache.addPhoto(url, image,false, this);
        requireActivity().addContentView(image, root.findViewById(R.id.my_image).getLayoutParams());

        return root;
    }




}
