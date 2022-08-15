package me.solo_team.futureleader.API;
/**
 * Enum класс со всеми (почти) методами и их названиями.
 * Сделал так ибо так хайпово
 *
 * @author Фома
 *
 */
public enum Methods {
    // TODO: ТУТ ТОЛЬКО ДОБАВЛЯТЬ НОВЫЕ МОТОДЫ ТАК ЖЕ КАК И ОСТАЛЬНЫЕ, НЕ БОЛЕЕ!
    REGISTER_USER("register_user"),
    LOGIN_USER("login_user"),
    GET_NEWS("get_news"),
    UPD_PROFILE_PICTURE("update_profile_picture"),
    UPD_FIELD("update_field"),
    UPD_STATUS("update_status"),
    UPD_ADMIN_ROLE("update_admin_role"),
    GET_ACHIEVEMENT("get_achievement"),
    GET_USERS("get_users"),
    GET_STRUCTURE("get_structure"),
    GET_NEW("get_new"),
    ADD_NEW("add_new"),
    UPL_IMAGE("upload_image"),
    GET_YT_LOGO("get_yt_logo"),
    GET_IDEAS("get_ideas"),
    CREATE_IDEA("create_idea"),
    SEND_VIDEO("send_video"),

    ADD_EVENT("add_event"),
    GET_EVENTS_DATE("get_events_date"),
    DELETE_EVENT("delete_event"),
    GET_EVENTS("get_events"),
    ADD_TICKET("create_ticket_to_event"),

    GET_MUSICS("get_musics"),
    ADD_MUSIC("add_audio"),
    ADD_MUSIC_PHOTO("add_music_photo"),

    GET_SURVEYS("get_surveys"),
    ADD_ANSWERS("add_survey_answers"),

    GET_SHOP("get_shop"),
    ADD_SHOP_REQUEST("add_shop_request"),

    GET_NEAREST_DR("get_nearest_birthday_people"),

    GET_CHATS("get_chats"),
    GET_CHAT("get_chat"),
    CREATE_DIALOG("create_dialog"),
    SEND_MESSAGE("send_message"),
    CHANGE_CHAT_IMAGE("change_chat_image"),
    CREATE_CHAT("create_chat"),
    CHANGE_CHAT_TITLE("change_chat_name"),

    UPPLOAD_APPLICATION("add_application"),
    ADD_DOCX_FILE("add_docx_file"),

    GET_SURVEYS_FOR_ADMIN("get_surveys_admin"),
    DELETE_SURVEYS("delete_survey"),
    CREATE_SURVEYS("create_surveys"),
    GET_SURVEYS_STAT("get_complete_surveys"),

    GET_WEITED_IDEAS("get_waited_ideas"),
    SET_IDEAS_STATUS("set_idea_status"),

    GET_UNVERIFIED_USERS("get_unverified_users_by_admin"),
    VERIFI_USER("verifi_user"),

    GET_ALLSTAT("get_login_statistic"),

    GET_FILES_LOGS("get_files_logs"),
    GET_LOGINS_LOGS("get_login_logs"),

    GET_SHOP_REQUESTS("get_shop_requests"),
    SET_SHOP_REQUESTS("set_shop_request"),
    GET_SHOP_HISTORY("get_shop_history"),
    SET_ADUIO_LIKE("set_like_audio"),
    SEARCH_by_name("search_music_by_name"),

    GET_USER("get_user");


    public String label;

    Methods(String label){
        this.label = label;
    }
}
