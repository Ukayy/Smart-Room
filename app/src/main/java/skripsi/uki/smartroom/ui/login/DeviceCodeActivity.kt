package skripsi.uki.smartroom.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_device_code.*
import skripsi.uki.smartroom.R

class DeviceCodeActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_code)
        this.supportActionBar!!.hide()
        btn_submit_code.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_submit_code ->{
                val intent: Intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}