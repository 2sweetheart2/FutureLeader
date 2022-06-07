package me.solo_team.futureleader.API;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import me.solo_team.futureleader.Objects.CustomString;

/**
 * класс для отправки запросов
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
     * @param listener new {@link ApiListener}
     * @param params   {@link CustomString} через запятую
     */
    public static void registerUser(ApiListener listener, CustomString... params) {
        HTTPS.sendPost(Methods.REGISTER_USER, Objects.requireNonNull(createJsonObj(params)), listener);
    }

    /**
     * метод авторизации userA
     *
     * @param listener new {@link ApiListener}
     * @param params   {@link CustomString} через запятую
     */
    public static void loginUser(ApiListener listener, CustomString... params) {
        HTTPS.sendPost(Methods.LOGIN_USER, Objects.requireNonNull(createJsonObj(params)), listener);
    }

    public static void getNews(ApiListener listener, CustomString... params) {
        HTTPS.sendPost(Methods.GET_NEWS, Objects.requireNonNull(createJsonObj(params)), listener);
    }

    public static void uploadProfilePicture(ApiListener listener, Bitmap photo, CustomString... params) {
        HTTPS.u(Methods.UPD_PROFILE_PICTURE, Objects.requireNonNull(createJsonObj(params)), photo, listener);
    }

    public static void updateFields(ApiListener listener, CustomString... fields) {
        HTTPS.sendPost(Methods.UPD_FIELDS, Objects.requireNonNull(createJsonObj(fields)), listener);
    }

    public static void getAchivement(ApiListener listener, CustomString... ids) {
        HTTPS.sendPost(Methods.GET_ACHIEVEMENT, Objects.requireNonNull(createJsonObj(ids)), listener);
    }

    public static void getUsers(ApiListener listener, CustomString... params) {
        HTTPS.sendPost(Methods.GET_USERS, Objects.requireNonNull(createJsonObj(params)), listener);
    }

    public static void getStructure(ApiListener listener, CustomString... params) {
        HTTPS.sendPost(Methods.GET_STRUCTURE, Objects.requireNonNull(createJsonObj(params)), listener);
    }

    public static void getNew(ApiListener listener, CustomString... params) {
        HTTPS.sendPost(Methods.GET_NEW, Objects.requireNonNull(createJsonObj(params)), listener);
    }

    public static void addNew(ApiListener listener, CustomString... params) {
        HTTPS.sendPost(Methods.ADD_NEW, Objects.requireNonNull(createJsonObj(params)), listener);
    }

    public static void addNew(ApiListener listener, JSONObject params) {
        HTTPS.sendPost(Methods.ADD_NEW, params, listener);
    }

    public static void uploadImage(ApiListener listener, Bitmap bitmap, CustomString... params){
        HTTPS.u(Methods.UPL_IMAGE, Objects.requireNonNull(createJsonObj(params)), bitmap, listener);

    }

}
