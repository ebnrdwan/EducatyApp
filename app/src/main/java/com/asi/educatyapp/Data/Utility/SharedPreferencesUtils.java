package com.asi.educatyapp.Data.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {
    public static String getCurrentStudent(Context context) {
        SharedPreferences PREF = context.getSharedPreferences(Constants.PREF, Activity.MODE_PRIVATE);
        return PREF.getString(Constants.SKEY, "");
    }


    public static void setCurrentStudent(Context context, String uid) {
        SharedPreferences PREF = context.getSharedPreferences(Constants.PREF, Activity.MODE_APPEND);
        SharedPreferences.Editor editor = PREF.edit();
        editor.putString(Constants.SKEY, uid);
        editor.commit();
    }
    public static void setTypeOfCurrentUser(Context context, String uid) {
        SharedPreferences PREF = context.getSharedPreferences(Constants.PREF, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = PREF.edit();
        editor.putString(Constants.TYPE_KEY, uid);
        editor.commit();
    }
    public static String getTypeOfCurrentUser(Context context) {
        SharedPreferences PREF = context.getSharedPreferences(Constants.PREF, Activity.MODE_PRIVATE);
        return PREF.getString(Constants.TYPE_KEY, "");
    }
    public static void removeTypeOfCurrentUser(Context context) {
        SharedPreferences PREF = context.getSharedPreferences(Constants.PREF, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = PREF.edit();
        editor.remove(Constants.TYPE_KEY);
        editor.apply();
    }

    public static void removeCurrentStudent(Context context) {
        SharedPreferences PREF = context.getSharedPreferences(Constants.PREF, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = PREF.edit();
        editor.remove(Constants.SKEY);
        editor.apply();
    }

    public static String getCurrentTeacher(Context context) {
        SharedPreferences PREF = context.getSharedPreferences(Constants.PREF, Activity.MODE_PRIVATE);
        return PREF.getString(Constants.TKEY, "");
    }

    public static void setCurrentTeacher(Context context, String uid) {
        SharedPreferences PREF = context.getSharedPreferences(Constants.PREF, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = PREF.edit();
        editor.putString(Constants.TKEY, uid);
        editor.commit();
    }

    public static void removeCurrentTeacher(Context context) {
        SharedPreferences PREF = context.getSharedPreferences(Constants.PREF, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = PREF.edit();
        editor.remove(Constants.SKEY);
        editor.apply();
    }

    public static String getCurrentGroupKey(Context context) {
        SharedPreferences PREF = context.getSharedPreferences(Constants.PREF, Activity.MODE_PRIVATE);
        return PREF.getString(Constants.GKEY, "");
    }

    public static void setCurrentGroupKey(Context context, String GroupKey) {
        SharedPreferences PREF = context.getSharedPreferences(Constants.PREF, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = PREF.edit();
        editor.putString(Constants.GKEY, GroupKey);
        editor.commit();
    }

    public static void removeCurrentGroupKey(Context context) {
        SharedPreferences PREF = context.getSharedPreferences(Constants.PREF, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = PREF.edit();
        editor.remove(Constants.GKEY);
        editor.apply();
    }
}
