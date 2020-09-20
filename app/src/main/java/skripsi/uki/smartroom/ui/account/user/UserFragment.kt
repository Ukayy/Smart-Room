package skripsi.uki.smartroom.ui.account.user

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import skripsi.uki.smartroom.ui.login.LoginActivity
import skripsi.uki.smartroom.R
import skripsi.uki.smartroom.ui.account.ChangePasswordActivity

class UserFragment : Fragment(), View.OnClickListener {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btn_logout: Button = view.findViewById(R.id.btn_logout)
        btn_logout.setOnClickListener(this)

        val btn_c_password: Button = view.findViewById(R.id.btn_c_password)
        btn_c_password.setOnClickListener(this)

    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_logout ->{
                val intentLogout = Intent(activity,
                    LoginActivity::class.java)
                startActivity(intentLogout)
            }
            R.id.btn_c_password ->{
                val intentChangePassword = Intent(activity,ChangePasswordActivity::class.java)
                startActivity(intentChangePassword)
            }
        }
    }

}