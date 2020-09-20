package skripsi.uki.smartroom.data

import android.content.Context
import skripsi.uki.smartroom.data.model.Session

class SharedPreference (context: Context) {
    companion object{
        private const val PREFS_NAME ="smart_room_pref"
        private const val DEVICE_KEY = "device_key"
        private const val USERNAME = "username"
        private const val PASSWORD = "password"
        private const val ROLE = "session"
    }

    private val preference = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setSession(value:Session){
        val editor = preference.edit()
        editor.putString(DEVICE_KEY, value.device_key)
        editor.putString(USERNAME,value.username)
        editor.putString(PASSWORD, value.password)
        editor.putString(ROLE,value.role)
        editor.apply()
    }

    fun getSession():Session{

        val model = Session()
        model.device_key = preference.getString(DEVICE_KEY,"")
        model.username = preference.getString(USERNAME,"")
        model.password = preference.getString(PASSWORD,"")
        model.role = preference.getString(ROLE,"")

        return model
    }
}