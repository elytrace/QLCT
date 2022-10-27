package ki1nhom2.btl.qlct

import android.content.Intent
import com.google.gson.Gson
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ki1nhom2.btl.qlct.addState.AddStateActivity
import ki1nhom2.btl.qlct.addState.AddStateActivity.Companion.consumptionInfo
import ki1nhom2.btl.qlct.addState.AddStateActivity.Companion.expenditureCost
import ki1nhom2.btl.qlct.addState.AddStateActivity.Companion.expenditureName
import ki1nhom2.btl.qlct.homeState.HomeStateActivity
import ki1nhom2.btl.qlct.homeState.HomeStateActivity.Companion.balance
import ki1nhom2.btl.qlct.homeState.HomeStateActivity.Companion.data
import ki1nhom2.btl.qlct.homeState.HomeStateActivity.Companion.income
import ki1nhom2.btl.qlct.homeState.HomeStateActivity.Companion.monthNames
import ki1nhom2.btl.qlct.homeState.HomeStateActivity.Companion.outcome
import ki1nhom2.btl.qlct.homeState.monthlyShorten.MonthlyInfoNode
import ki1nhom2.btl.qlct.profileState.ProfileStateActivity
import ki1nhom2.btl.qlct.statsState.StatsStateActivity
import ki1nhom2.btl.qlct.transactionsState.TransactionStateActivity
import com.google.gson.reflect.TypeToken
import ki1nhom2.btl.qlct.addState.addCost.ExpenditureCostNode
import java.lang.reflect.Type

open class MainActivity : AppCompatActivity() {

    companion object {

        // variables

        var isInitialized : Boolean = false

        // functions

        fun toMoneyFormat(money : Long) : String {
            if(money < 1000) return money.toString()

            var input : Long = money
            var output = ""

            var counter = 0
            while (input > 0) {
                if(counter % 3 == 0 && counter != 0) output = ",$output"
                output = (input % 10).toString() + output
                input /= 10
                counter++
            }

            return output
        }

        fun reCalculateBalance() {
            balance = 0
            for(i in 1..12) {
                balance += income[i] - outcome[i]
            }
        }
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadData()

        if(!isInitialized) {
            init()
            isInitialized = true

            saveData()
        }

    }

    fun changeColor(contentIndex : Int) {
        val btnHome = findViewById<ImageButton>(R.id.btnHome)
        val btnTrans = findViewById<ImageButton>(R.id.btnTrans)
        val btnStats = findViewById<ImageButton>(R.id.btnStats)
        val btnProfile = findViewById<ImageButton>(R.id.btnProfile)

        val txtHome = findViewById<TextView>(R.id.txtHome)
        val txtTrans = findViewById<TextView>(R.id.txtTrans)
        val txtStats = findViewById<TextView>(R.id.txtStats)
        val txtProfile = findViewById<TextView>(R.id.txtProfile)

        val btnAdd = findViewById<ImageButton>(R.id.btnAdd)

        val listBtn : ArrayList<ImageButton> = arrayListOf(btnHome, btnTrans, btnStats, btnProfile)
        val listTxt : ArrayList<TextView> = arrayListOf(txtHome, txtTrans, txtStats, txtProfile)

        if(contentIndex == 5) {
            btnAdd.setColorFilter(Color.argb(100, 5, 128, 60))
        }
        else {
            listBtn[contentIndex-1].setColorFilter(Color.BLACK)
            listTxt[contentIndex-1].setTextColor(Color.BLACK)
        }
    }

    private fun init() {
        for(i in 1..12) {
            income.add(0)
            outcome.add(0)
        }

        expenditureName.add("Tiền nhà")
        expenditureName.add("Tiền điện")
        expenditureName.add("Tiền nước")

        expenditureCost.add(0)
        expenditureCost.add(0)
        expenditureCost.add(0)

        for(i in 1..12) {
            data.add(
                MonthlyInfoNode(
                    monthNames[i - 1],
                    income[i - 1],
                    outcome[i - 1]
                )
            )
        }
    }

    fun toHomeState(view: View) {
        val intent = Intent(this, HomeStateActivity::class.java)
        startActivity(intent)
    }
    fun toTransactionState(view: View) {
        val intent = Intent(this, TransactionStateActivity::class.java)
        startActivity(intent)
    }
    fun toAddState(view: View) {
        val intent = Intent(this, AddStateActivity::class.java)
        startActivity(intent)
    }
    fun toStatsState(view: View) {
        val intent = Intent(this, StatsStateActivity::class.java)
        startActivity(intent)
    }
    fun toProfileState(view: View) {
        val intent = Intent(this, ProfileStateActivity::class.java)
        startActivity(intent)
    }

    fun saveData() {
        val pref = getSharedPreferences("database", MODE_PRIVATE)

        pref.edit().putString("isInitialized", Gson().toJson(isInitialized)).apply()

        pref.edit().putString("balance", Gson().toJson(balance)).apply()
        pref.edit().putString("allIncome", Gson().toJson(income)).apply()
        pref.edit().putString("allOutcome", Gson().toJson(outcome)).apply()

        pref.edit().putString("allExpenditureName", Gson().toJson(expenditureName)).apply()
        pref.edit().putString("allExpenditureCost", Gson().toJson(expenditureCost)).apply()
        pref.edit().putString("allConsumptionInfo", Gson().toJson(consumptionInfo)).apply()
    }

    private fun loadData() {
        val pref = getSharedPreferences("database", MODE_PRIVATE)
        var jsonData = pref.getString("isInitialized", null)
        isInitialized = jsonData != null

        jsonData = pref.getString("balance", null)
        if(jsonData != null)
            balance = Gson().fromJson(jsonData,Long::class.java)
        else
            balance = 0

        jsonData = pref.getString("allIncome", null)
        if(jsonData != null) {
            val type: Type = object : TypeToken<ArrayList<Long?>?>() {}.type
            income = Gson().fromJson(jsonData, type) as ArrayList<Long>
        }
        else
            income = ArrayList()

        jsonData = pref.getString("allOutcome", null)
        if(jsonData != null) {
            val type: Type = object : TypeToken<ArrayList<Long?>?>() {}.type
            outcome = Gson().fromJson(jsonData, type) as ArrayList<Long>
        }
        else
            outcome = ArrayList()

        jsonData = pref.getString("allExpenditureName", null)
        if(jsonData != null) {
            val type: Type = object : TypeToken<ArrayList<String?>?>() {}.type
            expenditureName = Gson().fromJson(jsonData, type) as ArrayList<String>
        }
        else
            expenditureName = ArrayList()

        jsonData = pref.getString("allExpenditureCost", null)
        if(jsonData != null) {
            val type: Type = object : TypeToken<ArrayList<Long?>?>() {}.type
            expenditureCost = Gson().fromJson(jsonData, type) as ArrayList<Long>
        }
        else
            expenditureCost = ArrayList()

        jsonData = pref.getString("allConsumptionInfo", null)
        if(jsonData != null) {
            val type: Type = object : TypeToken<ArrayList<ExpenditureCostNode?>?>() {}.type
            consumptionInfo = Gson().fromJson(jsonData, type) as ArrayList<ExpenditureCostNode>
        }
        else
            consumptionInfo = ArrayList()
    }

    fun createDefaultData() {

    }
}