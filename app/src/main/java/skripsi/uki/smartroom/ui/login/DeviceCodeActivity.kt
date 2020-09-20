package skripsi.uki.smartroom.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_device_code.*
import skripsi.uki.smartroom.R
import skripsi.uki.smartroom.data.SharedPreference
import skripsi.uki.smartroom.data.model.Session

class DeviceCodeActivity : AppCompatActivity() {

    private lateinit var model:Session
    var database:FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_code)
        this.supportActionBar!!.hide()

        btn_submit_code.setOnClickListener{
            getDeviceCode()
        }
    }


    fun getDeviceCode() {
        var input = device_code.text.toString().trim()
        val ref = database.getReference(input)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val mintent = Intent(this@DeviceCodeActivity, LoginActivity::class.java)
                    startActivity(mintent)
                } else {
                    Toast.makeText(this@DeviceCodeActivity, "Salah Cuy", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun saveCode(deviceCode: String) {
        val preference = SharedPreference(this)
        model.device_key = deviceCode
        preference.setSession(model)
    }

}