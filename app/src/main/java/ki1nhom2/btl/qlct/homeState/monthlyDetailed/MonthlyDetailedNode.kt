package ki1nhom2.btl.qlct.homeState.monthlyDetailed

import ki1nhom2.btl.qlct.addState.addCost.ExpenditureCostNode

class MonthlyDetailedNode(
    var monthName: String,
    var income: Long,
    var outcome: Long,
    // define later
    var consumptionList : ArrayList<ExpenditureCostNode>
)