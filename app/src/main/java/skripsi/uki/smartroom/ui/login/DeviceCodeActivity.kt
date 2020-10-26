package skripsi.uki.smartroom.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_device_code.*
import skripsi.uki.smartroom.R
import skripsi.uki.smartroom.data.UserPreference

class DeviceCodeActivity : AppCompatActivity() {

    companion object{
        private const val FIELD_REQUIRED = "Field tidak boleh kosong"
    }
    private lateinit var preference: UserPreference
    var database:FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_code)
        this.supportActionBar!!.hide()

        btn_submit_code.setOnClickListener{
            val key = device_code.text.toString().trim()
            if (key.isEmpty()){
                device_code.error = FIELD_REQUIRED
                return@setOnClickListener
            }
            getDeviceCode()
        }
    }


    fun getDeviceCode() {
        var input = device_code.text.toString().trim()
        val ref = database.getReference(input)
        preference = UserPreference(this)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    preference.setDeviceCode(input)
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

}