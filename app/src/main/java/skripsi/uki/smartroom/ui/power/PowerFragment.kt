package skripsi.uki.smartroom.ui.power

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.power_fragment.*
import skripsi.uki.smartroom.R
import skripsi.uki.smartroom.data.UserPreference
import java.text.SimpleDateFormat
import java.util.*

class PowerFragment : Fragment(), View.OnClickListener {

    private lateinit var database:FirebaseDatabase
    private lateinit var preference:UserPreference

    private var fanStatus:Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.power_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preference = UserPreference(requireActivity())
        database = Firebase.database
        getLamp()
        getFan()
        btn_fan.setOnClickListener(this)
        btn_door.setOnClickListener(this)
        getTime()
        getSuhu()
        getKelembapan()
    }

        override fun onClick(p0: View) {
        val deviceCode = preference.getDeviceCode().toString()

        when (p0.id){
            btn_fan.id->{
                val ref = database.getReference(deviceCode+"/alat/fan")
                ref.setValue(if (!fanStatus){"on"} else {"off"})
                fanStatus = !fanStatus
                status_fan.text = if (fanStatus){"ON"} else{ "OFF"}
            }
            btn_door.id -> {

                val mAlertDialog = activity?.let { AlertDialog.Builder(it) }
                mAlertDialog?.setTitle("Confirmation")
                mAlertDialog?.setMessage("Are you sure to open the door?")
                mAlertDialog?.setIcon(R.drawable.ic_baseline_announcement_24)

                mAlertDialog?.setPositiveButton("Yes") { dialog, id ->
                    database.getReference(deviceCode+"/pintu/kondisi").setValue("on")
                    Toast.makeText(context, "Door Opened", Toast.LENGTH_SHORT).show()
                    Handler().postDelayed({
                            database.getReference(deviceCode+"/pintu/kondisi").setValue("off")
                        Toast.makeText(context, "Door Closed", Toast.LENGTH_SHORT).show()
                    },8000)
                }

                mAlertDialog?.setNegativeButton("No") { dialog, id ->
                    dialog.dismiss()

                }
                mAlertDialog?.show()

            }
        }
    }

    private fun getFan(){
        val deviceCode = preference.getDeviceCode().toString()
        val ref = database.getReference(deviceCode+"/alat/fan")
        ref.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                status_fan?.text = snapshot.value.toString().toUpperCase()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun getSuhu(){
        val deviceCode = preference.getDeviceCode().toString()
        val ref:DatabaseReference = database.getReference(deviceCode+"/alat/sensor/suhu")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val newVal = snapshot.getValue<Double>()
                power_temp?.text = newVal?.toInt().toString()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    private fun getKelembapan(){
        val deviceCode = preference.getDeviceCode().toString()
        val ref:DatabaseReference = database.getReference(deviceCode+"/alat/sensor/kelembapan")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val newVal = snapshot.getValue<Double>()
                power_hum?.text = newVal?.toInt().toString()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }


    private fun getLamp(){
        val deviceCode = preference.getDeviceCode().toString()

        switch_1.setOnCheckedChangeListener{_, isChecked->
            database.getReference(deviceCode+"/alat/relay").child("0").setValue(if (isChecked){ "on"}else{"off"})
        }
        switch_2.setOnCheckedChangeListener{_, isChecked->
            database.getReference(deviceCode+"/alat/relay").child("1").setValue(if (isChecked){ "on"}else{"off"})
        }
        switch_3.setOnCheckedChangeListener{_, isChecked->
            database.getReference(deviceCode+"/alat/relay").child("2").setValue(if (isChecked){ "on"}else{"off"})
        }
        switch_4.setOnCheckedChangeListener{_, isChecked->
            database.getReference(deviceCode+"/alat/relay").child("3").setValue(if (isChecked){ "on"}else{"off"})
        }

        database.getReference(deviceCode+"/alat/relay").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                switch_1?.isChecked = snapshot.child("0").getValue<String>() == "on"
                switch_2?.isChecked = snapshot.child("1").getValue<String>() == "on"
                switch_3?.isChecked = snapshot.child("2").getValue<String>() == "on"
                switch_4?.isChecked = snapshot.child("3").getValue<String>() == "on"

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


    fun getTime(){

        val calender = Calendar.getInstance()
        val simpleDate = SimpleDateFormat("E, dd-MM-yyyy")
        val simpleTime = SimpleDateFormat("hh:mm a")

        val date = simpleDate.format(calender.time)
        val time = simpleTime.format(calender.time)

        tv_date.text = date.toString()
        tv_clock.text = time.toString()

    }


}