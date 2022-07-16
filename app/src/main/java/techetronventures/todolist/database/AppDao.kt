package techetronventures.todolist.database
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import techetronventures.todolist.database.AppEntity

@Dao
interface AppDao {

    @Query("SELECT * FROM todo_list")
    fun allTodoList(): List<AppEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTodo(appEntity: AppEntity)

}