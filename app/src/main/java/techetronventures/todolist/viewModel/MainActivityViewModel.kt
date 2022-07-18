package techetronventures.todolist.viewModel
import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import techetronventures.todolist.MyApp
import techetronventures.todolist.database.AppDao
import techetronventures.todolist.database.AppEntity
import javax.inject.Inject

class MainActivityViewModel(application: Application): AndroidViewModel(application){

    @Inject
    lateinit var appDao: AppDao

    var allItemList : LiveData<List<AppEntity>>

    init {
        (application as MyApp).getAppComponent().inject(this)
        allItemList = appDao.allTodoList().asLiveData()
    }

    fun insertAllItem(appEntity: AppEntity){
        viewModelScope.launch {
            appDao.insertTodo(appEntity)
        }
    }

    fun deleteItem(appEntity: AppEntity){
        viewModelScope.launch {
            appDao.deleteItem(appEntity)
        }
    }

    fun updateItem(appEntity: AppEntity){
        viewModelScope.launch {
            appDao.updateItem(appEntity)
        }
    }
}