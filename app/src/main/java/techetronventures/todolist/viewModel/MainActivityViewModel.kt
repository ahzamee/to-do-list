package techetronventures.todolist.viewModel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import techetronventures.todolist.MyApp
import techetronventures.todolist.database.AppDao
import techetronventures.todolist.database.AppEntity
import javax.inject.Inject

class MainActivityViewModel(application: Application): AndroidViewModel(application){

    @Inject
    lateinit var appDao: AppDao
    private var allItemList: MutableLiveData<List<AppEntity>>

    init {
        (application as MyApp).getAppComponent().inject(this)

        allItemList = MutableLiveData()
        getAllItem()
    }

    fun getRecordsObserver(): MutableLiveData<List<AppEntity>>{
        return allItemList
    }

    private fun getAllItem(){
        val list = appDao.allTodoList()
        allItemList.postValue(list)
    }

    fun insertAllItem(appEntity: AppEntity){
        appDao.insertTodo(appEntity)
    }

    fun deleteItem(appEntity: AppEntity){
        appDao.deleteItem(appEntity)
    }

    fun updateItem(appEntity: AppEntity){
        appDao.updateItem(appEntity)
    }
}