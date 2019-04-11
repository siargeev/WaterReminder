package mraqs.water.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import mraqs.water.di.common.ViewModelKey
import mraqs.water.ui.warning.WarningFragment
import mraqs.water.ui.warning.WarningViewModel

@Module
internal abstract class WarningFragmentModule {

    @ContributesAndroidInjector
    internal abstract fun warningFragment(): WarningFragment

    @Binds
    @IntoMap
    @ViewModelKey(WarningViewModel::class)
    abstract fun bindFragmentViewModel(viewModel: WarningViewModel): ViewModel
}
