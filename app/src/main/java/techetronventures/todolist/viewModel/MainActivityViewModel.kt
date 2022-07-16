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
    private var allRecordList: MutableLiveData<List<AppEntity>>

    init {
        (application as MyApp).getAppComponent().inject(this)

        allRecordList = MutableLiveData()
        getAllRecords()
    }

    fun getRecordsObserver(): MutableLiveData<List<AppEntity>>{
        return allRecordList
    }

    private fun getAllRecords(){
        val list = appDao.allTodoList()
        allRecordList.postValue(list)
    }

    fun insertAllRecord(appEntity: AppEntity){
        appDao.insertTodo(appEntity)
    }
}