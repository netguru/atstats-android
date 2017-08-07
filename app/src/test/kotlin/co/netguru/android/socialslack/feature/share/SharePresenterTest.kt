package co.netguru.android.socialslack.feature.share

import co.netguru.android.socialslack.RxSchedulersOverrideRule
import co.netguru.android.socialslack.TestHelper.anyObject
import co.netguru.android.socialslack.data.channels.ChannelsController
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@Suppress("IllegalIdentifier")
class SharePresenterTest {

    companion object {
        private val CHANNEL_MOST_ACTIVE = ChannelStatistics("1", "", 10, 3, 3, 3)
        private val CHANNEL2 = ChannelStatistics("1", "", 5, 3, 3, 3)
        private val CHANNEL3 = ChannelStatistics("1", "", 5, 3, 3, 3)
        private val CHANNEL4 = ChannelStatistics("1", "", 5, 3, 3, 3)
    }

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    val channelsController: ChannelsController = mock(ChannelsController::class.java)
    lateinit var view: ShareContract.View

    lateinit var sharePresenter: SharePresenter

    @Before
    fun setUp() {
        view = mock(ShareContract.View::class.java)
        sharePresenter = SharePresenter(channelsController)
        sharePresenter.attachView(view)

        CHANNEL_MOST_ACTIVE.currentPositionInList = 1
        CHANNEL2.currentPositionInList = 2
        CHANNEL3.currentPositionInList = 3
        CHANNEL4.currentPositionInList = 4
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