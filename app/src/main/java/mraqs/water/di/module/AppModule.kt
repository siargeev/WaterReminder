package mraqs.water.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import mraqs.water.App

@Module
class AppModule {

    @Provides
    fun providesContext(application: App): Context {
        return application.applicationContext
    }
}