package ki1nhom2.btl.qlct.homeState

import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ki1nhom2.btl.qlct.R
import ki1nhom2.btl.qlct.MainActivity
import ki1nhom2.btl.qlct.homeState.monthlyShorten.MonthlyInfoAdapter
import ki1nhom2.btl.qlct.homeState.monthlyShorten.MonthlyInfoNode

class HomeStateActivity : MainActivity() {

    companion object {
        var balance: Long = 0
        var incomePerMonth: ArrayList<Long> = ArrayList()
        var outcomePerMonth: ArrayList<Long> = ArrayList()
        val monthNames = arrayOf(
            "January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December"
        )

        val data = ArrayList<MonthlyInfoNode>()
        lateinit var adapter : MonthlyInfoAdapter
        lateinit var balanceDisplay : TextView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_state)

        val monthlyStatisticList = findViewById<RecyclerView>(R.id.monthlyStatistic)
        monthlyStatisticList.layoutManager = LinearLayoutManager(this)

        adapter = MonthlyInfoAdapter(data)
        monthlyStatisticList.adapter = adapter

        balanceDisplay = findViewById(R.id.balanceNumber)
        balanceDisplay.text = toMoneyFormat(balance)

        changeColor(1)
    }
}