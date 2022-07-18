package techetronventures.todolist.util

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import techetronventures.todolist.R
import techetronventures.todolist.view.MainActivity
import kotlin.concurrent.thread

public fun showUpdateDialog(activity: Activity, message: String) {
    val alertDialog: AlertDialog = activity.let {
        val builder = AlertDialog.Builder(it)
        builder.setTitle(activity.resources.getString(R.string.dialog_title_status))
        builder.setMessage(message)

        // Create the AlertDialog
        builder.create()
    }
    //show dialog
    alertDialog.show()

    thread {
        Thread.sleep (5000)
        alertDialog.dismiss()
        activity.finish()
        activity.startActivity(Intent(activity, MainActivity::class.java))
    }
}