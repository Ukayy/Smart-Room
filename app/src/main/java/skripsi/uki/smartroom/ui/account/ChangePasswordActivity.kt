package skripsi.uki.smartroom.ui.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.rizmaulana.sheenvalidator.lib.SheenValidator
import kotlinx.android.synthetic.main.activity_change_password.*
import skripsi.uki.smartroom.R
import skripsi.uki.smartroom.data.UserPreference

class ChangePasswordActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var preference: UserPreference
    private lateinit var sheenValidator: SheenValidator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        preference = UserPreference(this)

        sheenValidator = SheenValidator(this)
        sheenValidator.registerAsRequired(edt_new_password)
        sheenValidator.registerAsRequired(edt_confirm_password)
        sheenValidator.registerAsRequired(edt_old_password)

        btn_submit_cp.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when (p0.id) {

            R.id.btn_submit_cp -> {
                sheenValidator.validate()
                ChangePassword()
            }
        }
    }

    private fun ChangePassword() {
        val oldPassword = edt_old_password.text.toString()
        val newPassword = edt_new_password.text.toString()
        val confirmPassword = edt_confirm_password.text.toString()
        val deviceCode = preference.getDeviceCode().toString()
        val username = preference.getUsername().toString()
        val ref = FirebaseDatabase.getInstance()
            .getReference(deviceCode + "/user/" + username + "/password")
        Log.e("reference", deviceCode + "/" + username)

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val data = snapshot.value.toString()
                    if (oldPassword == data) {
                        if (newPassword == confirmPassword) {
                            ref.setValue(newPassword)
                            Toast.makeText(
                                this@ChangePasswordActivity,
                                "Change Success",
                                Toast.LENGTH_SHORT
                            ).show()
                            onBackPressed()
                        } else {
                            Toast.makeText(
                                this@ChangePasswordActivity,
                                "Password Not Match",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@ChangePasswordActivity,
                            "Incorrect Password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Log.e("msg", "Gak ono suuu")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

}