package me.solo_team.futureleader;

import android.content.Context;
import android.content.SharedPreferences;

public class CSettings {

    private final SharedPreferences sp;
    public CSettings(Context context){
        sp = context.getSharedPreferences("settings",Context.MODE_PRIVATE);
    }

    public boolean getStartChecker(){
        return sp.getBoolean("start_checker",false);
    }

    public void saveStartChecker(boolean state){
        sp.edit().putBoolean("start_checker",state).apply();
    }

    public void saveUserInfo(String password, String email){
        sp.edit().putString("user_password",password).putString("user_email",email).apply();
    }
    public String getUserPassword(){
        return sp.getString("user_password",null);
    }
    public String getUserEmail(){
        return sp.getString("user_email",null);
    }

    public boolean getStartScreen(){
        return sp.getBoolean("start_screen",true);
    }
    public void setStartScreen(boolean state){
        sp.edit().putBoolean("start_screen",state).apply();
    }

    public void removeUserInfo() {
        sp.edit().remove("user_password").remove("user_email").apply();
    }
}
