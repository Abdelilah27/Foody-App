package com.foody.foody.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManagement {
    var pref: SharedPreferences
    var editor: SharedPreferences.Editor
    var con: Context
    private var PRIVATE_MODE: Int = 0

    constructor(con: Context) {
        this.con = con
        pref = con.getSharedPreferences(PREF_ID, PRIVATE_MODE)
        editor = pref.edit()
    }

    companion object {
        val PREF_ID = "id_preference"
        val PREF_NAME = "name_preference"
        val PREF_EMAIL = "email_preference"
        val IS_LOGGED = "isLogged"
        val KEY_ID = "key_id"
        val KEY_NAME = "key_name"
        val KEY_EMAIL = "key_email"
    }

    fun createLogin(
        id: String,
        name: String = null.toString(),
        email: String = null.toString()
    ) {
        editor.putBoolean(IS_LOGGED, true)
        editor.putString(KEY_ID, id)
        editor.putString(KEY_NAME, name)
        editor.putString(KEY_EMAIL, email)
        editor.commit()
    }

    fun checkIsLogged(): Boolean {
        return pref.getBoolean(IS_LOGGED, false)
    }

    fun getName(): String {
        return pref.getString(KEY_NAME, "null")!!
    }

    fun getEmail(): String {
        return pref.getString(KEY_EMAIL, "null")!!
    }

    fun getID(): String {
        return pref.getString(KEY_ID, "null")!!
    }

    fun logOut() {
        editor.clear()
        editor.commit()
    }


}