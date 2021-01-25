package skripsi.uki.smartroom.ui.chart

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import skripsi.uki.smartroom.R
import skripsi.uki.smartroom.data.UserPreference
import skripsi.uki.smartroom.data.model.Sensor
import java.lang.System.currentTimeMillis
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChartFragment : Fragment() {
    private lateinit var preference: UserPreference
    lateinit var lineChart:LineChart


    val yValues: ArrayList<Entry> = ArrayList()
    val xValues: ArrayList<Entry> = ArrayList()
    val xtemp:ArrayList<Float> = ArrayList()
    val xdate:ArrayList<Float> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.chart_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        preference = UserPreference(requireActivity())
        lineChart = requireView().findViewById(R.id.chart)
        lineChart.isDragEnabled
        lineChart.setScaleEnabled(false)

        getTemp()

        //ini untuk temperature
        yValues.add(Entry(12F, 12F))

        //ini untuk kelmbapan
        xValues.add(Entry(12F, 12F))

        val set1 : LineDataSet = LineDataSet(yValues, "Temperature")
        set1.fillAlpha = 100

        val set2 : LineDataSet = LineDataSet(xValues, "Humidity")
        set1.fillAlpha = 100
        set2.color = Color.RED

        val dataSets : ArrayList<ILineDataSet> = ArrayList()
        dataSets.add(set1)
        dataSets.add(set2)

        val data: LineData = LineData(dataSets)
        lineChart.setData(data)

    }


    private fun getTemp() {
        val deviceCode = preference.getDeviceCode().toString()
        val mDatabase = FirebaseDatabase.getInstance().getReference(deviceCode+"/history/suhu")
        val time = getDateTime(currentTimeMillis().toString())
        Log.d("Tanggal saiki :", time.toString() )
        mDatabase.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //filter {item-> item.child("date").value.toString().toLong() >=123}.
                val data = snapshot.children.map {item ->
                    val date = item.child("timestamp").value.toString().toFloat()
                    val temp = item.child("suhu").value.toString().toFloat()
                    Sensor(date, temp)
                }

                Log.d("Coba yaaa  " , data.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun getDateTime(s: String): String? {
        try {
            val sdf = SimpleDateFormat("MM/dd/yyyy")
            val netDate = Date(s.toLong())
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }
}