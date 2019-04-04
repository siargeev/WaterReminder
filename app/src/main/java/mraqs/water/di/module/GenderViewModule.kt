package mraqs.water.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import mraqs.water.di.common.ViewModelKey
import mraqs.water.ui.intro.gender.GenderFragment
import mraqs.water.ui.intro.gender.GenderViewModel

@Module
internal abstract class GenderViewModule {

    @ContributesAndroidInjector
    internal abstract fun genderFragment(): GenderFragment

    @Binds
    @IntoMap
    @ViewModelKey(GenderViewModel::class)
    abstract fun bindFragmentViewModel(viewModel: GenderViewModel): ViewModel
}
