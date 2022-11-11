package com.foody.foody.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE

const val PREFERENCE_NAME = "test"
const val KEY_SELECTION = "key_selection"

// To Save Selected Spinner Position
class PreferenceManager(val context: Context) {

    fun setSelection(pos: Int) {
        val sharedPref = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
        sharedPref.edit().putInt(KEY_SELECTION, pos).commit()
    }

    fun getSelection(): Int {
        val sharedPref = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
        return sharedPref.getInt(KEY_SELECTION, 0)
    }

}