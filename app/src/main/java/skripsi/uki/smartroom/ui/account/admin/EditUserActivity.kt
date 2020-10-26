package skripsi.uki.smartroom.ui.account.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_edit_user.*
import kotlinx.android.synthetic.main.item_user.*
import skripsi.uki.smartroom.MainActivity
import skripsi.uki.smartroom.R
import skripsi.uki.smartroom.data.UserPreference
import skripsi.uki.smartroom.data.model.Users

class EditUserActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_USER = "extra_user"
    }
    private lateinit var preference:UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user)

        preference = UserPreference(this)
        val deviceCode = preference.getDeviceCode().toString()

        val user = intent.getParcelableExtra<Users>(EXTRA_USER)
        val name = user?.name
        val idCard = user?.id_card.toString()
        val email = user?.email.toString()
        val password = user?.password.toString()
        val database = FirebaseDatabase.getInstance().getReference(deviceCode+"/user/$name")

        edt_name.setText(name)
        edt_card.setText(idCard)
        edt_email.setText(email)
        edt_password.setText(password)



        btn_edit_user.setOnClickListener {
            val newEmail = edt_email.text.toString().trim()
            val newIdCard = edt_card.text.toString().trim()
            val newPassword = edt_password.text.toString().trim()

            database.child("email").setValue(newEmail)
            database.child("id_card").setValue(newIdCard)
            database.child("password").setValue(newPassword)

            Toast.makeText(this, "Edit Success", Toast.LENGTH_SHORT).show()
            onBackPressed()
        }

    }


}