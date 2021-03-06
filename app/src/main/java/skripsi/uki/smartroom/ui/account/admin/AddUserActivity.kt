package skripsi.uki.smartroom.ui.account.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import skripsi.uki.smartroom.R
import skripsi.uki.smartroom.data.UserPreference
import skripsi.uki.smartroom.data.model.Users

class AddUserActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var addName:EditText
    private lateinit var addCard:EditText
    private lateinit var addEmail:EditText
    private lateinit var addPassword:EditText
    private lateinit var preference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)
        preference = UserPreference(this)

        addName = findViewById(R.id.add_name)
        addCard = findViewById(R.id.add_card)
        addEmail = findViewById(R.id.add_email)
        addPassword = findViewById(R.id.add_password)
        val btnAddUser:Button = findViewById(R.id.btn_add_user)
        btnAddUser.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        addUser()
    }

    private fun addUser(){

        val deviceCode = preference.getDeviceCode().toString()
        val name = addName.text.toString().trim()
        val id_card = addCard.text.toString().trim().toUpperCase()
        val email = addEmail.text.toString().trim()
        val password = addPassword.text.toString().trim()


        if (name.isEmpty()){
            addName.error ="Fill Name"
            return
        }
        if (id_card.isEmpty()){
            addCard.error ="Fill Name"
            return
        }
        if (email.isEmpty()){
            addEmail.error ="Fill Name"
            return
        }
        if (password.isEmpty()){
            addPassword.error ="Fill Name"
            return
        }

        val ref = FirebaseDatabase.getInstance().getReference(deviceCode+"/user")
        val user = Users(id_card,name,password,email)

        ref.child(name).setValue(user).addOnCompleteListener{
            Toast.makeText(applicationContext,"Success",Toast.LENGTH_SHORT).show()
            onBackPressed()
        }

        val ref2 = FirebaseDatabase.getInstance().getReference(deviceCode+"/pintu/rfid")
        ref2.child(id_card).setValue(Users(id_card,name,null,null))
    }
}
