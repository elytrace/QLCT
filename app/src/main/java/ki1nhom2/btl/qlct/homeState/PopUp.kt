package ki1nhom2.btl.qlct.homeState

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.Image
import android.view.*
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import ki1nhom2.btl.qlct.MainActivity
import ki1nhom2.btl.qlct.MainActivity.Companion.toLongFormat
import ki1nhom2.btl.qlct.MainActivity.Companion.toMoneyFormat
import ki1nhom2.btl.qlct.R
import ki1nhom2.btl.qlct.addState.AddStateActivity
import ki1nhom2.btl.qlct.addState.addCost.ExpenditureCostNode
import ki1nhom2.btl.qlct.homeState.monthlyShorten.MonthlyInfoAdapter
import ki1nhom2.btl.qlct.transactionsState.TransactionStateActivity


class PopUp {

    //PopupWindow display method
    fun showPopupWindow(view: View, consumptionInfo : ExpenditureCostNode) {

        //Create a View object yourself through inflater
        val inflater =
            view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.home_popup_monthly_detailed, null)

        //Specify the length and width through constants
        val width = LinearLayout.LayoutParams.MATCH_PARENT
        val height = LinearLayout.LayoutParams.MATCH_PARENT

        //Make Inactive Items Outside Of PopupWindow
        val focusable = true

        //Create a window with our parameters
        val popupWindow = PopupWindow(popupView, width, height, focusable)

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)

        popupWindow.dimBehind()

        //Initialize the elements of our window, install the handler
        val name = popupView.findViewById<EditText>(R.id.name)
        val type = popupView.findViewById<EditText>(R.id.type)
        val date = popupView.findViewById<EditText>(R.id.date)
        val cost = popupView.findViewById<EditText>(R.id.cost)
        val description = popupView.findViewById<EditText>(R.id.description)

        name.hint = consumptionInfo.name
        type.hint = consumptionInfo.type
        date.hint = consumptionInfo.date
        cost.hint = toMoneyFormat(consumptionInfo.cost)
        description.hint = consumptionInfo.description

        popupView.setOnTouchListener { _, _ -> //Close the window when clicked
            popupWindow.dismiss()
            true
        }
    }

    private fun PopupWindow.dimBehind() {
        val container = contentView.rootView
        val context = contentView.context
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val p = container.layoutParams as WindowManager.LayoutParams
        p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        p.dimAmount = 0.9f
        wm.updateViewLayout(container, p)
    }
}