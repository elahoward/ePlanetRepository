package com.ejh.eplanetsapp.Util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    SharedPreferences sp;
    public Prefs (Activity activity){

        sp= activity.getPreferences(Activity.MODE_PRIVATE);
    }
    public void setSearch(String search) {

        sp.edit().putString("search", search).commit();
    }
    public String getSearch(){

        return sp.getString("search", "Earth");
    }

}
