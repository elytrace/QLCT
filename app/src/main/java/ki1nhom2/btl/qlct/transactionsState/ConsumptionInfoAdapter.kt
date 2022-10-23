package ki1nhom2.btl.qlct.transactionsState

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ki1nhom2.btl.qlct.R
import ki1nhom2.btl.qlct.homeState.monthlyDetailed.MonthlyDetailedNode

class ConsumptionInfoAdapter(private val mList: List<MonthlyDetailedNode>) : RecyclerView.Adapter<ConsumptionInfoAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_consumption_details, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val monthlyDetailed = mList[position]

        holder.monthName.text = monthlyDetailed.monthName
        holder.income.text = monthlyDetailed.income.toString()
        holder.outcome.text = monthlyDetailed.outcome.toString()


    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val monthName: TextView = itemView.findViewById(R.id.monthName)
        val income: TextView = itemView.findViewById(R.id.income)
        val outcome: TextView = itemView.findViewById(R.id.outcome)

    }
}
