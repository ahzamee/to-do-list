package techetronventures.todolist.di
import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import techetronventures.todolist.database.AppDao
import techetronventures.todolist.database.AppDatabase
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Singleton
    @Provides
    fun getAppDao(appDatabase: AppDatabase): AppDao {
        return appDatabase.getAppDao()
    }

    @Singleton
    @Provides
    fun getRoomDbInstance(): AppDatabase{
        return AppDatabase.getAppDBInstance(provideAppContext())
    }

    @Singleton
    @Provides
    fun provideAppContext(): Context {
        return application.applicationContext
    }
}