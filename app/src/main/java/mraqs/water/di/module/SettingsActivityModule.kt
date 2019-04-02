package mraqs.water.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import mraqs.water.di.common.ViewModelKey
import mraqs.water.ui.main.settings.SettingsActivity
import mraqs.water.ui.main.settings.SettingsViewModel

@Module
internal abstract class SettingsActivityModule {

    @ContributesAndroidInjector
    internal abstract fun settingsActivity(): SettingsActivity

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModel(viewModel: SettingsViewModel): ViewModel
}