package me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.nastavniki;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.menu.MenuGrid;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;
import me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.Partners;
import me.solo_team.futureleader.ui.news.open_news.OpenNewsFragment;

public class NastavninkiLayout extends Her {

    List<ImageView> views = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.founder_layout);
        MenuGrid grid = new MenuGrid(findViewById(R.id.back__), this, getWindowManager());
        List<String> urls = Arrays.asList(
                "https://future-leaders.ru/resuorces/others/bCG3ltrdfhATcBuJJCVkx32M4Tu8l98N9dnlUpQjptetJMy0U6GId2N3XO5-wzZvkTraNIQVWhzTG6ITLLU1kg.png",
                "https://future-leaders.ru/resuorces/others/hmi-CBaXepfJaJa76bNDvB9k-4LlLKLSjNzK2itEVr4JF64IbihjHHB43xG5R09IRe2wSm3kKswYfdDy0KWUiA.png",
                "https://future-leaders.ru/resuorces/others/5vlb7MdaefvTPNq2hW0MXkC0UePDoIOto3Fn8RugWn06qZwWC1lhTYa7gmKr5N5tdPOEIEmWnKQci5a9D1ZHjQ.png",
                "https://future-leaders.ru/resuorces/others/voFx4ubKkcneLnzhPJBhwwjBaR7PducXkC7-otMc-5Gol9t2xaKyy_2dUDuAB-To6y7Em4d4Tmh2JogOYQjkkA.png",
                "https://future-leaders.ru/resuorces/others/TbWbtxoD9qaXxWxALVAHYd9vzgTQ6-_jzkdN_c2HGnNNYmuRxIhc_Ze57nmaJ3wAWW9Ml2-vF0AcIiPiFe7NtA.png",
                "https://future-leaders.ru/resuorces/others/mdmfvLy3MVjf0ScIewkAyiseIUb8ljfSWqJ9_QAE6PjyQzOvJdVYzlU9njcCPLO49lXGnd2F2BiXZ3-swtwiGw.png"
        );
        for (int i = 0; i < urls.size(); i++) {
            ImageView v = grid.addImageElement(null, false);
            views.add(v);
            Constants.cache.addPhoto(urls.get(i), v, this);
        }
        views.get(0).setOnClickListener(v -> {
            Intent intent = new Intent(this, Partners.class);
            intent.putExtra("title", "Корпорации и бизнес");
            intent.putExtra("urls", new ArrayList<>(Arrays.asList(
                    "https://future-leaders.ru/resuorces/others/149Y2Ex6hnLxV2k_X-bWxpkHpKTaJGiMKEkvan_O9CYc4ENJ4f9PgwzdxD6vJpApIy0IL7f2xL8lXteBpUhHwA.png",
                    "https://future-leaders.ru/resuorces/others/JP7MsrCEHn9OzgCnZr-ke_BvheaiLKe_IrRZbey6g5Gw_LfqMMgknxHkSdt4jLcouaRJ9zypU6S4-zYkq_S_lA.png",
                    "https://future-leaders.ru/resuorces/others/2qlDPqflH7ZFLNr11y56WWaKlyTPH3X2vGiSTJQATQbhM_Ny2osHHOu7vR9JsVZCqQ9QlLNR126sQjPju56bPg.png",
                    "https://future-leaders.ru/resuorces/others/eCBYqLNJcLo-Ao5TS_QuWYn7Z6f_zTDYO4NAqcKRzz5othksYSq6ahKo-wuntqlmykVhpxOwz_yRoTS1RAdAvg.png",
                    "https://future-leaders.ru/resuorces/others/jkAOMVao8KOoEwT5EUN7DwrMApTCBHf2H9bVCP65XKhOEQjUjCsA5qb8sH2Fzh4HtE2ND0hHODeNZ8VbiaMoZQ.png",
                    "https://future-leaders.ru/resuorces/others/q9djWkGisF8wy-ZvlfRC8vgUDdVseF14Eq076MsDCeXx9CZTrEqu9hVEqpJcH8w-TaM190f6PjjDR9b3-tIz6A.png",
                    "https://future-leaders.ru/resuorces/others/er2o2V2VnoKEZOLHWXZOEIXO0ZAcs59xgD85SYVg2I2Tg6saLFEcdfgO0ZeY_BAaPfLACsSl9Why0AQoNzKOAQ.png",
                    "https://future-leaders.ru/resuorces/others/cCOsxLaAEDQmzp3UA5sFl3JKZtqWwdoV_lYULT1INlkKAs9zCDbrtYbDs9ovIe0gG2G7VrRLPXzxBzbRet2a9Q.png",
                    "https://future-leaders.ru/resuorces/others/MEQwXZyLIECnXlsmIau4LGMAZVT2-8T4VzRnR3eRQSQ-8mTpSpddFu1Lk0U1UjyXFZZUp3V1Y01rJDisIaSDVA.png",
                    "https://future-leaders.ru/resuorces/others/VnFivxj-I7JFXkakMNVRlG7ww85ZULkhsFpluV4Gsx2Dio-Vq16CYhI23HEYA2fwpMcxrQ3pgS2suPD0OqY3jQ.png",
                    "https://future-leaders.ru/resuorces/others/oF-jEN61wC77hqpDzoO5TcIqnqTswhzNjPlgHjxsq1VH1GpZzSkkmn-O5uofOC3aDCPccbwjLoK2RhAEykstKQ.png",
                    "https://future-leaders.ru/resuorces/others/bKA5qUJPtc766eabWNHNljUzwuLd49d7wN2PQXm49LCUe6oS6mZetKlP1LO7dMNdyxJxL0nZxzRsh_uPYHV_dw.png",
                    "https://future-leaders.ru/resuorces/others/ew63YmYCK2GEshphuq5GwtvkI7kEoHn7crMLLsfugaceir2wiYlqblrIiYdhqSMCFGnnFa4LIBxt2uBOmXKs-g.png",
                    "https://future-leaders.ru/resuorces/others/mMH5R0y0folSjWDIHL30TaS_ChMwYpKXxiBf725orF1YasWNFHp_HET49vAZnF6VXJNx6KCAHX1vXcqV97_31A.png",
                    "https://future-leaders.ru/resuorces/others/mc8AfBtj53QnUQnGNLPHA5cw7edg-GZPo-_aoVnYdwoQcmEsXHSuaEig49n-X77ah3wucj5sMnD5QxEKfe6g2g.png",
                    "https://future-leaders.ru/resuorces/others/MH8UthGeidNXOKcRoGqP_GYsKB2dZGsvX-zmHb_oJcNJCo2U3Der5uKHUfPUh_D8MvStHDJPucJNqLb5wJSsaQ.png",
                    "https://future-leaders.ru/resuorces/others/kCoKrXPtVK2kOjrDgNCaDhbFccbvqzrDXSiJaF5T-Hh6QGRVQeGmYpQjw6EBZlfs3475oD8F8nWv0YKaH9V1zQ.png",
                    "https://future-leaders.ru/resuorces/others/q2jMSUBwaNRdjOzlpppszhl7Ss7VXx1zsKbxCLL6QwH64MNmAAZThroox2kUmB2MatobUMibPSeSoSGPPHuqcQ.png",
                    "https://future-leaders.ru/resuorces/others/EUWmFTPfaSVY7FxlhmnDTlax9Q_e3khK-ZtsOn1FK3dEmD8fNxK_zXgXtGfZ2PbB8ad7ozCAFggeRq1IaUxU7w.png",
                    "https://future-leaders.ru/resuorces/others/UMLF33i6WfcUFvN7ZeqvbKiLSXG0Y_NfcA4BhzDtypQLdvtl55p7BxcAsO1_V3w9NZ4nfLV4p5_umFDbrCZzrw.png",
                    "https://future-leaders.ru/resuorces/others/k714Ac_U1LYmw1UwoiLUa7Jph8BoA1_a5Svv5OPyexLrLWGYHh37CM9OOp2deZ2_54mf4K1N6V6rUh1RwQK3zg.png",
                    "https://future-leaders.ru/resuorces/others/v1WEdQ_G_LPACO5UVTUjmfIEuPQ6rxnax4--PCGioRuxHAwqfrKR-ZEBsBXWFvVlgYJXvBgywF2i3q2BnYOIQw.png",
                    "https://future-leaders.ru/resuorces/others/4ZTFF7tHQriCyms2BSIESMv-4hDlNoixhmO5IKrLXyTx2tSU3I1OQx2_dgIxaqIuTD5RqWSGjWe8A_4b085APQ.png",
                    "https://future-leaders.ru/resuorces/others/ZxcqDHFa12WXY8fxg11BWn1Vj7nbWEr0f1R3S4ZsohZ3Amxt1GCu3jZzfUYFxDrgNNbyGyCssHzFAVHiLBLikA.png",
                    "https://future-leaders.ru/resuorces/others/aPCj9yiX_pLiylKa6Cm8BCB6J56AJLDbsAWfgpKZuMkw1kWBxAg3neVTQhcpgIb8hcA50EUxV3vr9r5bzcc2Cg.png",
                    "https://future-leaders.ru/resuorces/others/cit3aMnCmIFlYc7hSM8gkfGl9r6tE8X3xaMbZdgN9Ek_Vz66pXJqRpE9EaDlHs6qCjcNyAP-F-AZdDHBvEccog.png",
                    "https://future-leaders.ru/resuorces/others/msX16TKufV-TkF_2yDlRs4Ps6Azg91RFadfPJUDqF8yCzLooYope0qaYbaipfvcgerSBGC8uYeuujXqpwzRARw.png",
                    "https://future-leaders.ru/resuorces/others/qXEId9Yms6dM37TGQspV2V31Ytw5ReFVIRhZFHYSSjyZfzGIxmDW2eXGEgiHAIVLXY7ioDAbiiBmyc37tycbiA.png",
                    "https://future-leaders.ru/resuorces/others/GyXfCtG4LJeQXBT9dU_r3BixKLALsXNuDLt-NtUoRSPgMd9rKwaLypgO_PUMDpjX11X7YqCOastavh4dnkEr3w.png",
                    "https://future-leaders.ru/resuorces/others/j81s4pJ7CKC1G1Swy1NbeO6XpiyuDCaTC2qkq09_x5CO9tIrf78s6rxVyy44h0qnr0tXzy60VUZdRJ05hrvx9Q.png",
                    "https://future-leaders.ru/resuorces/others/QZhm-X-SEUnb_5ExgQmDvjdI4aA4mjDQgcbI79UrjQA2zBGGjjjIuuhbxsbK-dp-o61v4mtqw27Y2lrot-IkCQ.png",
                    "https://future-leaders.ru/resuorces/others/5_C77IplaXmNoD4dHZ6hwTCWlbtWbXgD00aP-1SIVM8kseaGjWbawkb9xm09XbymJB6Z1eVed9raxxlzAZiPew.png",
                    "https://future-leaders.ru/resuorces/others/jhaperdo6l59bdwPajGEdVCyquIxUSpbNF4P3FidVVYOttc-I-JIWnUoWgKI-VvfXmMtK2WrtexqpAOtnLjwEA.png",
                    "https://future-leaders.ru/resuorces/others/UFMk5TiMayg55BDS8R3wp357lvxUQ-1YQpFalLEXpbIUzN7pcsEGlun6wcpyLkF_EZ1P8n10nrEOevSoCKHlaw.png",
                    "https://future-leaders.ru/resuorces/others/ojwHlBR1jLehXOn3cvLm_dXcAX7YfP23PrtLTBuywhvd3odFfbhFaX1ZO0TqmbLUWoh5sHxgf3HL09BgHIUcHg.png",
                    "https://future-leaders.ru/resuorces/others/nyCIsx_VEqupR86WfPZDWZsg09dH-cD1TMo7RER2lzDJ7iuOBAnCWG80b_IpcrPxATHBgXOw7L9SELYv2hwf8Q.png",
                    "https://future-leaders.ru/resuorces/others/yctWpibPzpZPty4QpGtrtJnOeunQa6AqCJQf9-6NZMTQ1rbHWj7JJZFxYkoO7jMQP28l2fSLC8XVRVzQjJahKg.png",
                    "https://future-leaders.ru/resuorces/others/szRsItXfFO0w0iBuDC4uUTxCTlpkLTnI_HLSNMg3rM7PwrJ_EphcwweJtFIoRxwpPuSuMnq4jwigGx4Jd6mi_A.png",
                    "https://future-leaders.ru/resuorces/others/wQKYQXoZ-C6qfea3tjSzTnYKCE6kQ0V9JO_uZJGZDjhDSPkJJgg4m3e2GPCE_0T-sRGypVfA6vxyr1ckrauvAw.png",
                    "https://future-leaders.ru/resuorces/others/NqrYedusmPFvn32hg8wHGxoCP3eeTCGRs-T5f6HnvIyt27cTUea6DnU1tEqgCTIpW9lqIiqka-Fayddw85JTFg.png",
                    "https://future-leaders.ru/resuorces/others/lXYEmWxeu6PaPPmenTnV7ILA55VXkySYWqPQf8NmY9PdgJyi29rn8LOCJc3Q-gihzhLG7cSyV83g_g9U-_pm8Q.png"
                    )));
            Utils.ShowSnackBar.show(NastavninkiLayout.this,"страница не доступна!",v);
            //startActivity(intent)
        });
        views.get(1).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag", "Свой вклад уже внесли");
            intent.putExtra("id", -80);
            startActivity(intent);
        });
        views.get(2).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://russianleaders.org/endowment"));
            startActivity(intent);
        });
        setTitle("Наставники");
    }
}