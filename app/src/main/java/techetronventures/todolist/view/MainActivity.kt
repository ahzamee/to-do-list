package techetronventures.todolist.view

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import techetronventures.todolist.R
import techetronventures.todolist.adapter.TodoListAdapter
import techetronventures.todolist.database.AppEntity
import techetronventures.todolist.util.todayDate
import techetronventures.todolist.viewModel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var todoListAdapter: TodoListAdapter
    private lateinit var todayDateTxt: TextView
    private lateinit var greetingTxt: TextView
    private lateinit var additem: MaterialButton
    private lateinit var todoListRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initVariables()
        initTodoListRecyclerView()

        loadTOdoList()
    }

    private fun initVariables() {
        todayDateTxt = findViewById(R.id.today_date)
        greetingTxt = findViewById(R.id.greeting)
        additem = findViewById(R.id.add_item)
        todoListRecyclerView = findViewById(R.id.todo_list_recycler_view)

        todayDateTxt.text = todayDate("dd'th' MMMM")
        greetingTxt.text = resources.getText(R.string.greeting)

        additem.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, TodoFormActivity::class.java))
        })
    }


    private fun loadTOdoList() {
        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        viewModel.getRecordsObserver().observe(this
        ) { t ->
            if (t != null) {
                Log.d("data", "received data: " + t.size.toString())
            }

            todoListAdapter.setTodoList(t)
            todoListAdapter.notifyDataSetChanged()

        }


    }

    private fun initTodoListRecyclerView() {
        var listener = object : TodoListAdapter.OnItemClickListener {
            override fun onItemClick(data: AppEntity) {
                var intent = Intent(this@MainActivity, TodoDetailsActivity::class.java)
                intent.putExtra("TodoItem", data)
                startActivity(intent)
                Log.d("data", data.title.toString())
            }
        }
        todoListRecyclerView.layoutManager = LinearLayoutManager(this)
        todoListAdapter = TodoListAdapter(listener)
        todoListRecyclerView.adapter = todoListAdapter
    }
}