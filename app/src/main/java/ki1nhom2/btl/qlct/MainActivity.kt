package ki1nhom2.btl.qlct

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ki1nhom2.btl.qlct.addState.AddStateActivity
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

open class MainActivity : AppCompatActivity() {

    companion object {

        // variables

        var isInitialized : Boolean = false

        val displayMetrics = DisplayMetrics()

        var width = displayMetrics.widthPixels
        var height = displayMetrics.heightPixels

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

        fun calculateBalance() {
            for(i in 1..12) {
                balance += income[i] - outcome[i]
            }
        }
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(!isInitialized) {
            init()
            isInitialized = true
        }
        windowManager.defaultDisplay.getMetrics(displayMetrics)
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

        val listBtn : ArrayList<ImageButton> = arrayListOf(btnHome, btnTrans, btnStats, btnProfile)
        val listTxt : ArrayList<TextView> = arrayListOf(txtHome, txtTrans, txtStats, txtProfile)

        listBtn[contentIndex-1].setColorFilter(Color.BLACK)
        listTxt[contentIndex-1].setTextColor(Color.BLACK)
    }

    private fun init() {
        for(i in 1..12) {
            income.add(0)
            outcome.add(0)
            data.add(
                MonthlyInfoNode(
                    monthNames[i-1],
                    toMoneyFormat(income[i-1]).toLong(),
                    toMoneyFormat(outcome[i-1]).toLong()
                )
            )
        }

        expenditureName.add("Tiền nhà")
        expenditureName.add("Tiền điện")
        expenditureName.add("Tiền nước")

        expenditureCost.add(0)
        expenditureCost.add(0)
        expenditureCost.add(0)
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
}