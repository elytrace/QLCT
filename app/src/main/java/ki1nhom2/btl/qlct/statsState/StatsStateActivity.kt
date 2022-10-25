package ki1nhom2.btl.qlct.statsState

import android.os.Bundle
import ki1nhom2.btl.qlct.R
import ki1nhom2.btl.qlct.MainActivity

class StatsStateActivity : MainActivity() {

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.stats_state)

        changeColor(3)
    }
}