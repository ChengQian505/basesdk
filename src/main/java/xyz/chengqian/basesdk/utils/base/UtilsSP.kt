package xyz.chengqian.basesdk.utils.base

import android.content.Context
import android.content.SharedPreferences

/**
 * qian.cheng create on 2017/12/27.
 * content :
 */

object UtilsSP {

    private var preferences = HashMap<String, MyPreferences>()

    fun defaultPreferences() = getPreferences("app_default")

    private fun getPreferences(name: String): MyPreferences {
        return if (preferences.containsKey(name)) {
            preferences[name]!!
        } else {
            preferences[name] = MyPreferences(UtilsApp.getApp().getSharedPreferences(name, Context.MODE_PRIVATE))
            getPreferences(name)
        }
    }

    class MyPreferences(private val sharedPreferences: SharedPreferences) {

        fun put(key: String, any: Any) {
            put(key, UtilsJSON.toJsonString(any))
        }

        fun put(key: String, value: String) {
            val editor = sharedPreferences.edit()
            editor.putString(key, value)
            editor.apply()
        }

        fun put(key: String, value: Boolean) {
            val editor = sharedPreferences.edit()
            editor.putBoolean(key, value)
            editor.apply()
        }

        fun put(key: String, value: Int) {
            val editor = sharedPreferences.edit()
            editor.putInt(key, value)
            editor.apply()
        }

        fun getString(key: String): String {
            return sharedPreferences.getString(key, "")
        }

        fun getBoolean(key: String): Boolean {
            return sharedPreferences.getBoolean(key, false)
        }

        fun getInt(key: String): Int {
            return sharedPreferences.getInt(key, -1)
        }

        fun get(key: String): String {
            return getString(key)
        }

        /**
         * Remove all preferences in sp.
         *
         * @param isCommit True to use [SharedPreferences.Editor.commit],
         * false to use [SharedPreferences.Editor.apply]
         */
        fun clear() {
            sharedPreferences.edit().clear().apply()
        }
    }


}
