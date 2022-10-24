package ki1nhom2.btl.qlct.homeState.monthlyShorten

import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import ki1nhom2.btl.qlct.R
import ki1nhom2.btl.qlct.addState.AddStateActivity
import ki1nhom2.btl.qlct.homeState.HomeStateActivity

class MonthlyInfoAdapter(private val mList: List<MonthlyInfoNode>) : RecyclerView.Adapter<MonthlyInfoAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.home_node_monthly_info, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val monthlyStatisticStructure = mList[position]

        holder.monthName.text = monthlyStatisticStructure.monthName
        holder.income.text = monthlyStatisticStructure.income.toString()
        holder.outcome.text = monthlyStatisticStructure.outcome.toString()

        holder.dropDown.setOnClickListener {
            generateConsumptionList(holder, monthlyStatisticStructure.monthName)
            HomeStateActivity.adapter.notifyItemChanged(position)
        }
    }

    private fun generateConsumptionList(holder : ViewHolder, monthName : String) {

        val child : View = LayoutInflater.from(holder.dropDown.context).inflate(R.layout.home_consumption_details, null)
        val name : TextView = child.findViewById(R.id.name)
        val type : TextView = child.findViewById(R.id.type)
        val cost : TextView = child.findViewById(R.id.cost)
        val date : TextView = child.findViewById(R.id.date)
        val btnSeeMore : MaterialButton = child.findViewById(R.id.btnSeeMore)

        for(i in 0 until AddStateActivity.consumptionInfo.size) {
            if(AddStateActivity.consumptionInfo[i].date.substring(3, 5) == monthName) {
                name.text = AddStateActivity.consumptionInfo[i].name
                type.text = AddStateActivity.consumptionInfo[i].type
                cost.text = AddStateActivity.consumptionInfo[i].cost.toString()
                date.text = AddStateActivity.consumptionInfo[i].date

                holder.list.addView(child)
            }
        }
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
        val dropDown : ImageButton = itemView.findViewById(R.id.dropdown)
        val list : LinearLayout = itemView.findViewById(R.id.consumptionList)

    }
}
