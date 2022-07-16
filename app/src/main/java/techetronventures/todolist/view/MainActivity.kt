package techetronventures.todolist.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import techetronventures.todolist.R
import techetronventures.todolist.util.todayDate

class MainActivity : AppCompatActivity() {
    private lateinit var todayDateTxt: TextView
    private lateinit var greetingTxt: TextView
    private lateinit var additem: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initVariables()

    }

    private fun initVariables() {
        todayDateTxt = findViewById(R.id.today_date)
        greetingTxt = findViewById(R.id.greeting)
        additem = findViewById(R.id.add_item)

        todayDateTxt.text = todayDate()
        greetingTxt.text = resources.getText(R.string.greeting)

        additem.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, TodoFormActivity::class.java))
        })
    }


}