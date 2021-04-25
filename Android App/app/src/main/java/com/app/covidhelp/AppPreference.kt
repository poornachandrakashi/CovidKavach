package com.app.covidhelp

import android.content.Context
import android.content.SharedPreferences

class AppPreference private constructor(activity: Context) {
    private val IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch"
    private val mSharedPreferences: SharedPreferences =
        activity.getSharedPreferences("COVID_HELP", Context.MODE_PRIVATE)
    private val mEditor: SharedPreferences.Editor = mSharedPreferences.edit()

    companion object {
        private var instance: AppPreference? = null
        fun getInstance(arg: Context): AppPreference {
            return when {
                instance != null -> instance!!
                else -> synchronized(this) {
                    if (instance == null) instance = AppPreference(arg)
                    instance!!
                }
            }
        }
    }

    fun putString(key: String, value: String) {
        mEditor.putString(key, value)
        mEditor.commit()
    }

    fun getString(key: String, default: String): String? {
        return mSharedPreferences.getString(key, default)
    }

    fun clearPreference() {
        mEditor.clear()
        mEditor.commit()

        mEditor.commit()
    }

    fun setFirstTimeLaunch(isFirstTime: Boolean) {
        mEditor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime)
        mEditor.commit()
    }

    fun isFirstTimeLaunch(): Boolean {
        return mSharedPreferences.getBoolean(IS_FIRST_TIME_LAUNCH, true)
    }
}