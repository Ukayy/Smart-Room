package skripsi.uki.smartroom.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.view.isGone
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_splash_screen.*
import skripsi.uki.smartroom.MainActivity
import skripsi.uki.smartroom.R
import skripsi.uki.smartroom.data.UserPreference
import skripsi.uki.smartroom.ui.login.DeviceCodeActivity
import skripsi.uki.smartroom.ui.login.LoginActivity

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var preference :UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        this.supportActionBar?.hide()
        preference = UserPreference(this)

        val deviceCode = preference.getDeviceCode()
        val username = preference.getUsername()

        pgb_splash.isVisible
        Handler().postDelayed({
            pgb_splash.isGone
            if (deviceCode!="" && username!=""){
                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                finish()
            }else if(deviceCode!=""){
                startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this@SplashScreenActivity, DeviceCodeActivity::class.java))
                finish()
            }
        },2000)
    }
}