package techetronventures.todolist

import android.app.Application
import techetronventures.todolist.di.AppComponent
import techetronventures.todolist.di.AppModule
import techetronventures.todolist.di.DaggerAppComponent


class MyApp: Application(){
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        instance = this

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    fun getAppComponent(): AppComponent {
        return appComponent
    }

    companion object {
        lateinit var instance: MyApp
            private set
    }

}
