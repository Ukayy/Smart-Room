package skripsi.uki.smartroom.ui.account.admin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.admin_fragment.*
import skripsi.uki.smartroom.R
import skripsi.uki.smartroom.data.model.User

class AdminFragment : Fragment(), View.OnClickListener {

    var node = "12345/user"

    private val list = ArrayList<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.admin_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_user.setHasFixedSize(true)
        btn_add_user.setOnClickListener(this)


    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_add_user ->{
                val mintent = Intent(activity,AddUserActivity::class.java)
                startActivity(mintent)
            }
        }
    }


}