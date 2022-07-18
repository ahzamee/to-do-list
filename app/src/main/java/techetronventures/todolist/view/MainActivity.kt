package techetronventures.todolist.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
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

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initVariables()
        initTodoListRecyclerView()
        loadTodoList()
    }

    override fun onRestart() {
        super.onRestart()

        Toast.makeText( this,"onRestart", Toast.LENGTH_SHORT).show()

        //loadTodoList()
        // initTodoListRecyclerView()
        //todoListAdapter.notifyDataSetChanged()
    }

    private fun initVariables() {
        todayDateTxt = findViewById(R.id.today_date)
        greetingTxt = findViewById(R.id.greeting)
        additem = findViewById(R.id.add_item)
        todoListRecyclerView = findViewById(R.id.todo_list_recycler_view)

        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        todayDateTxt.text = todayDate("dd'th' MMMM")
        greetingTxt.text = resources.getText(R.string.greeting)

        additem.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, TodoFormActivity::class.java))
        })
    }


    private fun loadTodoList() {
        viewModel.allItemList.observe(this){
            it.let {
                todoListAdapter.setTodoList(it)
                todoListAdapter.notifyDataSetChanged()
            }
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
        todoListAdapter.notifyDataSetChanged()
    }
}