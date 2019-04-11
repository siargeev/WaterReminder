package mraqs.water.di.component

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import mraqs.water.App
import mraqs.water.di.common.ViewModelBuilder
import mraqs.water.di.module.ActivityViewModule
import mraqs.water.di.module.AppModule
import mraqs.water.di.module.BaseActivityModule
import mraqs.water.di.module.CongratsFragmentModule
import mraqs.water.di.module.GdprActivityModule
import mraqs.water.di.module.GenderViewModule
import mraqs.water.di.module.HomeActivityModule
import mraqs.water.di.module.IntervalFragmentModule
import mraqs.water.di.module.SettingsActivityModule
import mraqs.water.di.module.SplachActivityModule
import mraqs.water.di.module.VolumeFragmentModule
import mraqs.water.di.module.WarningFragmentModule
import mraqs.water.di.module.WeightViewModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ViewModelBuilder::class,
        AppModule::class,

        SplachActivityModule::class,
        GdprActivityModule::class,
        BaseActivityModule::class,
        HomeActivityModule::class,
        SettingsActivityModule::class,

        WarningFragmentModule::class,
        GenderViewModule::class,
        WeightViewModule::class,
        ActivityViewModule::class,

        VolumeFragmentModule::class,
        CongratsFragmentModule::class,
        IntervalFragmentModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}