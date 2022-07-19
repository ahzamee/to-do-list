package techetronventures.todolist.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import techetronventures.todolist.R
import techetronventures.todolist.database.AppEntity
import techetronventures.todolist.util.convertDateToFormat
import techetronventures.todolist.util.showUpdateDialog
import techetronventures.todolist.viewModel.MainActivityViewModel

class TodoDetailsActivity : AppCompatActivity() {

    private lateinit var itemCreatedOn: TextView
    private lateinit var itemUpdatedOn: TextView
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
        itemCreatedOn = findViewById(R.id.item_created_on)
        itemUpdatedOn = findViewById(R.id.item_updated_on)
        itemDateTime = findViewById(R.id.item_date_time)
        itemTitle = findViewById(R.id.item_title)
        itemNote = findViewById(R.id.item_note)
        itemEdit = findViewById(R.id.item_edit)
        itemDelete = findViewById(R.id.item_delete)

        appEntity = intent.getSerializableExtra("TodoItem") as AppEntity
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        val createdOn = resources.getString(R.string.create_on)+convertDateToFormat(appEntity.createdOn.toString(),"dd:mm:yyyy", "dd'th' MMM")
        val updatedOn = resources.getString(R.string.updated_on)+convertDateToFormat(appEntity.updatedOn.toString(),"dd:mm:yyyy", "dd'th' MMM")
        itemCreatedOn.text = createdOn
        itemUpdatedOn.text = updatedOn
        itemDateTime.text = convertDateToFormat(appEntity.dateTime.toString())
        itemTitle.text = appEntity.title.toString()
        itemNote.text = appEntity.note.toString()

        itemEdit.setOnClickListener {
            editItem()
        }
        itemDelete.setOnClickListener{
            deleteItem()
        }
    }

    private fun deleteItem() {
        viewModel.deleteItem(appEntity)
        showUpdateDialog(this,  resources.getString(R.string.update_success))
    }

    private fun editItem() {
        val intent = Intent(this, TodoFormActivity::class.java)
        intent.putExtra("TodoItem", appEntity)
        startActivity(intent)
    }
}