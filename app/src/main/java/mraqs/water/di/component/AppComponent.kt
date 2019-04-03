package mraqs.water.di.component

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import mraqs.water.App
import mraqs.water.di.common.ViewModelBuilder
import mraqs.water.di.module.AppModule
import mraqs.water.di.module.BaseActivityModule
import mraqs.water.di.module.HomeActivityModule
import mraqs.water.di.module.SettingsActivityModule
import mraqs.water.di.module.WeightViewModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ViewModelBuilder::class,
        AppModule::class,

        BaseActivityModule::class,
        HomeActivityModule::class,
        SettingsActivityModule::class,

        WeightViewModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}