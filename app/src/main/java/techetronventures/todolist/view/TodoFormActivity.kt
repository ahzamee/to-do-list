package techetronventures.todolist.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import techetronventures.todolist.R
import techetronventures.todolist.database.AppEntity
import techetronventures.todolist.util.todayDate
import techetronventures.todolist.viewModel.MainActivityViewModel
import java.util.*

class TodoFormActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener{

    private lateinit var todoTitleTxtV: TextInputLayout
    private lateinit var todoNoteTxtV: TextInputLayout
    private lateinit var datetimeBtn: MaterialButton
    private lateinit var newTodoSave: MaterialButton
    private lateinit var todoDateTime:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_form)

        initVariables()
    }

    private fun initVariables() {
        todoTitleTxtV = findViewById(R.id.todo_title)
        todoNoteTxtV = findViewById(R.id.todo_note)
        datetimeBtn = findViewById(R.id.date_time)
        newTodoSave = findViewById(R.id.save_item)

        datetimeBtn.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()

            val datePickerDialog = DatePickerDialog(
                this, this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        newTodoSave.setOnClickListener(View.OnClickListener {

            //check all the field is empty or not.
            if ((todoTitleTxtV.editText?.text)?.isBlank() == true ||
                (todoNoteTxtV.editText?.text)?.isBlank() == true || !::todoDateTime.isInitialized
            ){
                Toast.makeText(this, resources.getString(R.string.field_empty), Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)


            val todoEntity = AppEntity(
                title = todoTitleTxtV.editText?.text.toString(),
                note = todoNoteTxtV.editText?.text.toString(),
                dateTime = datetimeBtn.text.toString(),
                status = true,
                createdOn = todayDate("dd:mm:yy"),
                updatedOn = todayDate("dd:mm:yy")
            )

            try {
                viewModel.insertAllRecord(todoEntity)
                showSuccessDialog(resources.getString(R.string.dialog_body_success_insert))
            }catch (e: Exception){
                showSuccessDialog(resources.getString(R.string.failed)+e)
            }

        })
    }

    private fun showSuccessDialog(message: String) {
        val alertDialog: AlertDialog = this.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(R.string.add_new
                ) { dialog, _ ->
                    dialog.dismiss()
                }
                setNegativeButton(R.string.back,
                ){ dialog, _ ->
                    dialog.dismiss()
                    finish()
                }
            }
            builder.setTitle(resources.getString(R.string.dialog_title_status))
            builder.setMessage(message)

            // Create the AlertDialog
            builder.create()
        }
        //show dialog
        alertDialog.show()
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

        datetimeBtn.text = todoDateTime
    }
}