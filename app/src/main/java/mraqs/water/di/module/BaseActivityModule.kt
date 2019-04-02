package mraqs.water.di.module

import dagger.Module
import dagger.Provides

@Module
class BaseActivityModule {

    @Provides
    fun providesNavItem(navItem: Int): Int = navItem
}