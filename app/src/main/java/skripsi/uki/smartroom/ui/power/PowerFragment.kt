package skripsi.uki.smartroom.ui.power

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.power_fragment.*
import skripsi.uki.smartroom.R

class PowerFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() = PowerFragment()
    }

    private lateinit var viewModel: PowerViewModel
    private lateinit var database:FirebaseDatabase;

    private var doorStatus:Boolean = false
    private var acStatus:Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.power_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PowerViewModel::class.java)
        // TODO: Use the ViewModel
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = Firebase.database
        getSuhu()
        getKelembapan()

        btn_ac.setOnClickListener(this)
        btn_door.setOnClickListener(this)

        getLamp()
    }

    override fun onClick(p0: View?) {
        when (p0?.id){
            btn_ac.id->{
                database.getReference("12345/alat/ac/power").setValue(if (!acStatus){"on"} else {"off"})
                acStatus = !acStatus
                status_ac.text = if (acStatus){"ON"} else{ "OFF"}
            }
            btn_door.id->{
//                database.getReference("12345/pintu/kondisi").setValue(if (!doorStatus){"on"} else {"off"})
//                doorStatus = !doorStatus

            }
        }
    }

    private fun getSuhu(){
        val ref:DatabaseReference = database.getReference("12345/sensor/suhu/kondisi")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val newVal = snapshot.getValue<Double>()
                power_temp.text = newVal?.toInt().toString()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    private fun getKelembapan(){
        val ref:DatabaseReference = database.getReference("12345/sensor/kelembapan/kondisi")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val newVal = snapshot.getValue<Double>()
                power_hum.text = newVal?.toInt().toString()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }


    private fun getLamp(){

        switch_1.setOnCheckedChangeListener{_, isChecked->
            database.getReference("12345/alat/relay").child("0").setValue(if (isChecked){ "on"}else{"off"})
        }
        switch_2.setOnCheckedChangeListener{_, isChecked->
            database.getReference("12345/alat/relay").child("1").setValue(if (isChecked){ "on"}else{"off"})
        }
        switch_3.setOnCheckedChangeListener{_, isChecked->
            database.getReference("12345/alat/relay").child("2").setValue(if (isChecked){ "on"}else{"off"})
        }
        switch_4.setOnCheckedChangeListener{_, isChecked->
            database.getReference("12345/alat/relay").child("3").setValue(if (isChecked){ "on"}else{"off"})
        }

        database.getReference("12345/alat/relay").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                switch_1.isChecked = snapshot.child("0").getValue<String>() == "on"
                switch_2.isChecked = snapshot.child("1").getValue<String>() == "on"
                switch_3.isChecked = snapshot.child("2").getValue<String>() == "on"
                switch_4.isChecked = snapshot.child("3").getValue<String>() == "on"
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


}