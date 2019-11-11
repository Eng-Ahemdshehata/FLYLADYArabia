package com.ashehata.flyladyarabia.data.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;


public class AppPreference {
    private static SharedPreferences sharedPreferences ;

    public static final String SKIP_KEY = "skip";

    public static final String NOTIFICATION_KEY = "notification";

    public static final String NOTIFICATION_SOUND_KEY = "notification_sound";

    public static final String LANGUAGE_KEY = "lang";

    public static final String DEFAULT_LANGUAGE_KEY = "ar";


    public static final String  USER_NAME = "user_name";

    public static final String DO_NOT_SHOW_MESSAGE = "message";

    public static final String USER_ICON = "user_icon" ;

    public static final String MY_PREVIOUS_DATE = "previous_date" ;






    public static boolean readBoolean(Context context, String key) {

         sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        boolean b = sharedPreferences.getBoolean(key,false);

        return b ;
    }

    public static void writeBoolean(String key , boolean b ){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, b);
        editor.commit();
    }


    public static String readString(Context context, String key,String defaultValue) {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        String b = sharedPreferences.getString(key,defaultValue);

        return b ;
    }

    public static void writeString( String key , String value) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();

    }

    public static int readInt(Context context, String key,int defaultValue) {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        int b = sharedPreferences.getInt(key,defaultValue);

        return b ;
    }

    public static void writeInt( String key , int value) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();

    }
    public static void writeDate( String key , Date value) {

        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(value);
        editor.putString(key, json);
        Log.v("calender Preference",value.toString()+"");


        editor.commit();

    }

    public static Date readDate(Context context , String key ) {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        Gson gson = new Gson();
        String json = sharedPreferences.getString(key, "");
        Date obj = gson.fromJson(json, Date.class);
//        Log.v("calender Pref load",obj.toString()+"");


        return obj ;

    }








}
