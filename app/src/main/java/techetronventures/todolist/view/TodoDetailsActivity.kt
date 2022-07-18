package techetronventures.todolist.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import techetronventures.todolist.R
import techetronventures.todolist.database.AppEntity
import techetronventures.todolist.util.convertDateToFormat
import techetronventures.todolist.viewModel.MainActivityViewModel

class TodoDetailsActivity : AppCompatActivity() {

    private lateinit var itemDateTime: TextView
    private lateinit var itemTitle: TextView
    private lateinit var itemNote: TextView
    private lateinit var itemEdit: MaterialButton
    private lateinit var itemDelete: MaterialButton

    private lateinit var appEntity: AppEntity
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_details)

        initVariables()
    }

    private fun initVariables() {
        itemDateTime = findViewById(R.id.item_date_time)
        itemTitle = findViewById(R.id.item_title)
        itemNote = findViewById(R.id.item_note)
        itemEdit = findViewById(R.id.item_edit)
        itemDelete = findViewById(R.id.item_delete)

        appEntity = intent.getSerializableExtra("TodoItem") as AppEntity
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        itemDateTime.text = convertDateToFormat(appEntity.dateTime.toString())
        itemTitle.text = appEntity.title.toString()
        itemNote.text = appEntity.note.toString()

        itemEdit.setOnClickListener(){
            editItem()
        }
        itemDelete.setOnClickListener(){
            deleteItem()
        }
    }

    private fun deleteItem() {
        viewModel.deleteItem(appEntity)


    }

    private fun editItem() {
        var intent = Intent(this, TodoFormActivity::class.java)
        intent.putExtra("TodoItem", appEntity)
        startActivity(intent)
    }
}