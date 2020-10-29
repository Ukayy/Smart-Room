package skripsi.uki.smartroom.data.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.item_user.view.*
import skripsi.uki.smartroom.R
import skripsi.uki.smartroom.data.UserPreference
import skripsi.uki.smartroom.data.model.Users
import skripsi.uki.smartroom.ui.account.admin.EditUserActivity

class UserAdapter(activity: Activity) :RecyclerView.Adapter<UserAdapter.ListViewHolder>(){

    private var listUser = ArrayList<Users>()
    var preference : UserPreference = UserPreference(activity)

    fun setData(newListData: List<Users>?) {
        if (newListData == null) return
        listUser.clear()
        listUser.addAll(newListData)
        notifyDataSetChanged()
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var preference : UserPreference = UserPreference(itemView.context)
        private var deviceCode = preference.getDeviceCode().toString()

        private var database = FirebaseDatabase.getInstance().getReference(deviceCode+"/user")

        fun bind(users: Users) {

            with(itemView){
                tv_name.text = users.name
                tv_idcard.text= users.id_card
                val name = users.name
                val idCard = users.id_card
                val email = users.email
                val password = users.password
                val user = Users(idCard,name,password,email)

                btn_edit.setOnClickListener {
                    val mIntent = Intent(context, EditUserActivity::class.java)
                    mIntent.putExtra(EditUserActivity.EXTRA_USER, user)
                    context.startActivity(mIntent)

                    Toast.makeText(context, "Edit $name", Toast.LENGTH_SHORT).show()
                }
                btn_delete.setOnClickListener {


                    val mAlertDialog = context.let { AlertDialog.Builder(it) }
                    mAlertDialog.setTitle("Confirmation")
                    mAlertDialog.setMessage("Are you sure to delete $name?")
                    mAlertDialog.setIcon(R.drawable.ic_baseline_announcement_red)

                    mAlertDialog.setPositiveButton("Yes") { dialog, id ->
                        database.child("$name").removeValue()
                        Toast.makeText(context, "$name deleted", Toast.LENGTH_SHORT).show()

                    }

                    mAlertDialog.setNegativeButton("No") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.show()
                }
            }
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int =listUser.size


}