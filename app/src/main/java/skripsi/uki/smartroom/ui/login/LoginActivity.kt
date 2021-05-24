package skripsi.uki.smartroom.ui.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import id.rizmaulana.sheenvalidator.lib.SheenValidator
import kotlinx.android.synthetic.main.activity_login.*
import skripsi.uki.smartroom.MainActivity
import skripsi.uki.smartroom.R
import skripsi.uki.smartroom.data.UserPreference


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var preference: UserPreference
    private lateinit var sheenValidator: SheenValidator
    var doubleBack:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        this.supportActionBar!!.hide()
        preference = UserPreference(this)
        sheenValidator = SheenValidator(this)

        sheenValidator.registerAsRequired(edt_username)
        sheenValidator.registerAsRequired(edt_password)

        btn_login.setOnClickListener(this)
        tv_change_code.setOnClickListener(this)

    }

    override fun onClick(p0: View) {


        when(p0.id){
            R.id.btn_login -> {
                sheenValidator.validate()
                validation()
            }

            R.id.tv_change_code -> {
                preference.clear()
                val moveIntent = Intent(this, DeviceCodeActivity::class.java)
                startActivity(moveIntent)
                finish()
            }
        }
    }

    fun validation(){
        val username = edt_username.text.toString()
        val password = edt_password.text.toString().trim()
        val deviceCode = preference.getDeviceCode().toString()

        val ref = FirebaseDatabase.getInstance().getReference(deviceCode + "/user/" + username)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val pw = snapshot.child("password").value.toString().trim()

                    if (password == pw) {
                        Log.i("berhasil", "Berhasil Login")
                        Toast.makeText(this@LoginActivity, "login Successful", Toast.LENGTH_SHORT)
                            .show()
                        preference.setUsername(username)
                        val moveIntent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(moveIntent)
                    } else {
                        Log.e("salah", "Password salah")
                        Toast.makeText(
                            this@LoginActivity,
                            "Username and Password Incorrect",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Log.e("uname", "Username tidak adaa")
                    Toast.makeText(
                        this@LoginActivity,
                        "Username and Password Incorrect",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    override fun onBackPressed() {
        if (doubleBack ==true){
            super.onBackPressed()
            return;
        }
        doubleBack = true
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show()
        Handler().postDelayed( {
            doubleBack = false
        },2000)
    }
}