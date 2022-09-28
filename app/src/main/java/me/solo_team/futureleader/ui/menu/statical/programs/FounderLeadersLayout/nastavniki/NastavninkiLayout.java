package me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.nastavniki;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.menu.MenuGrid;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;
import me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.Partners;

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
            intent.putExtra("url_names", new ArrayList<>(Arrays.asList(
                    "https://future-leaders.ru/resuorces/others/T2dSSFGQv5L-eQgWSip43Mc3XhmKQYmosM7-gI37BNkWNfqI0DtiXa44u3M7eyOTkCHbqloPS3h4hFq9tr4EEA.png",
                    "https://future-leaders.ru/resuorces/others/T3Kv6f3MtJUfcfFI1gHb8Fk3WvdIuEU282sXBF7GKCKTy9zfYTZOlwhdeapdyh9teyucWASlczNY3vbigktEwg.png",
                    "https://future-leaders.ru/resuorces/others/rsPmFqi5puObSUON7i2t7B60gLeRPJjWzrzaSmqwbK3ITSWrBjk3j5Yi2ZKn_YKMrKBYhWMyfVuDzsAgm1E7mQ.png",
                    "https://future-leaders.ru/resuorces/others/baNSNbgj4IRf2ZsIuudm8v7c9O2sz-V871wIboEAiUdWGwMezs6QOiQZhAbCpjgzjJBY1axDfOoZCJNSbnNYZA.png",
                    "https://future-leaders.ru/resuorces/others/_fWBeDKhTcBt8e5eiXfT8yVhXi7hYnviAu6Y7D4IBbAgatJPVJZAnlIC_4CpzH1l2ueF9vv882Ruy0vYTESrZg.png",
                    "https://future-leaders.ru/resuorces/others/5wJPgV5k2bUOZRfZ1qPorNZhtwMU04E__1PTATkZxnXqeBWisEzVXUZD0eAppeQITnnLMol4xOF1STmfreoCXg.png",
                    "https://future-leaders.ru/resuorces/others/yzxgmf7gHZ8bLB0sAcSqWPpKkhiYnno3RWbI11BShoF_fC_woPsCFgCwXlCQbDvFtA4prYnRaDD6QlmgtZWltw.png",
                    "https://future-leaders.ru/resuorces/others/IcC9pnnOHEngNjsidaLnC4hpkQ2Ss8v4GX_5m5uad0gzm2aklWEZcbzWmSYXwdia_pEcV-nGE6NYPAdO61GRJQ.png",
                    "https://future-leaders.ru/resuorces/others/3qjmgR2enbtBYTkoZxs5lsQ29j6FuArOT4yhFWOJqSkWaQIKWWOFIOG-d2ni-VvcaNL93L4HvJnVIiLdEk7J2A.png",
                    "https://future-leaders.ru/resuorces/others/pNtMd2MQ5KHbw7FESjmOycGqgZ539XnQeRxHddplvPKq0hyF7npEX8FugO519bufQbON_se-pAyncz2xRuHI1g.png",
                    "https://future-leaders.ru/resuorces/others/01m6XPOM4reRD6gYx7NEnhBMS1VTJkBZBswUZyWJc6I1zHYpEfxT1fZ83pQYLDdqXMbntPGAFyiRrw8mGlnd7g.png",
                    "https://future-leaders.ru/resuorces/others/DyBAtN8dtCIAhWQy0x_55AeMi8cRiCBtK3ikND-vxpOM64fNZBI-Sz90BtOoXKd28Gt7Xn5CV7xTHh7NkbEXjg.png",
                    "https://future-leaders.ru/resuorces/others/c0T0fRzVPMdsBf3-ybpInpk9i9aRzgRJLeXl2_gsPO6-Jd4DQlk9ykLR6Y1wQTRgrp8zsfbiMZmNLM-gPR6Jfw.png",
                    "https://future-leaders.ru/resuorces/others/aRaFb5qClorVC_l3AI9xieibohEBTKlww1rqz-QRnop9RYcvcUmwgT714q_eMbYZDdgSS2CR0G-_Ma7hVB1YbQ.png",
                    "https://future-leaders.ru/resuorces/others/jJ72JOZjhJ6RRoKPBCgusi4nq8_wa7R4AQe4XPrrcz08D9EGICrVnSFE9hMJG8KZ-TGrVyIdSid59v39lRMADQ.png",
                    "https://future-leaders.ru/resuorces/others/yrOKP6jdyEmh9ABJ1CPOdQdjlD_vqKRx91PtDTJUWsW8ptn6x6cQ3w6sYMQcVO2gJsUH7Vb72wuwur-52p7qbg.png",
                    "https://future-leaders.ru/resuorces/others/HI6X0oLzb7dL1ozc9Md-1Ti8fBZQtd7-RuL_rD48BrYo3_qdZcWBLmA0r8b0noaWde74nFak0GdCRovi9_zXrQ.png",
                    "https://future-leaders.ru/resuorces/others/xZ2tvdmIaiO8gujNMkkYGf251FzPmLSmx1UbCRnZOaqRKhm76U4Awzvgv9c0o8J39WIikX6nT7ikKueLgkjyBg.png",
                    "https://future-leaders.ru/resuorces/others/LhehhYQ_JGLwqEqe_7AcX6Udk6YLkzRpgUSA1khktza_pGYfGY2Ho3Q88DNkPRIDsHplPIzKEQYJnqJbd36sfQ.png",
                    "https://future-leaders.ru/resuorces/others/924FGljzdaO-SBiP_mq8ZrS6aoqacohX6IDv5SobFa8yUyLQi9zFSOBELPTYCOt_RtVDlcXqoJMyn3i6GtgL1g.png",
                    "https://future-leaders.ru/resuorces/others/esropENAQQiG-pN0UiM3EswTEU2DGQsgC3N1A48GcgmDB68qJi6FInUurYEHmtPOGuilB82hTzTNlFz0up21qQ.png",
                    "https://future-leaders.ru/resuorces/others/k9qAyxH1DNSbxBu98dgIi9gyx1LZAdRppsmZ8SeOVWZ3f1Lwh7rpYY8l1BdJ_wZG1kr6RjgNtpzFbCTv6TqGgw.png",
                    "https://future-leaders.ru/resuorces/others/nRcLpiBLp0FXoeuVv-mxQMdlYUJN6XTtv3NrG4vFH51a688BOenQI2KKRHY_t8ULSEscQDYNc1PCyGgE8OKZUg.png",
                    "https://future-leaders.ru/resuorces/others/pNhXLg9pmI3dP0MZ5RFVvdBEwQHhRKXxCbE-AC0-NmhyMjPM9qv5OET9XwAWa_3G5sUGk4XfNbmXUOCYsgf5BA.png",
                    "https://future-leaders.ru/resuorces/others/jAEodMDuDgui2zAPI5nrzfFiK2MEfMNhHpkeRNCK4Uhu0whdFR2iyIDPmmoDGzs0vqscIL5BAO3E_FAYLCUaFw.png",
                    "https://future-leaders.ru/resuorces/others/KsivukX16Bv68qCe9O2oKakAfuttg5INrKKcB3j0Ar6EaezlfVLGgsHKI-edV4gTsDjoKmElq4WR-r8-_hxZ7Q.png",
                    "https://future-leaders.ru/resuorces/others/1d4cSQGRzQFZsU1IV1Z8ptRt4j9xLR9cgpYE_y4Ics6KpwcEW9ZxB_qbBOW5vdatrIYh8aOOe0-gqZF7_wTndw.png",
                    "https://future-leaders.ru/resuorces/others/PpGWVGOpe6b0doGlFQDH0SYAQBbpxCZr41mkfl0tslL-6hsr0BWyflZL1r1SazrZL3PIDJ6CTxKVzlWbmZfywQ.png",
                    "https://future-leaders.ru/resuorces/others/Xi-uEOHgG1rLNInRts6KXdxZK6wo3HoqbUSpOSkp_KdeYgcy9DWAr4OoJWnDOWF9xkvxGYHWzBTRTs-lVchDLA.png",
                    "https://future-leaders.ru/resuorces/others/wyE4DzlFg0mHMaczYUcaS0pCED63idTSLDM-WycNeERUosdAW_Ot1B6mOQ74pcPf2wWtjfZyeJwRjrL0CoaajQ.png",
                    "https://future-leaders.ru/resuorces/others/kcCkf5DG_hvK401XRdbANNNF6O_Bf6uj4LgKfbtzwM3Nl6RohmkGKAV3vpoemQIBlgk3XrQmwxd6zDwMw8mNOg.png",
                    "https://future-leaders.ru/resuorces/others/uBNgdA1XWEEUJ4yf7K3kX6Sbr8rVHiYRdzMBAen_NvpWRmHfOwZf3a3NL1cDE6I_ogoe9njwXOXSnqXZMp0qJw.png",
                    "https://future-leaders.ru/resuorces/others/HovzP5OUUIWSWZIKS5VIb11UJ35k7_8x9pfmAk-sDAZAW3DYIc0x24h3Ln1FdK7qa_OYhiN20r8RAWkzC9w8gQ.png",
                    "https://future-leaders.ru/resuorces/others/fGP5aWqS-GIajfDcCKCBMsgBnTa1CiiZaLg8ASz2tvAZgvi_E0IZONaBaMIs7b4Svtd6OSGqUGMu_sbuZZCGXw.png",
                    "https://future-leaders.ru/resuorces/others/K-ngVATmL67wb0sHSHuFQ1FTrUNM8RBWd-ruAG06HoaCYagu3gVNVgNL5Uff8cksrZJ7oA0ltFFW9UMwHyzv4A.png",
                    "https://future-leaders.ru/resuorces/others/d9KAd_E2dfw-65mTLw74XrEckkJBwKLb4ImaUjVU4XqFE4sIeVGzy2_3MTaSOyBmYR9PEfx4eX0BACwEkXehQA.png",
                    "https://future-leaders.ru/resuorces/others/GldL-qub9q5weNos-ArW4vt2_-0J3xS9lw1F6jK2X66ALzuHizsKUFaCgi7RL2ufKW6IPFq0yevjZm8Lpvsa7g.png",
                    "https://future-leaders.ru/resuorces/others/JNpoNZjPaFO5K0EowtPHDiXXhfblrQpK9U24aejs4Gk-LaimO8e9EV01KcX4szyDwKlNeLHxifDtcxDrERixpg.png",
                    "https://future-leaders.ru/resuorces/others/L2J51KV0CUZDmbJ_qf4edUs5vbBkfi5b6gC2yoYJ_5EpoXAiLGxyzkWBC2-bfJdiOeXaWcQ2PojRjINqA4StIg.png",
                    "https://future-leaders.ru/resuorces/others/i9-rbdY0FLn8wcpsDZCGVXjmhncymZGN2CYW4ri7xrdI6jI9a1Q8O-p4uiK4j0XYDYIAQveFuBGNdbq-TEFT9w.png",
                    "https://future-leaders.ru/resuorces/others/oRARsvdd5V0pHs-UylvobvahFujIFkcma4uB41Q3X1A9mNg3N6tZiJt4SlgyxDdpjJj0fRp_e8Lq89CjbaqvDw.png"
            )));
            intent.putExtra("redirects", new ArrayList<>(Arrays.asList(
                    "https://tekner.ru/",
                    "https://encore.ru/#about",
                    "https://teslaamazing.com/ru/",
                    "https://doubledata.ru/",
                    "https://spb.hh.ru/",
                    "https://experum.ru/",
                    "https://ancor.ru/",
                    "https://neva-realty.ru/",
                    "https://more.tv/",
                    "https://youthstore.ru/",
                    "https://vk.com/yandex.chef",
                    "https://www.spasiboshop.org/",
                    "https://ingria-startup.ru/ingria-open-hours/?utm_source=yandex&utm_medium=cpc&utm_campaign=56879957&utm_content=10665181747&utm_term=%D0%B8%D0%BD%D0%B3%D1%80%D0%B8%D1%8F%20%D0%B1%D0%B8%D0%B7%D0%BD%D0%B5%D1%81%20%D0%B8%D0%BD%D0%BA%D1%83%D0%B1%D0%B0%D1%82%D0%BE%D1%80&yclid=4834570603507788228",
                    "https://fedorinov.com/?fbclid=IwAR0Z_wul86MB1vKium_DEdRvcRAEbilG9eyRZpRNKh_jiqz3fv3jZ1Lchyk",
                    "http://mariae.ru/",
                    "https://gratanet.com/ru",
                    "http://weareproevent.com/",
                    "https://www.dentons.com/ru",
                    "https://novotrans.com/%D0%BC%D0%BE%D1%80%D1%81%D0%BA%D0%B8%D0%B5-%D0%BF%D0%BE%D1%80%D1%82%D1%8B/ust-luga-comp/",
                    "https://egotranslating.com/",
                    "http://nikkolom.ru/",
                    "https://souzconsalt.com/",
                    "https://logomachine.ru/",
                    "http://cropsalon.ru/",
                    "https://www.instagram.com/cubelineagency/",
                    "https://bi-school.ru/",
                    "https://www.instagram.com/wedangels/",
                    "https://actimir.ru/",
                    "https://www.instagram.com/shineandsmile.spb/",
                    "https://deasign.ru/",
                    "https://tsqconsulting.ru/",
                    "https://probiz-consulting.ru/",
                    "https://setters.education/archive/targetirovannaya-reklama?utm_source=ig&utm_medium=bio&utm_campaign=brova",
                    "https://investclub.site/",
                    "https://www.sixhands.co/",
                    "https://lonna.ru/",
                    "https://www.comagic.ru/?utm_source=yandex-direct&utm_medium=cpc&utm_campaign=brand_search_dec2020&cm_id=57430830_4391322196_10704840883_24024189949__none_search_type1_no_desktop_premium_2&yclid=4838304051772827916",
                    "https://gonzo-design.ru/",
                    "http://marketingstory.ru/",
                    "https://gamesup42.com/",
                    "https://spb.carmoney.ru/?utm_source=facebook.com&utm_medium=smm&utm_campaign=fb_main&fbclid=IwAR2ulTf3jwPeQNYdS6mTDeYMMPGj_3ggEM8kzrzCbJ7UPr9QzIZrxQJwdFs"
            )));
            intent.putExtra("names", new ArrayList<>(Arrays.asList(
                    "Со-Основатель|Со-Основатель",
                    "Собственник холдинга|Собственник холдинга",
                    "Со-Основатель|Со-Основатель",
                    "Технический директор|Технический директор",
                    "Директор по организационному развитию|Директор по организационному развитию",
                    "СЕО|СЕО",
                    "Зам. Гендиректора|Зам. Гендиректора",
                    "Директор по маркетингу|Директор по маркетингу",
                    "Руководитель|Руководитель",
                    "Владелец|Владелец",
                    "Основатель|Основатель",
                    "Генеральный директор|Генеральный директор",
                    "Директор|Директор",
                    "Основатель|Основатель",
                    "Член совета директоров|Член совета директоров",
                    "Управляющий партнёр|Управляющий партнёр",
                    "СЕО|СЕО",
                    "Партнёр|Партнёр",
                    "Генеральный директор|Генеральный директор",
                    "Пред. Совета директоров|Пред. Совета директоров",
                    "Пред. Совета директоров|Пред. Совета директоров",
                    "Основатель|Основатель",
                    "Основатель|Основатель",
                    "Директор|Директор",
                    "Владелец|Владелец",
                    "Креативный директор|Креативный директор",
                    "Директор по устойчивому развитию|Директор по устойчивому развитию",
                    "Основатель|Основатель",
                    "Руководитель|Руководитель",
                    "СЕО|СЕО",
                    "Основатель|Основатель",
                    "Генеральный директор|Генеральный директор",
                    "СЕО|СЕО",
                    "Основатель|Основатель",
                    "Управляющий партнёр|Управляющий партнёр",
                    "Основатель|Основатель",
                    "Управляющий партнёр|Управляющий партнёр",
                    "Руководитель|Руководитель",
                    "Пред. Совета директоров|Пред. Совета директоров",
                    "Генеральный директор|Генеральный директор",
                    "Основатель|Основатель"
            )));
            intent.putExtra("ids", new ArrayList<>(Arrays.asList(
                    -143, -144, -145, -146, -147, -148, -149, -150, -151, -152, -153, -154, -155, -156, -157, -158, -159, -160, -161, -162, -163, -164, -165, -166, -167, -168, -169, -170, -171, -172, -173, -174, -175, -176, -177, -178, -179, -180, -181, -182, -183)));
            startActivity(intent);
        });
        views.get(1).setOnClickListener(v -> {
            Intent intent = new Intent(this, Partners.class);
            intent.putExtra("title", "Технологии");
            intent.putExtra("urls", new ArrayList<>(Arrays.asList(
                    "https://future-leaders.ru/resuorces/others/0nhZev9W4ZLBPw40kJkqdusvs83f4k6EiqG9TzqEpt5nQ1sZ7M3b-mt-0c3q60DOpttl6ZiqmWSLEx9tFLdZsw.png",
                    "https://future-leaders.ru/resuorces/others/PHj404_-qH9K6r1WgZ1aBGlh7NAVq6YyFdd1z67Q7pXWmIpue6bZ1H4FwAJmxn4HVnnlozunMifLrJQgANm9Dg.png",
                    "https://future-leaders.ru/resuorces/others/2Fqp_r3rkSSRnAYC_e3WjTsR5e-2hr0LAOJAS997P52XJsG4O5NkRSr59YzKR-BCfPu3fSWpE2FZcbtd6T6h1w.png",
                    "https://future-leaders.ru/resuorces/others/nOkOZRJks60-IJ0b3IFO9aA_O8O6ffJE9JFECXMIvcEetFmkkAv1lTerehpnFCb_Y8DTmYWz0DMV0Hn2hph4cQ.png",
                    "https://future-leaders.ru/resuorces/others/gkvw3b0aSM4Fi3D-3fCq07Dlnza9jGsyE9ibzLnLk5HIXZr_OXmzboH9VtJqr1zJJ9Z-hhfjt56ZGWRKe4Rk_Q.png",
                    "https://future-leaders.ru/resuorces/others/5dYNqw6ICe1JiQ68mrpzyXTayD9GoJc8lWfCwDUcBGunK0mWsqeDsdYomT06cZZCTmfM0p5ld-pgbb9GZKl7hw.png",
                    "https://future-leaders.ru/resuorces/others/eyfmPiCWngTn8YqoVmcqb8BSvHZpAG4dm6ymn6ATF0vFkkd33Fja10pvUuUkiViaVJoEFLNXC3E1XXtYmXgeuw.png",
                    "https://future-leaders.ru/resuorces/others/IUjwjetjSk1YvD2UmGl6ZwYoSeqnRBFfkF8AaqzA_TkNgFxM6It1qsHGs1oxRSorfrxnQ6fLZonOWlauPno2Pg.png",
                    "https://future-leaders.ru/resuorces/others/nky7DCWFeoUypCTcsbE4WFd1BBeFyKdOn0BhGy7wTCBhDavGau94h_i8cgCPBj_iiE96GAcBMIghtW6ZDkRE-w.png",
                    "https://future-leaders.ru/resuorces/others/HQ2fJ2hMH7-WvHPSL6j4bi8wTgMu7fAInzGC9ggwyQq-Fvi_RWM8x4354HbEe3ty6mhVnDJBzuYCta9F1kPZog.png",
                    "https://future-leaders.ru/resuorces/others/ke99_ZZXTNKYgGhjTKTYDNLfZ8RW7ZUHvfpCxW8Y1gsMg8yWykWX7hjMDBwP13dGvVEkDekjoCTAKNzyzrhuIQ.png",
                    "https://future-leaders.ru/resuorces/others/DcEn7KYay9iz70lGlI9o680epdmC5kQ3DLZrOUt7N3TFgbV5vZZTOUUMDEz1oQaxIiY8meXItg4b6JPJ4zQHqA.png"
            )));
            intent.putExtra("names", new ArrayList<>(Arrays.asList(
                    "IT-Аналитик|IT-Аналитик",
                    "Директор по развитию|Директор по развитию",
                    "Генеральный директор|Генеральный директор",
                    "Руководитель проектов|Руководитель проектов",
                    "Глава представительства|Глава представительства",
                    "Совладелец|Совладелец",
                    "Креативный директор|Креативный директор",
                    "Основатель|Основатель",
                    "Со-Основатель|Со-Основатель",
                    "Директор по развитию|Директор по развитию",
                    "Технический директор|Технический директор",
                    "Генеральный директор|Генеральный директор"
            )));
            intent.putExtra("url_names", new ArrayList<>(Arrays.asList(
                    "https://future-leaders.ru/resuorces/others/s19BElHfKw9651A5WgACK-C_oHfYSYQHHVOEr3ZvJkeXFXoIP-7qBu6jSjtmfYGe2Thr1yGTGslYRt192dewMA.png",
                    "https://future-leaders.ru/resuorces/others/JU_Jw9uqIdVio-qrcUdcJs5jDqzUiH-ZXGpdUiM4NNDagUMxexwuYy3DniNN8r6t0lKyvin4npAIOmL7QWjX4w.png",
                    "https://future-leaders.ru/resuorces/others/Y2wkwR7Jw5TxAXrJ1xB4rbKoSGOJpc3GOtJRJywLd8PtrGnWllvzPLicKU6zc7win7QAaSXGFcGS9S9AQWLZVQ.png",
                    "https://future-leaders.ru/resuorces/others/WhLIqkx6Oe9SoAt4uqa6CY4jHuR43Kz-CojlnaKkPuPyfmqXtYDXKiEvuj5tv5eZ3GTm5-_ZkRc7eM52ksrz8Q.png",
                    "https://future-leaders.ru/resuorces/others/MQgdIYfTYboPoxxlVg6_cGMi6o4gmd_vPcf7IpWpVZCrXneHafnITfD69jUSJcrz8ynjZO0c8WEr_LxnhgBnKA.png",
                    "https://future-leaders.ru/resuorces/others/5fuIQpo0tWMKZSttjkefZW3jz2-ws98zsURy6Ud9H62gKyrff0tL_jseMyiaE8zv9Sp4yDr2O4Ql6YOlfjHt9w.png",
                    "https://future-leaders.ru/resuorces/others/4OGmV9kzFDy7vYHHaDnpttmcqTuIU6Oo26sj_9mumQnTZ10231NeLuZ2hIhT-C2HgIM8pViWB6OGoFhLNBUKiQ.png",
                    "https://future-leaders.ru/resuorces/others/iPy0iZID7ZpeuDK1ILX9XnVbipmYjJxf3gZaLcGbH9am9Rqd3UKm3hgD1_4DhtfUz4CiSD1jjvirC1rsfC_vEQ.png",
                    "https://future-leaders.ru/resuorces/others/6S5_tZn6uh5aFdmnmRMqsbwhblvLt0cWTi-rInoaKrq1-ufaB1bfg6-y0cuAR2fVPfCp2qTZS560c4k9unkqMg.png",
                    "https://future-leaders.ru/resuorces/others/CLQaIztYmWRPSdwuC46B7-bVwralX1vZNMMcKuluEZ1xJqLDzm2sf6I9cbJ0TZdhv5YIpQ3-7cbGR2tI8svUeA.png",
                    "https://future-leaders.ru/resuorces/others/d4BpXvBg3kB_YIX6-ihCajkF5EtRPeZY_X3ZAsvgeDEsnTzdC55_2Z8a-CxqKqcsHmtfAtrLWAN1zT_0khPQvg.png",
                    "https://future-leaders.ru/resuorces/others/N2BIFxmpFKtOiWwsjnMOH3tqgwQbd9WOp_jkT9sSccecxFKnJ7nWczrGu3PyIK-EZcFTFbep3sSdp3v2uytQZg.png"

            )));
            intent.putExtra("redirects", new ArrayList<>(Arrays.asList(
                    "https://www.unilever.ru/our-company/",
                    "https://gomobile.ru/?fbclid=IwAR3UyTQnaDwpoQ5HBXniJ69B3G0S_UINCLfA0Dw4XXQsQDdjXuefsZwT8kQ",
                    "https://yodvisor.com/",
                    "https://www.mtt.ru/",
                    "https://www.avast.ru/index#pc",
                    "https://cleverpumpkin.ru/",
                    "https://lynkfire.com/synticate",
                    "https://infoshell.ru/",
                    "https://whoosh.bike/#russiapopup",
                    "https://www.pmi.org/",
                    "https://mobileup.ru/",
                    "https://start.radario.ru/"
            )));
            intent.putExtra("ids", new ArrayList<>(Arrays.asList(
                    -131, -132, -133, -134, -135, -136, -137, -138, -139, -140, -141, -142
            )));
            startActivity(intent);
        });
        views.get(2).setOnClickListener(v -> {
            Intent intent = new Intent(this, Partners.class);
            intent.putExtra("title", "Образование");
            intent.putExtra("urls", new ArrayList<>(Arrays.asList(
                    "https://future-leaders.ru/resuorces/others/olA6xFcCOG0lZcbvdQtfBmnT8mZTcYJ7ud03StWe8AROWyryjkJ7Icgrvbi5vvbaDCMbMP6dezppeSlFRCl3gw.png",
                    "https://future-leaders.ru/resuorces/others/J0XJQJeBuXLzakaoFuHF8-9iXnmfKUtw0S0Ih0YoI7OMR4M91T-feorC4lv5kic5Wb6vRDHNcxBjc7FUAPL1yw.png",
                    "https://future-leaders.ru/resuorces/others/753WX8_pmUIMyR4e8g4UmwnGd8Hmgxj_KPoWoz0eVI-m8kYa6zIxsRou93KxJNnf85tOv5o8XumFU7E2iuixHQ.png",
                    "https://future-leaders.ru/resuorces/others/8sDdmdqNQN5cDs6cSdok7_HB27tSEHb7Rhrnp0UY2STmvivZFCMgPHRMsHLewsc0eAg7BK8_SD7xVlixSG3rpA.png",
                    "https://future-leaders.ru/resuorces/others/UJSKwCSpC4p98VL5T5380TIjjF4dajHVTqahNba6sGtdEKFS0PXjHz9MzVvaIGN6quIgCAzLmspF_l3e5eA8ew.png",
                    "https://future-leaders.ru/resuorces/others/syLDHJoiPl-0jeYzQdcziy62O7G7CI8d5xpMyOwYXFdzsoqLXEpjEwjgNjRyyAoQnDmKq3aW0BS85YKiOVZ_iw.png",
                    "https://future-leaders.ru/resuorces/others/boZoKahA_PM4Wyl7P-L4bI-jFjJh63yCxbwP7Y_q5DZT4fS5ofcUOlxoQxaToPRtUUZ2J_efluEIaps99VH7wQ.png"
            )));
            intent.putExtra("names", new ArrayList<>(Arrays.asList(
                    "ПРЕДСЕДАТЕЛЬ|Екатерина Морозова",
                    "ДИРЕКТОР ПО ПРОГРАММАМ|Алексей Малеев",
                    "ОСНОВАТЕЛЬ|Иван Иванов",
                    "ВЕДУЩИЙ ЭКСПЕРТ|Валерия Карпинская",
                    "ПРОФЕССОР|Кирилл Ратников",
                    "ОСНОВАТЕЛЬ|Рубен Варданян",
                    "ДИРЕКТОР ИНТИТУТА|Надежда Сурова"
            )));
            intent.putExtra("url_names", new ArrayList<>(Arrays.asList(
                    "https://future-leaders.ru/resuorces/others/EsLL3YrXLTN75HUy-TgBNXw5R7vVT3AU13MT01HX3og2iNQe0jadgWrplgYx3BnJjwNHAH8kTsCbOarCf86ayA.png",
                    "https://future-leaders.ru/resuorces/others/bs29_cX7TwgIHtWNK-lkNwGYUmqaQfPDcoX5DZhd3aVUS5Or_b_KcMgjy7jLLtIYhYhLcISqv7WHet-ZvzLppA.png",
                    "https://future-leaders.ru/resuorces/others/LKWI0A_WMSa2ZNztdtEx_9ZNU3kxTyrGgW-4RT19umTSyQz8TU_3RZEA5rwiabMySifEDZldhBIhRSZwiaznEg.png",
                    "https://future-leaders.ru/resuorces/others/YFBlFdJkpPMvHq40xi5XfxZdzmM1w1ruP6d9geGfpzmLfcNsgXefYW_ONG6fLEf5XOrocHPnpoM59DH_D9koEw.png",
                    "https://future-leaders.ru/resuorces/others/-m0ClHez3_i1YprU05uTEhK5Vg8uFRzPiSSztLY6PsczR_Ej7osEN60VkhsxlBLjX9eaoc8MyVe5bzwZWQgscw.png",
                    "https://future-leaders.ru/resuorces/others/FU4asEVtl8tUjYrJAQ_k4zdC4gnZFqL46wgQG4Qrog34salokOabfnuZNn74TnDMdiFemPRS-aMZYrZnunUqtA.png",
                    "https://future-leaders.ru/resuorces/others/i4NL-2pLOe4PhFjHQU4yfo5boerT5417lHGBewFLi4ZddK7_XGWm-c1UFMtX_W0gyxVsShGRQvHGMxYVAsXVHA.png"

            )));
            intent.putExtra("redirects", new ArrayList<>(Arrays.asList(
                    "http://www.enap.info/",
                    "https://mipt.ru/english/?fbclid=IwAR1r5B-VLozD7CPCANJL51MXaivaDRwYX_gCrhD2GguoD_W9vOpbyeqsoac",
                    "https://upstudy.ru/",
                    "https://ihb.spb.ru/science/divisions/neuroimaging",
                    "http://www.fa.ru/Pages/Home.aspx",
                    "https://www.skolkovo.ru/?utm_source=yandex&utm_medium=cpc&utm_campaign=2801_Search_Brand_Generic&utm_content=text&utm_term=%D1%81%D0%BA%D0%BE%D0%BB%D0%BA%D0%BE%D0%B2%D0%BE&yclid=4836703274662668792",
                    "https://new2.rea.ru/"
            )));
            intent.putExtra("ids", new ArrayList<>(Arrays.asList(
                    -100, -101, -102, -103, -104, -105, -106
            )));
            startActivity(intent);
        });
        views.get(3).setOnClickListener(v -> {
            Intent intent = new Intent(this, KoychingAndbisnes.class);
            startActivity(intent);
        });
        views.get(4).setOnClickListener(v -> {
            Intent intent = new Intent(this, Partners.class);
            intent.putExtra("title", "Государство и экономика");
            intent.putExtra("urls", new ArrayList<>(Arrays.asList(
                    "https://future-leaders.ru/resuorces/others/VybM7cEfcYnNJ1LepeY1zoTEYNcXsDeW_BTNo9JxzpBOdwPQwAIzndc0bQRbUXZ261iHb8EV4_j4XImk-xdZYA.png",
                    "https://future-leaders.ru/resuorces/others/ovBJVGjUsu2JscCsnZxzHq61xHEuTp4P6dps_A-FxYYU9ne1EcgZu808DTkN8v9v75E7gegmDINrFiAvBFiMxg.png",
                    "https://future-leaders.ru/resuorces/others/hjeLOAhd1rla3sIJpCoYp71gu9pj1o_nKP0DzdLquMr-tkRugACEEh4p-o9r1xMkdOHCma6NIc40LAb7I29N7g.png",
                    "https://future-leaders.ru/resuorces/others/9mQcWLJ1FS7VNBHOL3dOfqsJMz0AhKvVaSt1Tx4ZGlIsPEoRp2k77RbIr-fGj5PaUCU4PWO6eFfQYXsB4woFrA.png",
                    "https://future-leaders.ru/resuorces/others/zNHI7mtO6zoY1pTfxM7yuRPL3cMtrHpeLqofv19U24HqxVlGfuEdiI1QSpoWDVfYoNYJjwT2tPuwVvpwZJfkIQ.png",
                    "https://future-leaders.ru/resuorces/others/snp-Zaf5uGlbWWBzLLxylQXkktjZZOEn7Z_OaoA2wgwrFbHwPzrSS93wjnhTGtqqTwBrv0AFtjRXtqKU91Mn5w.png",
                    "https://future-leaders.ru/resuorces/others/HjfF_P5-qbrg5Ad6RvmlvOTPg27BVu5Ax8flPd8udd0D2EotbDRZXW-_xiemyvI8QyiXfx795xwu4_kN4jGjUw.png",
                    "https://future-leaders.ru/resuorces/others/y7F7lpqA-QPdYuW4FG-2tOruVKBw6367lbAq58KKChtHarEHdL6Vx94Uwhj1jAEnEX8ehxKfitZCu0xIogXGmA.png",
                    "https://future-leaders.ru/resuorces/others/8tfC6a4KvOvD6YwuQ4VlNmJ4lopxaog6kpaHQfcroeeF3HbgK6-ZP6shzB5pkkja2KqOzVHPZCApCOq2mPtdMg.png",
                    "https://future-leaders.ru/resuorces/others/gXRRNwDvIQOsX5AjWc9oJnSwgev7tO33Mx-1xClaDWTKwvLxnBctw_wsLc3W5zDzvKreqQBwH0NIq8lWDSYDZw.png",
                    "https://future-leaders.ru/resuorces/others/pYLYfDTU_kQ46oTCKEzwDrXfQkjW0ws52q4mUyaBSebbe8qNbImamrQPv39a66Dx0-EFDwbCXcGpLJPA6-zz1A.png",
                    "https://future-leaders.ru/resuorces/others/OY2EKWUhuEgi--9ukkiK4HoUGbXLPvC-kuk14RUHnZcXUNo7-ZYgOjVin6JrjbEAbwYOZtc0o0Zv1xmIRdpBNg.png"
            )));
            intent.putExtra("names", new ArrayList<>(Arrays.asList(
                    "ЧЛЕН ОБЩЕСТВЕННОЙ ПАЛАТЫ|Николай Буров",
                    "ЭКСПЕРТ|Ольга Терещенко",
                    "ЗАМЕСТИТЕЛЬ ПРЕДСЕДАТЕЛЯ|Рустем Макулов",
                    "УПРАВЛЯЮЩИЙ ФИЛИАЛОМ|Елена Верёвочкина",
                    "СЕО|Максим Баланев",
                    "ЗАМЕСТИТЕЛЬ ПРЕДСЕДАТЕЛЯ|Вячеслав Калганов",
                    "ПЕРВЫЙ ЗАМЕСТИТЕЛЬ|Кирилл Мясоедов",
                    "ПРЕДСЕДАТЕЛЬ ПРАВЛЕНИЯ|Валентина Галицкая",
                    "ФИНАНСОВЫЙ ДИРЕКТОР|Рустем Зарипов",
                    "ПРЕДСЕДАТЕЛЬ|Владимир Запевалов",
                    "ЗАМ.ПРЕДСЕДАТЕЛЯ|Артём Демин",
                    "ЧЛЕН ОБЩЕСТВЕННОЙ ПАЛАТЫ|Глеб Кузнецов"
            )));
            intent.putExtra("url_names", new ArrayList<>(Arrays.asList(
                    "https://future-leaders.ru/resuorces/others/wQWzfotmFFzRGm-N3TqvpVDP0MqqQ9moXaa0xNTxWYLuTTjnEYocosDGVTVnPE15gX37PV7xQV-INdzz1ZuQ7w.png",
                    "https://future-leaders.ru/resuorces/others/TQNH-Js5lP5cLFPbQPNyJhBoY6tHzZtRava-dx_WjpS1nOqwCKR1gWf6y0u883v0zk6geEjnBc8frtSAjULzJQ.png",
                    "https://future-leaders.ru/resuorces/others/7duyfQsfSVdFZk8ITv0KJI_P1WbPRjyG2dMwJ_p0lXx3GF-65P2MnQix2OaG3ZBh7xkWjowE63yLzG0i1MX1xg.png",
                    "https://future-leaders.ru/resuorces/others/ByYfUAiL721MYwzXcKlNSu1GaiCdo_EQomzyuFs_JIbooNM77uWQ0UF0SbW65EdKMXlZreVkpdhAvu8pM-pmWQ.png",
                    "https://future-leaders.ru/resuorces/others/ze6E8g9kc4yB8-kk2co6kmr5qW2K1yp1IL5l3CEOr9J9-IzVKpoGiyDgmGL_KG_IJuL99BWwwqlDPefR3gvzOg.png",
                    "https://future-leaders.ru/resuorces/others/wOOouiVaCGSX3DJNZJhaY0AMw7djPopMayHQXs8NzJN8TuT2JZyNoEAYG6tWwmDY9AcliZ7ykTB4A5vAFpgubw.png",
                    "https://future-leaders.ru/resuorces/others/t9RTLRTw9Y3hfm-AItyVdIg9Owgltcehx6YYBwAqYtG94q-fBVcxqUk1CjkQDfS-QkEy7743ebnSPex3Spo6aw.png",
                    "https://future-leaders.ru/resuorces/others/jVUrE8ywpRRffapLzDP1Wc0qBy6kU9aNa8kysU0mABRBfxxEeOajE9oxA219b4P8iytgX4sv3AcGh-RFky2XaQ.png",
                    "https://future-leaders.ru/resuorces/others/NW8bom34j9lQ6G2liJMEdErD-ndSjG82mkCoeIHl4XdzUzDcP_zetG84CiKeLsfhCgcLr0g4gWYVsEFFR4M1rQ.png",
                    "https://future-leaders.ru/resuorces/others/O7uKVIUNhwR_wp25lkZyru6TrNmEwRiS6gCtMzYZnXviY8MRPGqRavioGuirWF6hPMzgCt3pfRCaAwzgPqHO0Q.png",
                    "https://future-leaders.ru/resuorces/others/NYZfyjeulwN4oUTTyANk0MskRSuesEvvjPSBkPTq9qSor0rJwkfuP5fipI0r-VSOZgXKeOlJAYtrWCooV-cxMg.png",
                    "https://future-leaders.ru/resuorces/others/Mw7_Yxu9bS8Y2IDrFMouOIs6Pd8ngYZf0MzKIeJ1DPM2xh5uj7geoIVoeWqIda2_XnU7tlQeP3OJj2E8G0j1PQ.png"
            )));
            intent.putExtra("redirects", new ArrayList<>(Arrays.asList(
                    "https://www.op78.ru/",
                    "https://dop.rgiis.ru/",
                    "https://www.bancaintesa.ru/",
                    "https://www.open.ru/roa?migration=rgsb",
                    "http://www.fbd.spb.ru/ru/",
                    "https://kvs.gov.spb.ru/",
                    "https://abr.ru/",
                    "https://www.bankorange.ru/",
                    "https://www.t-systems.com/de/de?fbclid=IwAR1zrg9HAJKjf_wgOabKXhVLeK9lNITacrianQuwGRvJOVz1zVvK1Lye8_s",
                    "https://sankt-peterburg.mid.ru/ru/",
                    "https://www.mos.ru/kos/",
                    "https://www.oprf.ru/"
            )));
            intent.putExtra("ids", new ArrayList<>(Arrays.asList(
                    -111, -112, -113, -114, -115, -116, -117, -118, -119, -120, -121, -122
            )));
            startActivity(intent);
        });
        views.get(5).setOnClickListener(v -> {
            Intent intent = new Intent(this, Partners.class);
            intent.putExtra("title", "Государство и экономика");
            intent.putExtra("urls", new ArrayList<>(Arrays.asList(
                    "https://future-leaders.ru/resuorces/others/uQw9HlW7IbOAdqE78RrG8duyB2Pvs2J2Jg8UOwv4nZGVAbEv_gB9DM34Qwf9hVFmy0H6ufpzLbfVzwFGz9Iw_A.png",
                    "https://future-leaders.ru/resuorces/others/GpjDyUNhJ-w_FebumnlGyU_Ub-ORRTpIEGPMI4okty1bT6hVFbMg9TWHXbmGyEVJq1rRvTVftJodRfNGR2SaDw.png",
                    "https://future-leaders.ru/resuorces/others/5XFaPqRQmqP20mypfy9cRnA1b-FQGG0hSqRtrqgOb_5aHKqaTskfDt9k-UaflIp-uHc4luQ44mKsqSTrE8wq7w.png",
                    "https://future-leaders.ru/resuorces/others/M-9UdA3Jdocc8oywl887vbiOfRAnVdK5FPZYS3WsH48lAKFAWeSbnprKPu02NRNtaLwcGEU-KichVzO0jIgcow.png",
                    "https://future-leaders.ru/resuorces/others/3c0ELvxF2k3qmlB2-IyJGrYQLrzdqbblvkgTcXvkkMKHs2KplJ41oUUqUvn873BvnojeXjVRyzQs_vMylKH_pw.png",
                    "https://future-leaders.ru/resuorces/others/uk74CjXRnBCHBHWR3aF8xc0gTK74aE6B4LPqqoBueap4Buv5kCBE5BjmQNMXbaKc2Vg50C4RAzl7a7aitWoqnA.png",
                    "https://future-leaders.ru/resuorces/others/S2H_10m-_ev43L4i9grdtKeAHMWvZzHQVLfHecNxYHqTPfTjzJS5XqF9EamBN-bF9Of9qzb9ZTR64GFo1XNmLw.png",
                    "https://future-leaders.ru/resuorces/others/Bgfy6vHCQEBJ3efwHrCOL7q-dOfP8ExR3uVvslTVG78YzkzvgQjeDWmSOi3gZohOrAVpAp2rvAOcgmzjEeecGw.png"
            )));
            intent.putExtra("names", new ArrayList<>(Arrays.asList(
                    "PROJECT MANAGER|МАРИАМ АНДРЕАСЯН",
                    "РУКОВОДИТЕЛЬ|АНАТОЛИЙ МОВШОВИЧ",
                    "ЖУРНАЛИСТ|ЕЛЕНА ГАНЖУР",
                    "ПРОДЮСЕР|МАРИЯ КОЛЮБАЕВА",
                    "ДИРЕКТОР|ТАТЬЯНА ПИНЧУК",
                    "ОСНОВАТЕЛЬ|АЛЕКСАНДРА КАРПОВА",
                    "МУЗЫКАЛЬНЫЙ ВЕДУЩИЙ|ИГОРЬ ТИТОВ",
                    "ЗАМЕСТИТЕЛЬ ДИРЕКТОРА|ВЛАДИМИР ОПРЕДЕЛЕНОВ"
            )));
            intent.putExtra("url_names", new ArrayList<>(Arrays.asList(
                    "https://future-leaders.ru/resuorces/others/5amwoDci9qgWFM5k4QcPH4q_C7BV9XZ-kWM89QN3VGNvRipcENJxPsTvOdQP56UtNe0HugLmOksznKq5_FEJMQ.png",
                    "https://future-leaders.ru/resuorces/others/pT7YZmisAwRZkxjk1L7sXYCLz5qwhjBZwe3A2BVAVPuIWIteKXja5IX6dF1pyWfpi_r07-Yxm4jdpRFuVukPUQ.png",
                    "https://future-leaders.ru/resuorces/others/JGPZ_FzePHTNfBzT0FB4j77UtdHhWgc38iuHTt2DmenEt3uqHWFUgvN6c1QC9Ze7TGqSASvNhItdDHWeZOPFsw.png",
                    "https://future-leaders.ru/resuorces/others/yeZCxxm7AUAq31Uif7pW7SEl1VAmHQl00UN2VpbcMOIcGezjqcsRbSkjXYLk3BJJKZU6qKUphANU5u0dOm4yDw.png",
                    "https://future-leaders.ru/resuorces/others/jaPb9K_5Z5FnOF1CjQrRkZWsGU2r3WjFZSzDko8nh-DF03QdMbFH_l5M3elnfCFVg9CmZMUASN5TGy_HSEL1Uw.png",
                    "https://future-leaders.ru/resuorces/others/ZkbvsVI0F8sSbIT9wBXkkFFQ5tHUEDlNPY35qkFWfX8ftlunVGa0tYRITyxXojle4k1CP2B-E-KmHBm-ypDnJA.png",
                    "https://future-leaders.ru/resuorces/others/8-MecLRCYL59at4Y5UgyijPLk5KGnoakjKmlPD8Pn-rdruDEXGYBPC04gt34FKXfioILGH-v5xfpciDJv4Hwxg.png",
                    "https://future-leaders.ru/resuorces/others/TJZf3j_Lx7W6fyhiXXAZKyXA6bHSwofFvPGPbHnMEfBL4vunBMAMroKYm_YDm-vwwMcQm5-0JkWcR6CGi6BNJg.png"
            )));
            intent.putExtra("redirects", new ArrayList<>(Arrays.asList(
                    "https://polymus.ru/ru/",
                    "https://touch-world.ru/",
                    "https://www.forbes.ru/biznes/434929-virtualnaya-probirka-kak-cifrovaya-laboratoriya-analizov-lifetime-vyrosla-v-desyat-raz",
                    "https://www.storylab.com/?fbclid=IwAR1G-ct784SUUa8onSc_Bqm_SXZtB-MUDLCBZwFZVEYlZVGGzdVbwP6t-mI",
                    "https://streetartmuseum.ru/",
                    "https://www.instagram.com/nakedcarp/",
                    "https://rusradio.ru/",
                    "https://www.pushkinmuseum.art/"
            )));
            intent.putExtra("ids", new ArrayList<>(Arrays.asList(
                    -123, -124, -125, -126, -127, -128, -129, -130
            )));
            startActivity(intent);
        });
        setTitle("Наставники");
    }
}