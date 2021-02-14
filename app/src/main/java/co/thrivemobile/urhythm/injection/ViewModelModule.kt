package co.thrivemobile.urhythm.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.thrivemobile.urhythm.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun postListViewModel(viewModel: MainViewModel): ViewModel
}