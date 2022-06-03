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
    UPD_FIELDS("update_fields"),
    UPD_STATUS("update_status"),
    UPD_ADMIN_ROLE("update_admin_role"),
    GET_ACHIEVEMENT("get_achievement"),
    GET_USERS("get_users"),
    GET_STRUCTURE("get_structure");


    public String label;

    Methods(String label){
        this.label = label;
    }
}
