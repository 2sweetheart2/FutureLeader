package me.solo_team.futureleader.API;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
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
        HTTPS.sendPost(Methods.UPD_FIELD, Objects.requireNonNull(createJsonObj(fields)), listener);
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

    public static void uploadImage(ApiListener listener, Bitmap bitmap, CustomString... params) {
        HTTPS.u(Methods.UPL_IMAGE, Objects.requireNonNull(createJsonObj(params)), bitmap, listener);

    }


    public static void getUser(ApiListener listener, CustomString... params) {
        HTTPS.sendPost(Methods.GET_USER, Objects.requireNonNull(createJsonObj(params)), listener);
    }

    public static void getIdeas(ApiListener listener, CustomString... params) {
        HTTPS.sendPost(Methods.GET_IDEAS, Objects.requireNonNull(createJsonObj(params)), listener);
    }

    public static void sendVideo(ApiListener listener, File video, CustomString... params) {
        HTTPS.sendVideoFile(Methods.SEND_VIDEO, createJsonObj(params), video, listener);
    }

    public static void getEventsDate(ApiListener listener, CustomString...params){
        HTTPS.sendPost(Methods.GET_EVENTS_DATE,createJsonObj(params),listener);
    }
    public static void getEvents(ApiListener listener, CustomString...params){
        HTTPS.sendPost(Methods.GET_EVENTS,createJsonObj(params),listener);
    }
    public static void addEvents(ApiListener listener, CustomString...params){
        HTTPS.sendPost(Methods.ADD_EVENT,createJsonObj(params),listener);
    }
    public static void deleteEvent(ApiListener listener, CustomString...params){
        HTTPS.sendPost(Methods.DELETE_EVENT,createJsonObj(params),listener);
    }
    public static void createTicket(ApiListener listener, CustomString...params){
        HTTPS.sendPost(Methods.ADD_TICKET,createJsonObj(params),listener);
    }
    public static void getSurveys(ApiListener listener, CustomString...params){
        HTTPS.sendPost(Methods.GET_SURVEYS,createJsonObj(params),listener);
    }
    public static void addAnser(ApiListener listener, CustomString...params){
        HTTPS.sendPost(Methods.ADD_ANSWERS,createJsonObj(params),listener);
    }
    public static void getNearestDr(ApiListener listener, CustomString...params){
        HTTPS.sendPost(Methods.GET_NEAREST_DR,createJsonObj(params),listener);
    }
}
