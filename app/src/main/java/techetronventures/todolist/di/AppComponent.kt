package techetronventures.todolist.di
import dagger.Component
import techetronventures.todolist.viewModel.MainActivityViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent{
    fun inject(activity: MainActivityViewModel)
}