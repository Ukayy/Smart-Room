package skripsi.uki.smartroom.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_user.view.*
import skripsi.uki.smartroom.R
import skripsi.uki.smartroom.data.model.User

class UserAdapter(private  val listUser:ArrayList<User>) :RecyclerView.Adapter<UserAdapter.ListviewHolder>(){

    class ListviewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user:User){
            with(itemView){
                number.text = "1"
                data_name.text = user.name
                data_id.text = user.id_card
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListviewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent,false)
        return ListviewHolder(view)
    }

    override fun onBindViewHolder(holder: ListviewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

}