package me.solo_team.futureleader.API;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import me.solo_team.futureleader.R;

/**
 * Обработчик события прихода сообщения
 *
 * @author Фома
 */
// TODO: ТУТ НИЧЕГО НЕ МЕНЯТЬ!
public interface ApiListener {
    default void process(JSONObject jsonObject) throws JSONException {
        if (jsonObject.has("error"))
            onError(jsonObject.getJSONObject("error"));

        else
            try {
                if(jsonObject.isNull("response")) onSuccess(null);
                else onSuccess(jsonObject.getJSONObject("response"));
            }catch (JSONException e){
                e.printStackTrace();
            }
    }

    void onError(JSONObject json) throws JSONException;

    void inProcess();

    void onSuccess(JSONObject json) throws JSONException;

    default void createNotification(View v,String message) {
        try {
            Snackbar.make(v, message, Snackbar.LENGTH_LONG)
                    .setAction("CLOSE", view -> {
                    })
                    .setActionTextColor(Color.RED)
                    .show();
        } catch (Exception ignored){}
    }
    default Dialog openWaiter(Context context){
        Dialog d = new Dialog(context);
        d.setContentView(R.layout.wait_layout);
        d.show();
        d.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        d.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        //vf.getWindow().setBackgroundBlurRadius(15);
        d.setCancelable(false);
        return d;
    }
}
