package skripsi.uki.smartroom.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_login.*
import skripsi.uki.smartroom.MainActivity
import skripsi.uki.smartroom.MainActivity.Companion.EXTRA_USERNAME
import skripsi.uki.smartroom.R

class LoginActivity : AppCompatActivity(), View.OnClickListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLoginActivity: Button = findViewById(R.id.btn_login)
        btnLoginActivity.setOnClickListener(this)

        this.supportActionBar!!.hide()

        tv_change_code.setOnClickListener {
            val moveIntent = Intent(this, DeviceCodeActivity::class.java)
            startActivity(moveIntent)
        }


    }

    override fun onClick(p0: View) {
        var username = edt_username.text.toString()
        when(p0.id){
            R.id.btn_login ->{
                val moveIntent = Intent(this, MainActivity::class.java)
                moveIntent.putExtra(EXTRA_USERNAME,username)
                startActivity(moveIntent)
            }
        }
    }

}