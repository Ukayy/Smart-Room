package skripsi.uki.smartroom.ui.chart

import android.graphics.Color
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
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
import skripsi.uki.smartroom.R

class ChartFragment : Fragment() {

    lateinit var lineChart:LineChart

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

        lineChart = requireView().findViewById(R.id.chart)
        lineChart.isDragEnabled
        lineChart.setScaleEnabled(false)

        val yValues: ArrayList<Entry> = ArrayList()
        val xValues: ArrayList<Entry> = ArrayList()

        yValues.add(Entry(1F, 60F))
        yValues.add(Entry(2F, 50F))
        yValues.add(Entry(3F, 70F))
        yValues.add(Entry(4F, 30F))
        yValues.add(Entry(5F, 50F))
        yValues.add(Entry(6F, 60F))
        yValues.add(Entry(7F, 50F))
        yValues.add(Entry(8F, 70F))
        yValues.add(Entry(9F, 30F))
        yValues.add(Entry(10F, 50F))
        yValues.add(Entry(11F, 30F))
        yValues.add(Entry(12F, 50F))

        xValues.add(Entry(1F, 30F))
        xValues.add(Entry(2F, 40F))
        xValues.add(Entry(3F, 50F))
        xValues.add(Entry(4F, 50F))
        xValues.add(Entry(5F, 60F))
        xValues.add(Entry(6F, 70F))
        xValues.add(Entry(7F, 40F))
        xValues.add(Entry(8F, 60F))
        xValues.add(Entry(9F, 40F))
        xValues.add(Entry(10F, 60F))
        xValues.add(Entry(11F, 40F))
        xValues.add(Entry(12F, 70F))

        val set1 : LineDataSet = LineDataSet(yValues, "Temperature")
        set1.fillAlpha = 100

        val set2 : LineDataSet = LineDataSet(xValues, "Humidity")
        set1.fillAlpha = 100
        set2.color = Color.RED

        val dataSets : ArrayList<ILineDataSet> = ArrayList()
        dataSets.add(set1)
        dataSets.add(set2)

        val data:LineData = LineData(dataSets)
        lineChart.setData(data)
    }

}