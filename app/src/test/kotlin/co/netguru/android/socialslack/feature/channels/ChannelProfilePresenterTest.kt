package co.netguru.android.socialslack.feature.channels

import android.util.JsonReader
import co.netguru.android.socialslack.RxSchedulersOverrideRule
import co.netguru.android.socialslack.TestHelper.whenever
import co.netguru.android.socialslack.data.channels.ChannelHistoryProvider
import co.netguru.android.socialslack.data.channels.model.ChannelHistory
import co.netguru.android.socialslack.feature.channels.profile.ChannelProfileContract
import co.netguru.android.socialslack.feature.channels.profile.ChannelProfilePresenter
import com.google.gson.Gson
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import java.io.File
import java.io.FileReader


class ChannelProfilePresenterTest {

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()
    val gson = Gson()
    val channelHistoryJson = FileReader(File(ClassLoader.getSystemResource("channel_history.json").toURI()))


    val channelHistoryProvider = mock(ChannelHistoryProvider::class.java)
    lateinit var view: ChannelProfileContract.View

    lateinit var presenter: ChannelProfileContract.Presenter

    @Before
    fun setUp() {
        view = mock(ChannelProfileContract.View::class.java)

        presenter = ChannelProfilePresenter(channelHistoryProvider)
        presenter.attachView(view)
    }

    @Test
    fun shouldShowNumberOfHeresAndMentions () {
        // given
        whenever(channelHistoryProvider.getMessagesForChannel(ArgumentMatchers.anyString()))
                .thenReturn(Observable.fromIterable(gson.fromJson(channelHistoryJson, ChannelHistory::class.java).messageList))
        // when
        presenter.getChannelInfo("Channel")
        // then
        verify(view).showChannelInfo(1, 3)
    }

}