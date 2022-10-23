package ki1nhom2.btl.qlct

import android.content.Intent
import android.os.Bundle
import android.view.View
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
        setContentView(R.layout.home_state)

        if(!isInitialized) {
            init()
            isInitialized = true

        }
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