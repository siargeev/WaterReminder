package mraqs.water.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import mraqs.water.di.common.ViewModelKey
import mraqs.water.ui.gdpr.GdprActivity
import mraqs.water.ui.gdpr.GdprViewModel
import mraqs.water.ui.main.home.HomeActivity
import mraqs.water.ui.main.home.HomeViewModel

@Module
internal abstract class GdprActivityModule {

    @ContributesAndroidInjector
    internal abstract fun gdprActivity(): GdprActivity

    @Binds
    @IntoMap
    @ViewModelKey(GdprViewModel::class)
    abstract fun bindGdprViewModel(viewModel: GdprViewModel): ViewModel
}