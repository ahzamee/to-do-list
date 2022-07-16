package techetronventures.todolist.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import techetronventures.todolist.R
import java.util.*

class TodoFormActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener{

    private lateinit var todoTitle: TextInputLayout
    private lateinit var todoNote: TextInputLayout
    private lateinit var dateTime: MaterialButton
    private lateinit var newTodoSave: MaterialButton
    private lateinit var todoDateTime:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_form)

        initVariables()
    }

    private fun initVariables() {
        todoTitle = findViewById(R.id.todo_title)
        todoNote = findViewById(R.id.todo_note)
        dateTime = findViewById(R.id.date_time)
        newTodoSave = findViewById(R.id.save_item)

        dateTime.setOnClickListener(View.OnClickListener {
            val calendar: Calendar = Calendar.getInstance()

            val datePickerDialog = DatePickerDialog(this, this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
            datePickerDialog.show()
        })

        newTodoSave.setOnClickListener(View.OnClickListener {

        })
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        val calendar: Calendar = Calendar.getInstance()
        //yy-mm-ddThh:mm:ss
        todoDateTime = "$year-$month-$day"

        val timePickerDialog = TimePickerDialog(this, this, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE),
            DateFormat.is24HourFormat(this))
        timePickerDialog.show()
    }

    override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {
        todoDateTime = todoDateTime+"T"+"$hourOfDay:$minute:0"

        dateTime.text = todoDateTime
    }
}