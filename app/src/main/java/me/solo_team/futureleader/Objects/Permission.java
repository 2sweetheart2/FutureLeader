package me.solo_team.futureleader.Objects;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Permission implements Serializable {
    public boolean can_add_new = false;
    public boolean can_view_new_views = false;
    public boolean can_view_like_views = false;
    public boolean can_view_admin_panel = false;
    public boolean can_get_ideas = false;
    public boolean can_edit_profile = false;
    public boolean can_set_ideas_status = false;
    public boolean can_get_users = false;
    public boolean can_get_user = false;
    public boolean can_get_admin_roles = false;
    public boolean can_chage_admin_role = false;
    public boolean can_get_reg_log = false;
    public boolean can_get_log_log = false;
    public boolean can_get_file_log = false;
    public boolean can_view_shop_request = false;
    public boolean can_set_shop_request = false;
    public boolean can_get_unverified_users = false;
    public boolean can_verifi_user = false;
    public boolean can_get_stat = false;
    public boolean can_create_achievement = false;
    public boolean can_remove_achievement = false;
    public boolean can_add_achievement_user = false;
    public boolean can_get_complete_surveys = false;
    public boolean can_delete_survey = false;
    public boolean can_create_surveys = false;
    public boolean can_delete_event = false;
    public boolean can_add_event = false;
    public boolean can_create_admin_role = false;
    public boolean can_remove_role = false;
    public boolean can_get_who_has_this_role = false;
    public boolean can_update_role = false;
    public boolean can_get_events_tickets = false;
    public boolean can_get_all_currency = false;
    public boolean can_remove_unverifi_users = false;
    public boolean can_add_currency = false;
    public boolean can_add_mentors = false;

    public String name;
    public int id;

    public Permission(JSONObject perm) {
        try {
            can_add_mentors = intToBoolean(perm.getInt("can_add_mentors"));
            can_edit_profile = intToBoolean(perm.getInt("can_edit_profile"));
            can_get_all_currency = intToBoolean(perm.getInt("can_get_all_currency"));
            can_add_currency = intToBoolean(perm.getInt("can_add_currency"));
            can_remove_unverifi_users = intToBoolean(perm.getInt("can_remove_unverifi_users"));
            can_add_new = intToBoolean(perm.getInt("can_add_new"));
            can_view_new_views = intToBoolean(perm.getInt("can_view_new_views"));
            can_view_like_views = intToBoolean(perm.getInt("can_view_like_views"));
            can_view_admin_panel = intToBoolean(perm.getInt("can_view_admin_panel"));
            can_get_ideas = intToBoolean(perm.getInt("can_get_ideas"));
            can_set_ideas_status = intToBoolean(perm.getInt("can_set_ideas_status"));
            can_get_users = intToBoolean(perm.getInt("can_get_users"));
            can_get_user = intToBoolean(perm.getInt("can_get_user"));
            can_get_admin_roles = intToBoolean(perm.getInt("can_get_admin_roles"));
            can_chage_admin_role = intToBoolean(perm.getInt("can_chage_admin_role"));
            can_get_reg_log = intToBoolean(perm.getInt("can_get_reg_log"));
            can_get_log_log = intToBoolean(perm.getInt("can_get_log_log"));
            can_get_file_log = intToBoolean(perm.getInt("can_get_file_log"));
            can_view_shop_request = intToBoolean(perm.getInt("can_view_shop_request"));
            can_get_unverified_users = intToBoolean(perm.getInt("can_get_unverified_users"));
            can_set_shop_request = intToBoolean(perm.getInt("can_set_shop_request"));
            can_verifi_user = intToBoolean(perm.getInt("can_verifi_user"));
            can_get_stat = intToBoolean(perm.getInt("can_get_stat"));
            can_create_achievement = intToBoolean(perm.getInt("can_create_achievement"));
            can_remove_achievement = intToBoolean(perm.getInt("can_remove_achievement"));
            can_add_achievement_user = intToBoolean(perm.getInt("can_add_achievement_user"));
            can_get_complete_surveys = intToBoolean(perm.getInt("can_get_complete_surveys"));
            can_delete_survey = intToBoolean(perm.getInt("can_delete_survey"));
            can_create_surveys = intToBoolean(perm.getInt("can_create_surveys"));
            can_delete_event = intToBoolean(perm.getInt("can_delete_event"));
            can_add_event = intToBoolean(perm.getInt("can_add_event"));
            can_create_admin_role = intToBoolean(perm.getInt("can_create_admin_role"));
            can_remove_role = intToBoolean(perm.getInt("can_remove_role"));
            can_get_who_has_this_role = intToBoolean(perm.getInt("can_get_who_has_this_role"));
            can_update_role = intToBoolean(perm.getInt("can_update_role"));
            can_get_events_tickets = intToBoolean(perm.getInt("can_get_events_tickets"));
            if (perm.has("name"))
                name = perm.getString("name");
            if (perm.has("id"))
                id = perm.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Boolean intToBoolean(int bool) {
        return bool == 1;
    }
}