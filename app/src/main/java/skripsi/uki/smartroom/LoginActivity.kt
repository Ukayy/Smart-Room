package skripsi.uki.smartroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLoginActivity: Button = findViewById(R.id.btn_login)
        btnLoginActivity.setOnClickListener(this)

        this.supportActionBar!!.hide()


    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_login ->{
                val moveIntent = Intent(this, MainActivity::class.java)
                startActivity(moveIntent)
            }
        }
    }

}