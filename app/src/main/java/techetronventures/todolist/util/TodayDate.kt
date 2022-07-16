package techetronventures.todolist.util

import java.text.SimpleDateFormat
import java.util.*

public fun todayDate(): String {
    val sdf = SimpleDateFormat("dd'th' MMMM")
    return sdf.format(Date())
}
