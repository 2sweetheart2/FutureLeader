package me.solo_team.futureleader.ui.news;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

import me.solo_team.futureleader.MainActivity;
import me.solo_team.futureleader.R;

public class NewsFragment extends Fragment {

    private LayoutInflater inflater;
    private ViewGroup container;
    private LinearLayout nw;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_news, container, false);
        nw = root.findViewById(R.id.news_list);
        this.inflater = inflater;
        this.container = container;
        List<String> uris = Arrays.asList(
                "https://storage.yandexcloud.net/hrbox/260/2022/04/27/fde053db-23e9-4072-8b44-2621e9d74f70.extra/resize_800x450.jpg?response-content-disposition=filename%3D%22resize_800x450.jpg%22&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=oD9hozHVlgeP05O8GQ3L%2F20220428%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220428T143758Z&X-Amz-SignedHeaders=host&X-Amz-Expires=21600&X-Amz-Signature=946812dbdcb1ef1c878fb6954efcf826f7c710c72a32cf553f627cb70b3359bd",
                "https://storage.yandexcloud.net/hrbox/260/2022/04/26/3331710e-5c99-4029-8b55-6528bf903a51.extra/resize_800x450.jpg?response-content-disposition=filename%3D%22resize_800x450.jpg%22&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=oD9hozHVlgeP05O8GQ3L%2F20220428%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220428T191943Z&X-Amz-SignedHeaders=host&X-Amz-Expires=21600&X-Amz-Signature=a44e5e34eec509cdb0d9efa43c14aaf07f2c3ba70009d5da9b5bd249e4b9e88e",
                "https://storage.yandexcloud.net/hrbox/260/2022/04/25/041b3ad4-5425-4444-b0e0-3f99adabf541.extra/resize_800x450.jpg?response-content-disposition=filename%3D%22resize_800x450.jpg%22&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=oD9hozHVlgeP05O8GQ3L%2F20220428%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220428T191943Z&X-Amz-SignedHeaders=host&X-Amz-Expires=21600&X-Amz-Signature=ca434d6a97fdcead7a784588fbe356d267f05b52b040f429367cfa43afde6242",
                "https://storage.yandexcloud.net/hrbox/260/2022/04/25/2c2fb9f7-ffac-43b1-89a0-d2aff03fb0ba.extra/resize_800x450.jpg?response-content-disposition=filename%3D%22resize_800x450.jpg%22&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=oD9hozHVlgeP05O8GQ3L%2F20220428%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220428T191943Z&X-Amz-SignedHeaders=host&X-Amz-Expires=21600&X-Amz-Signature=7d00da134cc19628f3b9be35aaccfaddf96dfdd2cae6c6d3bee60a7e483d1cc5",
                "https://storage.yandexcloud.net/hrbox/260/2022/04/20/bcc0694b-44e0-46e0-9e6d-7be1105ca2b9.extra/resize_800x450.jpg?response-content-disposition=filename%3D%22resize_800x450.jpg%22&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=oD9hozHVlgeP05O8GQ3L%2F20220428%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220428T191943Z&X-Amz-SignedHeaders=host&X-Amz-Expires=21600&X-Amz-Signature=802edbc9fdde80a745f67f338a404b002ce175fefc9e1464ff4f6a9e5d560df5",
                "https://storage.yandexcloud.net/hrbox/260/2022/04/19/88671104-328d-47a9-a8f0-13f0608e6009.extra/resize_800x450.jpg?response-content-disposition=filename%3D%22resize_800x450.jpg%22&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=oD9hozHVlgeP05O8GQ3L%2F20220428%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220428T191943Z&X-Amz-SignedHeaders=host&X-Amz-Expires=21600&X-Amz-Signature=0c193348b9d68a5c57176f54f2a517690b6434443e7232075d8e9dc3304ade8c",
                "https://storage.yandexcloud.net/hrbox/260/2022/04/19/5626bbc5-f7ae-46c1-a7dc-697ba427c0de.extra/resize_800x450.jpg?response-content-disposition=filename%3D%22resize_800x450.jpg%22&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=oD9hozHVlgeP05O8GQ3L%2F20220428%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220428T191943Z&X-Amz-SignedHeaders=host&X-Amz-Expires=21600&X-Amz-Signature=144fed4badd7702b1903503707fe2d23cf201a53eb8812798e48e52b9cbb780e",
                "https://storage.yandexcloud.net/hrbox/260/2022/04/19/c1d39521-c49c-49bc-9bfc-92802c60f493.extra/resize_800x450.jpg?response-content-disposition=filename%3D%22resize_800x450.jpg%22&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=oD9hozHVlgeP05O8GQ3L%2F20220428%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220428T191943Z&X-Amz-SignedHeaders=host&X-Amz-Expires=21600&X-Amz-Signature=6c4b1688655a93f893445e10f716fa5eb6845d684d76334906bfa9f789fa5194"
        );
        List<String> names = Arrays.asList(
                "#КООРДИНАЦИОННЫЙ СОВЕТ",
                "#КООРДИНАЦИОННЫЙ СОВЕТ",
                "#КООРДИНАЦИОННЫЙ СОВЕТ",
                "#КООРДИНАЦИОННЫЙ СОВЕТ",

                "#НОВОСТИ ФОНДА",
                "#НОВОСТИ ФОНДА",
                "#КООРДИНАЦИОННЫЙ СОВЕТ",
                "#НОВОСТИ ФОНДА"
        );
        for(int i =0;i<9;i++){
            // тут умирает, не трогать!!!!!!!!!!!!!!!!!!!!
            //addElement(uris.get(i),names.get(i));
        }
        return root;
    }

    private void addElement(String uri,String name){
        FrameLayout row = (FrameLayout) inflater.inflate(R.layout.news_news, container,false);
        ConstraintLayout cn = row.findViewById(R.id.news_obj);
        Picasso.get().load(uri).into((ImageView)cn.getChildAt(1));
        ((TextView)cn.getChildAt(2)).setText(name);
        switch (MainActivity.wightwindowSizeClass){
            case COMPACT:
                ((TextView)cn.getChildAt(2)).setTextSize(12f);
                break;
            case MEDIUM:
                ((TextView)cn.getChildAt(2)).setTextSize(15f);
                break;
            case EXPANDED:
                ((TextView)cn.getChildAt(2)).setTextSize(20);
                break;
        }
        nw.addView(row);
    }


}