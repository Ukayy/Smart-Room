package skripsi.uki.smartroom.data

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_user.view.*
import skripsi.uki.smartroom.data.model.Users

class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(users:Users){
        itemView.apply {
            val name = users.name
            val idCard = users.id_card

            tv_name.text = name
            tv_idcard.text = idCard
        }
    }
}