package me.solo_team.futureleader.API;

import android.graphics.Color;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Обработчик события прихода сообщения
 *
 * @author Фома
 */
// TODO: ТУТ НИЧЕГО НЕ МЕНЯТЬ!
public interface FullApiListener {

    default void process(JSONObject jsonObject) throws JSONException {
        onFinish();
        if (jsonObject.has("error"))
            onError(jsonObject.getJSONObject("error"));
        else
            onSuccess(jsonObject.getJSONObject("response"));
    }

    void inProgress();

    void onFinish();

    void onError(JSONObject json);

    void onSuccess(JSONObject json);

    default void createNotification(View v, String message) {
        Snackbar.make(v, message, Snackbar.LENGTH_LONG)
                .setAction("CLOSE", view -> {
                })
                .setActionTextColor(Color.RED)
                .show();
    }
}