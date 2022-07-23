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
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import techetronventures.todolist.R
import techetronventures.todolist.database.AppEntity
import techetronventures.todolist.util.showUpdateDialog
import techetronventures.todolist.util.todayDate
import techetronventures.todolist.viewModel.MainActivityViewModel
import java.util.*

class TodoFormActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener{

    lateinit var todoItem: AppEntity
    private lateinit var todoTitleTxtV: TextInputLayout
    private lateinit var todoTitleEdt: TextInputEditText
    private lateinit var todoNoteTxtV: TextInputLayout
    private lateinit var todoNoteEdt: TextInputEditText
    private lateinit var datetimeBtn: MaterialButton
    private lateinit var todoSaveBtn: MaterialButton
    private lateinit var todoDateTime:String
    private var isUpdateItem: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_form)

        initVariables()
        checkIntentData()
    }

    private fun checkIntentData() {
        if(intent.getSerializableExtra("TodoItem")!=null){
            todoItem = intent.getSerializableExtra("TodoItem") as AppEntity

            todoTitleEdt.setText(todoItem.title.toString())
            todoNoteEdt.setText(todoItem.note.toString())
            datetimeBtn.text = todoItem.dateTime.toString()

            isUpdateItem = true
        }
    }

    private fun initVariables() {
        todoTitleTxtV = findViewById(R.id.todo_title)
        todoTitleEdt = findViewById(R.id.todo_title_edt)

        todoNoteTxtV = findViewById(R.id.todo_note)
        todoNoteEdt = findViewById(R.id.todo_note_edt)

        datetimeBtn = findViewById(R.id.date_time)
        todoSaveBtn = findViewById(R.id.save_item)

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

        todoSaveBtn.setOnClickListener(View.OnClickListener {

            if (isUpdateItem){
                updateTodoItem(todoItem)
                return@OnClickListener
            }
            newTodoSave()

        })
    }

    private fun updateTodoItem(todoItem: AppEntity) {
        val viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        val todoEntity = AppEntity(
            id = todoItem.id,
            title = todoTitleTxtV.editText?.text.toString(),
            note = todoNoteTxtV.editText?.text.toString(),
            dateTime = datetimeBtn.text.toString(),
            status = true,
            createdOn = todoItem.createdOn,
            updatedOn = todayDate("dd:mm:yy")
        )

        viewModel.updateItem(todoEntity)
        showUpdateDialog(this, resources.getString(R.string.update_success))
    }

    private fun newTodoSave() {
        //check all the field is empty or not.
        if ((todoTitleTxtV.editText?.text)?.isBlank() == true ||
            (todoNoteTxtV.editText?.text)?.isBlank() == true || !::todoDateTime.isInitialized
        ){
            Toast.makeText(this, resources.getString(R.string.field_empty), Toast.LENGTH_SHORT).show()
            return
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
            viewModel.insertAllItem(todoEntity)
            showSuccessDialog(resources.getString(R.string.dialog_body_success_insert))
        }catch (e: Exception){
            showSuccessDialog(resources.getString(R.string.failed)+e)
        }
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
        val month = month+1 //as it is counting from zero
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