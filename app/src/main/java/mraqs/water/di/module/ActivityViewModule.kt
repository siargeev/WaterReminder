package mraqs.water.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import mraqs.water.di.common.ViewModelKey
import mraqs.water.ui.intro.activity.ActivityFragment
import mraqs.water.ui.intro.activity.ActivityViewModel

@Module
internal abstract class ActivityViewModule {

    @ContributesAndroidInjector
    internal abstract fun activityFragment(): ActivityFragment

    @Binds
    @IntoMap
    @ViewModelKey(ActivityViewModel::class)
    abstract fun bindFragmentViewModel(viewModel: ActivityViewModel): ViewModel
}
