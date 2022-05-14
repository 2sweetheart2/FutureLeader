package me.solo_team.futureleader.API;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import me.solo_team.futureleader.Objects.CustomString;

/**
 * класс для отправки запросов
 *
 */
public class API {

    /**
     * метод для создания из {@link CustomString} единого {@link JSONObject}
     *
     * @param params {@link CustomString}
     * @return {@link JSONObject}
     */
    private static JSONObject createJsonObj(CustomString... params) {
        try {
            JSONObject jsonObject = new JSONObject();
            for (CustomString s : params) {
                jsonObject.put(s.name, s.value);
            }
            return jsonObject;
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * метод регистрации userА
     *
     * @param listener new {@link ApiHandler}
     * @param params   {@link CustomString} через запятую
     */
    public static void registerUser(ApiHandler listener, CustomString... params) {
        HTTPS.sendPost(Methods.REGISTER_USER, Objects.requireNonNull(createJsonObj(params)), listener);
    }

    /**
     * метод авторизации userA
     * @param listener new {@link ApiHandler}
     * @param params    {@link CustomString} через запятую
     */
    public static void loginUser(ApiHandler listener, CustomString... params) {
        HTTPS.sendPost(Methods.LOGIN_USER, Objects.requireNonNull(createJsonObj(params)), listener);
    }

}
