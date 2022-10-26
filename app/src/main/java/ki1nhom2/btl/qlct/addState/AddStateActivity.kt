package ki1nhom2.btl.qlct.addState

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import ki1nhom2.btl.qlct.MainActivity
import ki1nhom2.btl.qlct.R
import ki1nhom2.btl.qlct.addState.addCost.ExpenditureCostNode
import ki1nhom2.btl.qlct.addState.addName.ExpenditureInfoAdapter
import ki1nhom2.btl.qlct.addState.addName.ExpenditureInfoNode
import ki1nhom2.btl.qlct.homeState.HomeStateActivity
import ki1nhom2.btl.qlct.homeState.HomeStateActivity.Companion.balance
import ki1nhom2.btl.qlct.homeState.HomeStateActivity.Companion.outcome
import java.util.*


class AddStateActivity : MainActivity() {

    companion object {
        // Add Name
        val expenditureName : ArrayList<String> = ArrayList()
        val expenditureCost : ArrayList<Long> = ArrayList()

        // Add Cost
        val consumptionInfo : ArrayList<ExpenditureCostNode> = ArrayList()
    }

    private val data = ArrayList<ExpenditureInfoNode>()
    lateinit var expenditureList : RecyclerView
    lateinit var message : TextView
    var calendar: Calendar = Calendar.getInstance()

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_choose_state)

        for(i in 0 until expenditureName.size) {
            data.add(
                ExpenditureInfoNode(
                    expenditureName[i], expenditureCost[i])
            )
        }

        changeColor(5)
    }

    fun toAddExpenditureCost(view: View) {
        setContentView(R.layout.add_state_cost)
        message = findViewById(R.id.expenditureNameMessage)

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "dd/MM/yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                val dateDisplay : TextView = findViewById(R.id.dateDisplay)
                dateDisplay.text = sdf.format(calendar.time)
            }

        val btnChooseDate : Button = findViewById(R.id.btnChooseDate)
        btnChooseDate.setOnClickListener {
            DatePickerDialog(this@AddStateActivity,
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        var expenditureTypeChoosing = ""
        var expenditureIndexChoosing = 0
        val expenditureTypeSpinner : Spinner = findViewById(R.id.expenditureTypeSpinner)
        val adapterSpinner : ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, expenditureName)
        adapterSpinner.setDropDownViewResource(android.R.layout.select_dialog_singlechoice)
        expenditureTypeSpinner.adapter = adapterSpinner

        expenditureTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                expenditureTypeChoosing = ""
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                expenditureTypeChoosing = expenditureName[position]
                expenditureIndexChoosing = position
            }
        }

        val confirmButton : Button = findViewById(R.id.confirmButton)
        confirmButton.setOnClickListener {
            val expenditureNameTextField : TextInputEditText = findViewById(R.id.expenditureNameTextField)
            val expenditureCostTextField : TextInputEditText = findViewById(R.id.expenditureCostTextField)
            val dateDisplay : TextView = findViewById(R.id.dateDisplay)
            val description : TextInputEditText = findViewById(R.id.description)

            if(expenditureNameTextField.text?.isEmpty() == true) {
                message.text = "Nhập tên khoản chi!!"
            }
            else if(expenditureCostTextField.text?.isEmpty() == true) {
                message.text = "Nhập số tiền đã chi!!"
            }
            else if(dateDisplay.text?.isEmpty() == true) {
                message.text = "Vui lòng chọn ngày!!"
            }
            else {
                consumptionInfo.add(
                    ExpenditureCostNode(
                        expenditureNameTextField.text.toString(),
                        expenditureCostTextField.text.toString().toLong(),
                        expenditureTypeChoosing,
                        dateDisplay.text as String,
                        description.text.toString()
                    )
                )
                expenditureCost[expenditureIndexChoosing] += expenditureCostTextField.text.toString().toLong()

                val monthChange: Int = consumptionInfo.last().date.subSequence(3, 5).toString().toInt()
                outcome[monthChange - 1] += consumptionInfo.last().cost
                HomeStateActivity.data[monthChange - 1].outcome = outcome[monthChange - 1]

                message.text = "Đã lưu thông tin khoản chi!"
                expenditureNameTextField.text?.clear()
                expenditureCostTextField.text?.clear()
                dateDisplay.text = ""
                description.text?.clear()
            }
        }
    }

    fun toAddExpenditureName(view: View) {
        setContentView(R.layout.add_state_name)
        message = findViewById(R.id.expenditureNameMessage)

        expenditureList = findViewById(R.id.expenditureInfoList)
        expenditureList.layoutManager = LinearLayoutManager(this)

        val adapter = ExpenditureInfoAdapter(data)
        expenditureList.adapter = adapter

        val deleteBtn : ImageButton = findViewById(R.id.expenditureDelete)
        deleteBtn.setOnClickListener {
            for(i in data.size-1 downTo 0) {
                if(data[i].checkBox) {
                    data.removeAt(i)
                    expenditureName.removeAt(i)
                    expenditureCost.removeAt(i)
                    adapter.notifyItemRemoved(i)
                    message.text = "Xóa loại khoản chi thành công"
                }
            }
        }

        val confirmButton : Button = findViewById(R.id.confirmButton)
        confirmButton.setOnClickListener {
            val nameInputted = findViewById<TextInputEditText>(R.id.expenditureNameInputField)
            if(nameInputted.text.toString().isNotEmpty() && !expenditureName.contains(nameInputted.text.toString())) {
                expenditureName.add(nameInputted.text.toString())
                expenditureCost.add(0)
                data.add(
                    ExpenditureInfoNode(
                        expenditureName.last(), expenditureCost.last())
                )
                adapter.notifyItemInserted(expenditureName.size-1)
                message.text = "Thêm loại khoản chi thành công"
                nameInputted.text = null
            }
            else if(expenditureName.contains(nameInputted.text.toString())) {
                message.text = "Loại khoản chi đã tồn tại!"
            }
        }
    }

    fun toAddBudget(view: View) {
        setContentView(R.layout.add_state_budget)
        message = findViewById(R.id.expenditureNameMessage)

        val moneyInput = findViewById<TextInputEditText>(R.id.moneyInput)
        val description = findViewById<TextView>(R.id.description)
        val confirmBtn = findViewById<MaterialButton>(R.id.confirmButton)

        moneyInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            @SuppressLint("SetTextI18n")
            override fun afterTextChanged(p0: Editable?) {
                if(moneyInput.text?.isNotEmpty() == true) {
                    description.text = "Số dư tài khoản mới: ${
                        toMoneyFormat(
                            balance + moneyInput.text.toString().toLong()
                        )
                    }"
                }
            }
        })

        confirmBtn.setOnClickListener {
            balance += moneyInput.text.toString().toLong()
            message.text = "Đã cập nhật số dư!"
            moneyInput.text?.clear()
            description.text = ""
        }
    }

    fun backToAddState(view: View) {
        setContentView(R.layout.add_choose_state)
    }

}