package skripsi.uki.smartroom.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.rizmaulana.sheenvalidator.lib.SheenValidator
import kotlinx.android.synthetic.main.activity_login.*
import skripsi.uki.smartroom.MainActivity
import skripsi.uki.smartroom.R
import skripsi.uki.smartroom.data.UserPreference


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var preference: UserPreference
    private lateinit var sheenValidator: SheenValidator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        this.supportActionBar!!.hide()
        preference = UserPreference(this)
        sheenValidator = SheenValidator(this)
        sheenValidator.setOnValidatorListener {
            Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
        }
        sheenValidator.registerAsRequired(edt_username)
        sheenValidator.registerAsRequired(edt_password)
        val btnLoginActivity: Button = findViewById(R.id.btn_login)
        btnLoginActivity.setOnClickListener(this)
        tv_change_code.setOnClickListener (this)

    }

    override fun onClick(p0: View) {


        when(p0.id){
            R.id.btn_login ->{
                sheenValidator.validate()
                validation()
            }

            R.id.tv_change_code ->{
                preference.clear()
                val moveIntent = Intent(this, DeviceCodeActivity::class.java)
                startActivity(moveIntent)

            }
        }
    }

    fun validation(){
        val username = edt_username.text.toString()
        val password = edt_password.text.toString().trim()
        val deviceCode = preference.getDeviceCode().toString()

        val ref = FirebaseDatabase.getInstance().getReference(deviceCode+"/user/"+username)
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
                    Toast.makeText(this@LoginActivity, "Username and Password Incorrect", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

}