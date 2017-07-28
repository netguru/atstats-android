package co.netguru.android.socialslack.feature.channels.profile

import dagger.Module
import dagger.Provides
import javax.inject.Named

// TODO 27.07.2017 remove this module when using user component/module
@Module
class ChannelProfileModule {

    @Named("UserId")
    @Provides
    fun provideUserId(): String {
        return "Anna"
    }
}