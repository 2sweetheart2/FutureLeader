package me.solo_team.futureleader.API;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;
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

    private static JSONObject createJsonObj(List<CustomString> params) {
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

    public static void updateFields(ApiListener listener, List<CustomString> fields) {
        HTTPS.sendPost(Methods.UPD_FIELD, Objects.requireNonNull(createJsonObj(fields)), listener);
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


    public static void addNew(ApiListener listener, JSONObject params) {
        HTTPS.sendPost(Methods.ADD_NEW, params, listener);
    }

    public static void uploadImage(ApiListener listener, Bitmap bitmap, CustomString... params) {
        HTTPS.u(Methods.UPL_IMAGE, Objects.requireNonNull(createJsonObj(params)), bitmap, listener);
    }

    public static void addMusicPhoto(ApiListener listener, Bitmap bitmap, CustomString... params) {
        HTTPS.u(Methods.ADD_MUSIC_PHOTO, Objects.requireNonNull(createJsonObj(params)), bitmap, listener);
    }


    public static void getIdeas(ApiListener listener, CustomString... params) {
        HTTPS.sendPost(Methods.GET_IDEAS, Objects.requireNonNull(createJsonObj(params)), listener);
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
    public static void getMusics(ApiListener listener, CustomString...params){
        HTTPS.sendPost(Methods.GET_MUSICS,createJsonObj(params),listener);
    }
    public static void addMusic(ApiListener listener, byte[] bytes, String name, CustomString...params){
        HTTPS.sendAudio(Methods.ADD_MUSIC,createJsonObj(params),bytes,name,listener);
    }
    public static void getShop(ApiListener listener, CustomString...params){
        HTTPS.sendPost(Methods.GET_SHOP,createJsonObj(params),listener);
    }
    public static void addShopOrder(ApiListener listener, CustomString...params){
        HTTPS.sendPost(Methods.ADD_SHOP_REQUEST, createJsonObj(params),listener);
    }


    public static void getChats(ApiListener listener, CustomString...params){
        HTTPS.sendPost(Methods.GET_CHATS,createJsonObj(params),listener);
    }
    public static void getChat(ApiListener listener, CustomString...params){
        HTTPS.sendPost(Methods.GET_CHAT,createJsonObj(params),listener);
    }
    public static void sendMessage(ApiListener listener, CustomString...params){
        HTTPS.sendPost(Methods.SEND_MESSAGE,createJsonObj(params),listener);
    }
    public static void updateChatImage(ApiListener listener, Bitmap bitmap, CustomString...params){
        HTTPS.u(Methods.CHANGE_CHAT_IMAGE,createJsonObj(params),bitmap,listener);
    }
    public static void createChat(ApiListener listener, JSONObject object){
        HTTPS.sendPost(Methods.CREATE_CHAT,object,listener);
    }
    public static void changeChatTitle(ApiListener listener, CustomString...params){
        HTTPS.sendPost(Methods.CHANGE_CHAT_TITLE,createJsonObj(params),listener);
    }
    public static void uploadApplication(ApiListener listener,JSONObject object){
        HTTPS.sendPost(Methods.UPPLOAD_APPLICATION,object,listener);
    }
    public static void addDOCXFile(ApiListener listener, byte[] bytes, String name, CustomString...params){
        HTTPS.sendAudio(Methods.ADD_DOCX_FILE,createJsonObj(params),bytes,name,listener);
    }
    public static void getSurveysAdmin(ApiListener listener,CustomString...params){
        HTTPS.sendPost(Methods.GET_SURVEYS_FOR_ADMIN,createJsonObj(params),listener);
    }

    public static void deleteSurvey(ApiListener listener, CustomString... params) {
        HTTPS.sendPost(Methods.DELETE_SURVEYS, createJsonObj(params), listener);
    }

    public static void createSurveys(ApiListener listener, JSONObject object) {
        HTTPS.sendPost(Methods.CREATE_SURVEYS, object, listener);
    }

    public static void getSurveysAdminStaticstic(ApiListener listener, CustomString... params) {
        HTTPS.sendPost(Methods.GET_SURVEYS_STAT, createJsonObj(params), listener);
    }

    public static void getWaitedIdeas(ApiListener listener, CustomString... params) {
        HTTPS.sendPost(Methods.GET_WEITED_IDEAS, createJsonObj(params), listener);
    }

    public static void setIdeasStatus(ApiListener listener, JSONObject params) {
        HTTPS.sendPost(Methods.SET_IDEAS_STATUS, params, listener);
    }

    public static void createIdea(ApiListener listener, CustomString... params) {
        HTTPS.sendPost(Methods.CREATE_IDEA, createJsonObj(params), listener);
    }

    public static void getUnverifiedUsers(ApiListener listener, CustomString... params) {
        HTTPS.sendPost(Methods.GET_UNVERIFIED_USERS, createJsonObj(params), listener);
    }

    public static void verifiUser(ApiListener listener, CustomString... params) {
        HTTPS.sendPost(Methods.VERIFI_USER, createJsonObj(params), listener);
    }

    public static void getFilesLogs(ApiListener listener, CustomString... params) {
        HTTPS.sendPost(Methods.GET_FILES_LOGS, createJsonObj(params), listener);
    }

    public static void getLoginLogs(ApiListener listener, CustomString... params) {
        HTTPS.sendPost(Methods.GET_LOGINS_LOGS, createJsonObj(params), listener);
    }

    public static void getUser(ApiListener listener, CustomString... params) {
        HTTPS.sendPost(Methods.GET_USER, createJsonObj(params), listener);
    }

    public static void getAllStat(ApiListener listener, CustomString... params) {
        HTTPS.sendPost(Methods.GET_ALLSTAT, createJsonObj(params), listener);
    }

    public static void getShopRequests(ApiListener listener, CustomString... params) {
        HTTPS.sendPost(Methods.GET_SHOP_REQUESTS, createJsonObj(params), listener);
    }

    public static void setShopRequests(ApiListener listener, CustomString... params) {
        HTTPS.sendPost(Methods.SET_SHOP_REQUESTS, createJsonObj(params), listener);
    }

    public static void getShopHistory(ApiListener listener, CustomString... params) {
        HTTPS.sendPost(Methods.GET_SHOP_HISTORY, createJsonObj(params), listener);
    }

    public static void setAduioLike(ApiListener listener, CustomString... params) {
        HTTPS.sendPost(Methods.SET_ADUIO_LIKE, createJsonObj(params), listener);
    }
    public static void searchMusicByName(ApiListener listener, CustomString... params){
        HTTPS.sendPost(Methods.SEARCH_by_name, createJsonObj(params), listener);
    }
    public static void addVideoFile(ApiListener listener, byte[] bytes, String name, CustomString...params){
        HTTPS.sendAudio(Methods.ADD_VIDEO_FILE,createJsonObj(params),bytes,name,listener);
    }
    public static void addVideo(ApiListener listener,CustomString...params){
        HTTPS.sendPost(Methods.ADD_VIDEO,createJsonObj(params),listener);
    }

    public static void getAchievements(ApiListener listener, CustomString...params){
        HTTPS.sendPost(Methods.GET_ACHIEVEMENTS,createJsonObj(params),listener);
    }
    public static void addUserAchievement(ApiListener listener,CustomString...params){
        HTTPS.sendPost(Methods.ADD_ACHIEVEMENT_USER,createJsonObj(params),listener);
    }
    public static void removeAchievement(ApiListener listener,CustomString...params){
        HTTPS.sendPost(Methods.REMOVE_ACHIEVEMENT,createJsonObj(params),listener);
    }
    public static void createAchievement(ApiListener listener,JSONObject params){
        HTTPS.sendPost(Methods.CREATE_ACHIEVEMENT,params,listener);
    }

    public static void getUsersRole(ApiListener listener,CustomString...params) {
        HTTPS.sendPost(Methods.GET_ROLES,createJsonObj(params),listener);
    }

    public static void setUserRole(ApiListener listener,CustomString...params){
        HTTPS.sendPost(Methods.SET_ROLE_FOR_USER,createJsonObj(params),listener);
    }

    public static void removeRole(ApiListener listener,CustomString...params){
        HTTPS.sendPost(Methods.REMOVE_ROLE,createJsonObj(params),listener);
    }
    public static void updateRole(ApiListener listener,CustomString... params){
        HTTPS.sendPost(Methods.UPDATE_ROLE,createJsonObj(params),listener);
    }
    public static void createRole(ApiListener listener,List<CustomString> params){
        HTTPS.sendPost(Methods.CREATE_ROLE,createJsonObj(params),listener);
    }
    public static void getWhoHasThisRole(ApiListener listener,CustomString...params){
        HTTPS.sendPost(Methods.GET_WHO_HAS_THIS_ROLE,createJsonObj(params),listener);
    }

    public static void getTickets(ApiListener listener, CustomString...params) {
        HTTPS.sendPost(Methods.GET_TICKETS,createJsonObj(params),listener);
    }
}
