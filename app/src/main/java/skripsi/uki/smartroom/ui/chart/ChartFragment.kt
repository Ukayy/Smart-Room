package skripsi.uki.smartroom.ui.chart

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.chart_fragment.*
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.chart_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        preference = UserPreference(requireActivity())
        lineChart = requireView().findViewById(R.id.chart)
        lineChart.isDragEnabled
        lineChart.setScaleEnabled(false)

        getTemp()

    }


    private fun getTemp() {
        val deviceCode = preference.getDeviceCode().toString()
        val mDatabase = FirebaseDatabase.getInstance().getReference(deviceCode + "/history/suhu")

        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.children.map { item ->
                    val date = item.child("timestamp").value.toString()
                    val temp = item.child("suhu").value.toString().toFloat()
//                    yValues.add(Entry(date, temp))
                    val time = getTime(date).toString().toFloat()
                    Sensor(time, temp)
                }

                for (i in data) {
                    yValues.add(Entry(i.date, i.value))

                }




                val set1 = LineDataSet(yValues, "Temperature")
                set1.fillAlpha = 100
                set1.setAxisDependency(YAxis.AxisDependency.LEFT)

                val dataSets : ArrayList<ILineDataSet> = ArrayList()
                dataSets.add(set1)
                val linedata = LineData(dataSets)
                lineChart.setData(linedata)
                lineChart.invalidate()

//                set1.fillAlpha = 100
//
////                val set2 = LineDataSet(xValues, "Humidity")
//                set1.fillAlpha = 100
////                set2.color = Color.RED
//
//                val dataSets : ArrayList<ILineDataSet> = ArrayList()
//                dataSets.add(set1)
////                dataSets.add(set2)
//
//                val linedata = LineData(dataSets)
//                lineChart.setData(linedata)
                progressBar.visibility = View.GONE


                Log.d("Coba yaaa  ", yValues.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    @SuppressLint("SimpleDateFormat")
    private fun getTime(s: String): String? {
        try {
            val sdf = SimpleDateFormat("hh.mm")
            val netDate = Date(s.toLong())
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }
}