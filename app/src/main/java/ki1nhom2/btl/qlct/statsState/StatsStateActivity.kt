package ki1nhom2.btl.qlct.statsState

import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.android.material.button.MaterialButton
import ki1nhom2.btl.qlct.MainActivity
import ki1nhom2.btl.qlct.R
import ki1nhom2.btl.qlct.homeState.HomeStateActivity.Companion.outcomePerMonth
import ki1nhom2.btl.qlct.statsState.LineChart.LineChartAdapter
import ki1nhom2.btl.qlct.statsState.PieChart.PieChartAdapter
import kotlin.math.floor


class StatsStateActivity : MainActivity() {

    private lateinit var lineChart : LineChart
    private lateinit var pieChart : PieChart

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.stats_state)

        val title = findViewById<TextView>(R.id.title)
        lineChart = findViewById(R.id.lineChart)
        pieChart = findViewById(R.id.pieChart)

        val btnShowType = findViewById<MaterialButton>(R.id.showTypeGraph)
        val btnShowMonth = findViewById<MaterialButton>(R.id.showMonthGraph)

        btnShowType.setOnClickListener {
            title.text = "Biến động chi tiêu qua các tháng"
            lineChart.visibility = View.VISIBLE
            pieChart.visibility = View.GONE

            lineChart.setTouchEnabled(true)

            LineChartAdapter.renderData(lineChart)

            btnShowType.isClickable = false
            btnShowMonth.isClickable = true
        }

        btnShowMonth.setOnClickListener {
            title.text = "Tỉ lệ các loại khoản chi"
            lineChart.visibility = View.GONE
            pieChart.visibility = View.VISIBLE

            pieChart.setTouchEnabled(true)

            PieChartAdapter.renderData(pieChart)

            btnShowType.isClickable = true
            btnShowMonth.isClickable = false
        }

        changeColor(3)
    }
}