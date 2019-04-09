package mraqs.water.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import mraqs.water.di.common.ViewModelKey
import mraqs.water.ui.congrats.CongratsFragment
import mraqs.water.ui.congrats.CongratsViewModel
import mraqs.water.ui.volume.VolumeFragment
import mraqs.water.ui.volume.VolumeViewModel

@Module
internal abstract class CongratsFragmentModule {

    @ContributesAndroidInjector
    internal abstract fun congratsFragment(): CongratsFragment

    @Binds
    @IntoMap
    @ViewModelKey(CongratsViewModel::class)
    abstract fun bindFragmentViewModel(viewModel: CongratsViewModel): ViewModel
}
