package co.netguru.android.socialslack.feature.channels.profile

import co.netguru.android.socialslack.RxSchedulersOverrideRule
import co.netguru.android.socialslack.TestHelper.whenever
import co.netguru.android.socialslack.data.channels.ChannelsProvider
import co.netguru.android.socialslack.data.channels.model.ChannelMessages
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@Suppress("IllegalIdentifier")
class ChannelProfilePresenterTest {

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    val TS: Float = 1000F
    val USER_ID = "<@User>"

    val messageList: MutableList<ChannelMessages> = mutableListOf()

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
            add(ChannelMessages(ChannelMessages.MESSAGE_TYPE, TS, "user", ChannelMessages.HERE_TAG))
            add(ChannelMessages(ChannelMessages.MESSAGE_TYPE, TS, "user", USER_ID))
            add(ChannelMessages(ChannelMessages.MESSAGE_TYPE, TS, "user", ChannelMessages.HERE_TAG))
            add(ChannelMessages("OtherType", TS, "user", "Other User"))
            add(ChannelMessages(ChannelMessages.MESSAGE_TYPE, TS, "user", USER_ID))
            add(ChannelMessages("OtherType", TS, "user", ChannelMessages.HERE_TAG))
        }
        whenever(channelHistoryProvider.getMessagesForChannel(ArgumentMatchers.anyString()))
                .thenReturn(Observable.fromIterable(messageList))
        // when
        presenter.getChannelInfo("Channel")
        // then
        verify(view).showChannelInfo(2, 2)
    }

    @Test
    fun `should show an error when error is returned`() {
        // given
        whenever(channelHistoryProvider.getMessagesForChannel(ArgumentMatchers.anyString()))
                .thenReturn(Observable.error(Throwable()))
        // when
        presenter.getChannelInfo("Channel")
        // then
        verify(view).showError()
    }

    @After
    fun tearDown() {
        presenter.detachView(false)
    }

}