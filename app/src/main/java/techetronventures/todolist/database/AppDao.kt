package techetronventures.todolist.database
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    @Query("SELECT * FROM todo_list")
    fun allTodoList(): Flow<List<AppEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTodo(appEntity: AppEntity)

    @Delete
    fun deleteItem(appEntity: AppEntity)

    @Update
    fun updateItem(appEntity: AppEntity)

}