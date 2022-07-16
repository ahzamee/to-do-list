package techetronventures.todolist.database
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "todo_list")

class AppEntity(
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "id")val id: Int = 0,
    @ColumnInfo(name = "title")val title: String?,
    @ColumnInfo(name = "note")val note: String?,
    @ColumnInfo(name = "dateTime")val dateTime: String?,
    @ColumnInfo(name = "status")val status: Boolean?,
    @ColumnInfo(name = "createdOn")val createdOn: String?,
    @ColumnInfo(name = "updatedOn")val updatedOn: String?
): Serializable