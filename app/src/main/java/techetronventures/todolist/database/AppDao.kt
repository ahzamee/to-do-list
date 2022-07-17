package techetronventures.todolist.database
import androidx.room.*
import techetronventures.todolist.database.AppEntity

@Dao
interface AppDao {

    @Query("SELECT * FROM todo_list")
    fun allTodoList(): List<AppEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTodo(appEntity: AppEntity)

    @Delete
    fun deleteItem(appEntity: AppEntity)

    @Update
    fun updateItem(appEntity: AppEntity)

}