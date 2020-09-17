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
import skripsi.uki.smartroom.ui.login.DeviceCodeActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        this.supportActionBar?.hide()
        pgb_splash.isVisible
        Handler().postDelayed({
            pgb_splash.isGone
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            finish()
        },2)
    }
}