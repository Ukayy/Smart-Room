package skripsi.uki.smartroom.ui.account.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import skripsi.uki.smartroom.R

class EditUserActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_NAME = "extra_name"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user)

        val name = intent.getStringExtra(EXTRA_NAME).toString()
        var database = FirebaseDatabase.getInstance().getReference("12345/user/$name")

//        edt_username.setText(name)
//        edt_username.setEnabled(false)
//        edt_username.setFocusable(false)

    }
}