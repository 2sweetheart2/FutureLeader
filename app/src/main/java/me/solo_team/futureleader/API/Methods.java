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
    SEND_VIDEO("send_video"),

    ADD_EVENT("add_event"),
    GET_EVENTS_DATE("get_events_date"),
    DELETE_EVENT("delete_event"),
    GET_EVENTS("get_events"),
    ADD_TICKET("create_ticket_to_event"),

    GET_SURVEYS("get_surveys"),
    ADD_ANSWERS("add_survey_answers"),


    GET_USER("get_user");


    public String label;

    Methods(String label){
        this.label = label;
    }
}
