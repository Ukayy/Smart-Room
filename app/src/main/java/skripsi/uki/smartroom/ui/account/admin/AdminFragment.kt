package skripsi.uki.smartroom.ui.account.admin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.admin_fragment.*
import kotlinx.android.synthetic.main.item_user.view.*
import skripsi.uki.smartroom.R
import skripsi.uki.smartroom.data.UsersViewHolder
import skripsi.uki.smartroom.data.model.Users

class AdminFragment : Fragment(), View.OnClickListener {

    var node = "12345/user"
    var mDatabase =FirebaseDatabase.getInstance().getReference(node)
    private val list:MutableList<Users> = mutableListOf()

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

        btn_add_user.setOnClickListener(this)
        rv_user.setHasFixedSize(true)
        rv_user.layoutManager = LinearLayoutManager(activity)
        getListUsers()

    }

    private fun getListUsers() {
        val FirebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<Users, UsersViewHolder>(
            Users::class.java,
            R.layout.item_user,
            UsersViewHolder::class.java,
            mDatabase
        ){
            override fun populateViewHolder(viewHolder: UsersViewHolder, model: Users, position: Int) {

                viewHolder.itemView.tv_name.text = model.name
                viewHolder.itemView.tv_idcard.text = model.id_card

            }

        }
        rv_user.adapter = FirebaseRecyclerAdapter
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
