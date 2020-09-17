package skripsi.uki.smartroom.ui.account.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.admin_fragment.*
import kotlinx.android.synthetic.main.item_user.view.*
import skripsi.uki.smartroom.R
import skripsi.uki.smartroom.data.UsersViewHolder
import skripsi.uki.smartroom.data.model.UserAdapter
import skripsi.uki.smartroom.data.model.Users

class AdminFragment : Fragment(), View.OnClickListener {

    var node = "12345/user"
    var mDatabase = FirebaseDatabase.getInstance().getReference(node)
    private val list: MutableList<Users> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.admin_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_add_user.setOnClickListener(this)
        rv_user.setHasFixedSize(true)
        getListUsers()

    }

    private fun getListUsers() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val data = dataSnapshot.children.map { item ->
                    Users(
                        "${item.child("id_card").value}",
                        "${item.child("name").value}",
                        "${item.child("password").value}",
                        "${item.child("email").value}"
                    )
                }
                list.addAll(data)
                rv_user.layoutManager = LinearLayoutManager(activity)
                val UserAdapter = UserAdapter(list as ArrayList<Users>)
                rv_user.adapter = UserAdapter
                Log.d("lol", data.toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("Kosong", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })

    }


    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.btn_add_user -> {
                val mintent = Intent(activity, AddUserActivity::class.java)
                startActivity(mintent)
            }
        }
    }
}
