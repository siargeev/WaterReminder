package mraqs.water.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import mraqs.water.di.common.ViewModelKey
import mraqs.water.ui.volume.VolumeFragment
import mraqs.water.ui.volume.VolumeViewModel

@Module
internal abstract class VolumeFragmentModule {

    @ContributesAndroidInjector
    internal abstract fun volumeFragment(): VolumeFragment

    @Binds
    @IntoMap
    @ViewModelKey(VolumeViewModel::class)
    abstract fun bindFragmentViewModel(viewModel: VolumeViewModel): ViewModel
}
