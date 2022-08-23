package me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.future_leaders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.WebViewsContent.WebView;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class Contacts extends Her {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.her_ne_eby_name);
        ImageView contact1 = findViewById(R.id.contact_1);
        ImageView contact2 = findViewById(R.id.contact_2);
        ImageView contact3 = findViewById(R.id.contact_3);
        ((TextView) findViewById(R.id.text_2)).setText("YouTube");
        ((TextView) findViewById(R.id.text_3)).setText("Сайт");
        List<String> urls = Arrays.asList(
                "https://future-leaders.ru/resuorces/others/NSof6p8uuEcigq_M_D_uJ3RSNwXoQ9dkRGTYGWrMoDKEt2OS-EcAjpV1dXivyCC1u6GGIoTQPDPhijqcEbvfmw.png",
                "https://future-leaders.ru/resuorces/others/SITg-LDUPffIPXzDYpTbcn3Lb-eHvQC5tw-99Ng5AEMaqw8Lt6y9YtFXrR_TLDXx4oIYcfPn5LpU1ltFT8nltA.png",
                "https://future-leaders.ru/resuorces/others/WtQzMleq4Yy_Sn1K3tR4o_9zwSXKCnsNma29w6GewtJ0uu54Uim3Y73eO3QLLdxJUIXDGQMJB6D6i9clLBdx6g.png"        );
        Constants.cache.addPhoto(urls.get(0),contact1,this);
        Constants.cache.addPhoto(urls.get(1),contact2,this);
        Constants.cache.addPhoto(urls.get(2),contact3,this);
        contact1.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://vk.com/fbl.russia"));
            startActivity(intent);
        });
        contact2.setOnClickListener(v ->{
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.youtube.com/channel/UCroPuprExQcQ0eT8TygAqgA"));
            startActivity(intent);
        });
        contact3.setOnClickListener(v ->{
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://russianleaders.org/"));
            startActivity(intent);
        });
        setTitle("Контакты");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
}
