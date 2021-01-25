package com.ydh.budayabyl.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SharedPrefHelper(private val context: Context) {


    private val pref: SharedPreferences by lazy {
        context.getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE)
    }

    private val notifTokenKey = "NOTIF_TOKEN_KEY"
    private val uidKey = "UID_KEY"

    var token: String?
        get() = pref.getString(Constant.PREF_TOKEN, "") ?: ""
        set(value) = pref.edit { putString(Constant.PREF_TOKEN, value) }

    var isLogin: Boolean
        get() = pref.getBoolean(Constant.PREF_IS_LOGIN, false)
        set(value) = pref.edit { putBoolean(Constant.PREF_IS_LOGIN, value) }

    var notifToken: String?
        get() = pref.getString(notifTokenKey, "") ?:""
        set(value) = pref.edit { putString(notifTokenKey, value) }

    var uid: String?
        get() = pref.getString(uidKey, "")?:""
        set(value) = pref.edit{ putString(uidKey, value) }

    fun onClear() {
        pref.edit().clear().apply()
    }
}