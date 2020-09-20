package skripsi.uki.smartroom.data.model

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.item_user.view.*
import skripsi.uki.smartroom.R
import skripsi.uki.smartroom.ui.account.admin.EditUserActivity
import skripsi.uki.smartroom.ui.account.user.UserFragment

class UserAdapter :RecyclerView.Adapter<UserAdapter.ListViewHolder>(){

    private var listUser = ArrayList<User>()

    fun setData(newListData: List<User>?) {
        if (newListData == null) return
        listUser.clear()
        listUser.addAll(newListData)
        notifyDataSetChanged()
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var database = FirebaseDatabase.getInstance().getReference("12345/user")

        fun bind(users: User) {

            with(itemView){
                number.text = users.angka
                tv_name.text = users.name
                tv_idcard.text= users.id_card
                val name = users.name

                btn_edit.setOnClickListener {
                    val mIntent = Intent(context, EditUserActivity::class.java)
                    mIntent.putExtra(EditUserActivity.EXTRA_NAME, "$name")
                    context.startActivity(mIntent)

                    Toast.makeText(context, "ini edit", Toast.LENGTH_SHORT).show()
                }
                btn_delete.setOnClickListener {


                    val mAlertDialog = context.let { AlertDialog.Builder(it) }
                    mAlertDialog.setTitle("Confirmation")
                    mAlertDialog.setMessage("Are you sure to delete $name?")
                    mAlertDialog.setIcon(R.drawable.ic_baseline_announcement_24)

                    mAlertDialog?.setPositiveButton("Yes") { dialog, id ->
                        database.child("$name").removeValue()
                        Toast.makeText(context, "$name deleted", Toast.LENGTH_SHORT).show()

                    }

                    mAlertDialog?.setNegativeButton("No") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog?.show()
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