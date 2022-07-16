package techetronventures.todolist.util

import java.text.SimpleDateFormat
import java.util.*

public fun todayDate(pattern :String): String {
    val sdf = SimpleDateFormat(pattern)
    return sdf.format(Date())
}
