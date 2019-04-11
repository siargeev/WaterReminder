package mraqs.water.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import mraqs.water.di.common.ViewModelKey
import mraqs.water.ui.interval.IntervalFragment
import mraqs.water.ui.interval.IntervalViewModel

@Module
internal abstract class IntervalFragmentModule {

    @ContributesAndroidInjector
    internal abstract fun intervalFragment(): IntervalFragment

    @Binds
    @IntoMap
    @ViewModelKey(IntervalViewModel::class)
    abstract fun bindFragmentViewModel(viewModel: IntervalViewModel): ViewModel
}
