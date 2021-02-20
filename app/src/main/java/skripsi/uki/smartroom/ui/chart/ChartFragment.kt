package skripsi.uki.smartroom.ui.chart

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Color.RED
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.chart_fragment.*
import skripsi.uki.smartroom.R
import skripsi.uki.smartroom.data.UserPreference
import skripsi.uki.smartroom.data.model.Sensor
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChartFragment : Fragment() {
    private lateinit var preference: UserPreference
    lateinit var lineChart:LineChart
    lateinit var lineChart2:LineChart

    val yValues: ArrayList<Entry> = ArrayList()
    val xValues: ArrayList<Entry> = ArrayList()
    val quarters:ArrayList<String> = ArrayList()

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

        lineChart2 = requireView().findViewById(R.id.chart2)
        lineChart2.isDragEnabled
        lineChart2.setScaleEnabled(false)

        getTemp()
        getHum()

    }


    private fun getTemp() {
        val deviceCode = preference.getDeviceCode().toString()
        val mDatabase = FirebaseDatabase.getInstance().getReference(deviceCode + "/history/suhu")

        mDatabase.limitToLast(50).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.children.map { item ->
                    val date = item.child("timestamp").value.toString()
                    val temp = item.child("suhu").value.toString().toFloat()
                    quarters.add(getTime(date).toString())
                    val time = getTime(date).toString().toFloat()

                    Sensor(time, temp)
                }
                var n = 0f;
                for (i in data) {
                    yValues.add(Entry(n, i.value))
                    n++
                }

                val set1 = LineDataSet(yValues, "Temperature")
                set1.fillAlpha = 100
                set1.setAxisDependency(YAxis.AxisDependency.LEFT)

                val dataSets: ArrayList<ILineDataSet> = ArrayList()
                dataSets.add(set1)
                val linedata = LineData(dataSets)
                lineChart.setData(linedata)
                lineChart.invalidate()

                val formatter: ValueFormatter = object : ValueFormatter() {
                    override fun getAxisLabel(value: Float, axis: AxisBase?): String? {
                        return quarters[value.toInt()]
                    }
                }
                val xAxis: XAxis = lineChart.getXAxis()
                xAxis.setGranularity(1f) // minimum axis-step (interval) is 1

                xAxis.setValueFormatter(formatter)
                progressBar.visibility = View.GONE

                Log.d("Coba suhu  ", yValues.toString())


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


    fun getHum(){

        val deviceCode = preference.getDeviceCode().toString()
        val mDatabase1 = FirebaseDatabase.getInstance().getReference(deviceCode+"/history/kelembapan")
        mDatabase1.limitToLast(50).addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.children.map { item ->
                    val date = item.child("timestamp").value.toString()
                    val temp = item.child("kelembapan").value.toString().toFloat()
                    quarters.add(getTime(date).toString())
                    val time = getTime(date).toString().toFloat()

                    Sensor(time, temp)
                }

                var n = 0f;
                for (i in data) {
                    xValues.add(Entry(n, i.value))
                    n++
                }



                val set2 = LineDataSet(xValues, "Humidity")
                set2.fillAlpha = 100
                set2.setAxisDependency(YAxis.AxisDependency.LEFT)
                set2.color = RED

                val dataSets: ArrayList<ILineDataSet> = ArrayList()
                dataSets.add(set2)
                val linedata = LineData(dataSets)
                lineChart2.setData(linedata)
                lineChart2.invalidate()

                val formatter: ValueFormatter = object : ValueFormatter() {
                    override fun getAxisLabel(value: Float, axis: AxisBase?): String? {
                        return quarters[value.toInt()]
                    }
                }
                val xAxis: XAxis = lineChart2.getXAxis()
                xAxis.setGranularity(1f) // minimum axis-step (interval) is 1

                xAxis.setValueFormatter(formatter)

                progressBar2.visibility = View.GONE
                Log.d("Coba hum  ", xValues.toString())

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


    @SuppressLint("SimpleDateFormat")
    private fun getTime(s: String): String? {
        try {
            val sdf = SimpleDateFormat("HH.mm")
            val netDate = Date(s.toLong())
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

}