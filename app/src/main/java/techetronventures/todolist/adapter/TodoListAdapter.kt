package techetronventures.todolist.adapter

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import techetronventures.todolist.R
import techetronventures.todolist.database.AppEntity
import techetronventures.todolist.util.convertDateToFormat
import techetronventures.todolist.util.todayDate

class TodoListAdapter(
    private val listener: OnItemClickListener): RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder>() {

    private lateinit var appEntityList: List<AppEntity>

    interface OnItemClickListener {
        fun onItemClick(data: AppEntity)
    }

    fun setTodoList(AppEntityList: List<AppEntity>?) {
        this.appEntityList = AppEntityList!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_todo_item, parent, false)

        return TodoListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        appEntityList.let { holder.bind(it[position], listener) }
    }

    override fun getItemCount(): Int {
        return appEntityList.size
    }

    inner class TodoListViewHolder (view: View): RecyclerView.ViewHolder(view){

        private val itemTodoTitleTxtView: TextView = view.findViewById(R.id.item_todo_title)
        private val itemTodoDateTxtView: TextView = view.findViewById(R.id.item_todo_date)
        private val itemCircleView: View = view.findViewById(R.id.item_circle)
        //private val itemLineView: View = view.findViewById(R.id.item_line)

        fun bind(data: AppEntity, listener: OnItemClickListener){
            itemTodoTitleTxtView.text = data.title
            itemTodoDateTxtView.text = data.dateTime?.let { convertDateToFormat(it) }

            val res = data.dateTime?.let {
                convertDateToFormat(it,"yyyy-MM-dd'T'HH:mm:ss","yyyy-MM-dd")
            }?.compareTo(todayDate("yyyy-MM-dd"))

            if (res != null) {
                if (res>0){
                    itemCircleView.setBackgroundResource(R.drawable.round_corner_active)
                }else if(res<0){
                    itemCircleView.setBackgroundResource(R.drawable.round_corner_inactive)
                }else{
                    itemCircleView.setBackgroundResource(R.drawable.round_corner_current)
                }
            }

            itemView.setOnClickListener{listener.onItemClick(data)}
        }
    }
}