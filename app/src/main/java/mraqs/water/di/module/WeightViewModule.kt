package mraqs.water.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import mraqs.water.di.common.ViewModelKey
import mraqs.water.ui.intro.weight.WeightFragment
import mraqs.water.ui.intro.weight.WeightViewModel

@Module
internal abstract class WeightViewModule {

    @ContributesAndroidInjector
    internal abstract fun weightFragment(): WeightFragment

    @Binds
    @IntoMap
    @ViewModelKey(WeightViewModel::class)
    abstract fun bindFragmentViewModel(viewModel: WeightViewModel): ViewModel
}
