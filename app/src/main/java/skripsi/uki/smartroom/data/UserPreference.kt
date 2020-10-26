package skripsi.uki.smartroom.data

import android.content.Context

class UserPreference (context: Context) {
    companion object{
        private const val PREFS_NAME ="smart_room_pref"
        private const val DEVICE_KEY = "device_key"
        private const val USERNAME = "username"
        private const val PASSWORD = "password"

    }

    private val preference = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setDeviceCode(deviceCode: String){
        val editor = preference.edit()
        editor.putString(DEVICE_KEY, deviceCode)
        editor.apply()
    }

    fun getDeviceCode(): String? {
        val deviceCode = preference.getString(DEVICE_KEY,"")
        return deviceCode
    }

    fun setUsername(username: String){
        val editor = preference.edit()
        editor.putString(USERNAME, username)
        editor.apply()
    }

    fun getUsername(): String? {
        val username = preference.getString(USERNAME,"")
        return username
    }

    fun setPassword(password: String){
        val editor = preference.edit()
        editor.putString(PASSWORD, password)
        editor.apply()
    }

    fun getPassword(): String? {
        val password = preference.getString(PASSWORD,"")
        return password
    }

    fun clear(){
        val editor = preference.edit()
        editor.clear()
        editor.commit()
    }
}