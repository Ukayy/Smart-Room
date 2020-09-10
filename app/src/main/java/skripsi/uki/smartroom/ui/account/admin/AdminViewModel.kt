package skripsi.uki.smartroom.ui.account.admin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import skripsi.uki.smartroom.data.model.User

class AdminViewModel : ViewModel(){
    private var data = MutableLiveData<List<User>>()
    private var ref = FirebaseDatabase.getInstance().getReference("12345/user")

    init {
        getUser()
    }

    private fun getUser() {


    }

    fun setData():MutableLiveData<List<User>>{
        return data
    }
}