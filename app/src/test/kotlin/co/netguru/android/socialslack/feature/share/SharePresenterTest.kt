package co.netguru.android.socialslack.feature.share

import co.netguru.android.socialslack.RxSchedulersOverrideRule
import co.netguru.android.socialslack.TestHelper.anyObject
import co.netguru.android.socialslack.data.channels.ChannelsProvider
import co.netguru.android.socialslack.data.channels.model.Channel
import org.junit.After
import org.junit.Before
import org.mockito.Mockito.*
import org.junit.Rule
import org.junit.Test

@Suppress("IllegalIdentifier")
class SharePresenterTest {

    companion object {
        private val CHANNEL_MOST_ACTIVE = Channel("1", "", "", false, false, 0, 1)
        private val CHANNEL2 = Channel("2", "", "", false, false, 0, 2)
        private val CHANNEL3 = Channel("3", "", "", false, false, 0, 3)
        private val CHANNEL4 = Channel("4", "", "", false, false, 0, 4)
    }

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    val channelsProvider: ChannelsProvider = mock(ChannelsProvider::class.java)
    lateinit var view: ShareContract.View

    lateinit var sharePresenter: SharePresenter

    @Before
    fun setUp() {
        view = mock(ShareContract.View::class.java)
        sharePresenter = SharePresenter(channelsProvider)
        sharePresenter.attachView(view)
    }

    @Test
    fun `should init channel view when preparing view for channel`() {
        //when
        sharePresenter.prepareView(CHANNEL_MOST_ACTIVE, listOf(CHANNEL_MOST_ACTIVE, CHANNEL2, CHANNEL3))
        //then
        verify(view).initShareChannelView(anyObject(), anyObject())
    }

    @Test
    fun `should show channel name when preparing view for channel`() {
        //when
        sharePresenter.prepareView(CHANNEL_MOST_ACTIVE, listOf(CHANNEL_MOST_ACTIVE, CHANNEL2, CHANNEL3))
        //then
        verify(view).showChannelName(anyString())
    }

    @Test
    fun `should show selected channel most active text when selected channel is most active`() {
        //when
        sharePresenter.prepareView(CHANNEL_MOST_ACTIVE, listOf(CHANNEL_MOST_ACTIVE, CHANNEL2, CHANNEL3))
        //then
        verify(view).showSelectedChannelMostActiveText()
    }

    @Test
    fun `should show selected channel talk more text when selected channel is not most active`() {
        //when
        sharePresenter.prepareView(CHANNEL2, listOf(CHANNEL_MOST_ACTIVE, CHANNEL2, CHANNEL3))
        //then
        verify(view).showSelectedChannelTalkMoreText()
    }

    @Test
    fun `should show extra item when selected channel position is greater than last most active channel position`() {
        //when
        sharePresenter.prepareView(CHANNEL4, listOf(CHANNEL_MOST_ACTIVE, CHANNEL2, CHANNEL3, CHANNEL4))
        //then
        verify(view).showSelectedChannelOnLastPosition(anyObject())
    }

    @Test
    fun `should not show extra item when selected channel position is in most active channels list`() {
        //when
        sharePresenter.prepareView(CHANNEL3, listOf(CHANNEL_MOST_ACTIVE, CHANNEL2, CHANNEL3, CHANNEL4))
        //then
        verify(view, never()).showSelectedChannelOnLastPosition(anyObject())
    }

    @Test
    fun `should dismiss view when on close button click`() {
        //when
        sharePresenter.onCloseButtonClick()
        //then
        verify(view).dismissView()
    }

    @After
    fun tearDown() {
        sharePresenter.detachView(false)
    }
}