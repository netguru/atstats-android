package co.netguru.android.socialslack.feature.channels.profile

import co.netguru.android.socialslack.RxSchedulersOverrideRule
import co.netguru.android.socialslack.TestHelper.whenever
import co.netguru.android.socialslack.data.channels.ChannelsProvider
import co.netguru.android.socialslack.data.channels.model.ChannelMessage
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*

@Suppress("IllegalIdentifier")
class ChannelProfilePresenterTest {

    companion object {
        @JvmStatic
        val TS: Float = 1000F
        @JvmStatic
        val USER_ID = "<@User>"
        @JvmStatic
        val CHANNEL = "channel"
        @JvmStatic
        val USER = "user"
        @JvmStatic
        val OTHER_TYPE = "otherType"
        @JvmStatic
        val OTHER_USER = "Other User"
    }

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    val messageList: MutableList<ChannelMessage> = mutableListOf()

    val channelHistoryProvider: ChannelsProvider = mock(ChannelsProvider::class.java)
    lateinit var view: ChannelProfileContract.View

    lateinit var presenter: ChannelProfileContract.Presenter

    @Before
    fun setUp() {
        view = mock(ChannelProfileContract.View::class.java)

        presenter = ChannelProfilePresenter(channelHistoryProvider, USER_ID)
        presenter.attachView(view)
    }

    @Test
    fun `should show correct number of heres and mentions when getting messages from channel`() {
        // given
        messageList.apply {
            add(ChannelMessage(1, ChannelMessage.MESSAGE_TYPE, CHANNEL, TS, USER, ChannelMessage.HERE_TAG))
            add(ChannelMessage(2, ChannelMessage.MESSAGE_TYPE, CHANNEL, TS, USER, USER_ID))
            add(ChannelMessage(3, ChannelMessage.MESSAGE_TYPE, CHANNEL, TS, USER, ChannelMessage.HERE_TAG))
            add(ChannelMessage(4, OTHER_TYPE, CHANNEL, TS, USER, OTHER_USER))
            add(ChannelMessage(5, ChannelMessage.MESSAGE_TYPE, CHANNEL, TS, USER, USER_ID))
            add(ChannelMessage(6, OTHER_TYPE, CHANNEL, TS, USER, ChannelMessage.HERE_TAG))
        }
        whenever(channelHistoryProvider.getMessagesForChannel(ArgumentMatchers.anyString()))
                .thenReturn(Observable.fromIterable(messageList))
        // when
        presenter.getChannelInfo(CHANNEL)
        // then
        verify(view).showChannelInfo(2, 2)
    }

    @Test
    fun `should show an error when error is returned`() {
        // given
        whenever(channelHistoryProvider.getMessagesForChannel(ArgumentMatchers.anyString()))
                .thenReturn(Observable.error(Throwable()))
        // when
        presenter.getChannelInfo(CHANNEL)
        // then
        verify(view).showError()
    }

    @Test
    fun `should show loading view when the channel message are request`() {
        // given
        whenever(channelHistoryProvider.getMessagesForChannel(ArgumentMatchers.anyString()))
                .thenReturn(Observable.fromIterable(messageList))
        // when
        presenter.getChannelInfo(CHANNEL)
        // then
        verify(view).showLoadingView()
    }

    @Test
    fun `should show hide view when the channel messages call is successful`() {
        // given
        whenever(channelHistoryProvider.getMessagesForChannel(ArgumentMatchers.anyString()))
                .thenReturn(Observable.fromIterable(messageList))
        // when
        presenter.getChannelInfo(CHANNEL)
        // then
        verify(view).hideLoadingView()
    }

    @Test
    fun `should show hide view when the channel messages call returns error`() {
        // given
        whenever(channelHistoryProvider.getMessagesForChannel(ArgumentMatchers.anyString()))
                .thenReturn(Observable.error(Throwable()))
        // when
        presenter.getChannelInfo(CHANNEL)
        // then
        verify(view).hideLoadingView()
    }

    @After
    fun tearDown() {
        presenter.detachView(false)
    }

}