package co.netguru.android.atstats.feature.share

import co.netguru.android.atstats.RxSchedulersOverrideRule
import co.netguru.android.atstats.TestHelper.anyObject
import co.netguru.android.atstats.data.channels.ChannelsController
import co.netguru.android.atstats.data.channels.model.ChannelStatistics
import co.netguru.android.atstats.data.filter.model.ChannelsFilterOption
import co.netguru.android.atstats.data.filter.model.UsersFilterOption
import co.netguru.android.atstats.data.user.model.UserStatistic
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
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
        private val MOCKED_CHANNELS_FILTER_OPTION = ChannelsFilterOption.MOST_ACTIVE_CHANNEL.name

        private val USER1 = UserStatistic("1", "", "", "", "", 1, 1, 2, 1, null, 1)
        private val USER2 = UserStatistic("2", "", "", "", "", 2, 2, 4, 1, null, 2)
        private val USER3 = UserStatistic("3", "", "", "", "", 3, 3, 6, 1, null, 3)
        private val USER4 = UserStatistic("4", "", "", "", "", 4, 4, 8, 1, null, 4)
        private val MOCKED_USERS_FILTER_OPTION = UsersFilterOption.PERSON_WHO_WE_TALK_THE_MOST.name
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
        sharePresenter.prepareView(CHANNEL_MOST_ACTIVE, listOf(CHANNEL_MOST_ACTIVE, CHANNEL2, CHANNEL3), MOCKED_CHANNELS_FILTER_OPTION)
        //then
        verify(view).initShareChannelView(anyObject(), anyObject())
    }

    @Test
    fun `should init user view when preparing view for user`() {
        //when
        sharePresenter.prepareView(USER1, listOf(USER1, USER2, USER3), MOCKED_USERS_FILTER_OPTION)
        //then
        verify(view).initShareUsersView(anyObject(), anyObject())
    }

    @Test
    fun `should show channel name when preparing view for channel`() {
        //when
        sharePresenter.prepareView(CHANNEL_MOST_ACTIVE, listOf(CHANNEL_MOST_ACTIVE, CHANNEL2, CHANNEL3), MOCKED_CHANNELS_FILTER_OPTION)
        //then
        verify(view).showChannelName(anyString())
    }

    @Test
    fun `should show messages number title when preparing view for channels and filter option is not connected with mentions`() {
        //when
        sharePresenter.prepareView(CHANNEL_MOST_ACTIVE, listOf(CHANNEL_MOST_ACTIVE, CHANNEL2, CHANNEL3), MOCKED_CHANNELS_FILTER_OPTION)
        //then
        verify(view).showMessagesNrTitle()
    }

    @Test
    fun `should show messages number title when preparing view for channels and filter option is connected with mentions`() {
        //when
        sharePresenter.prepareView(CHANNEL_MOST_ACTIVE, listOf(CHANNEL_MOST_ACTIVE, CHANNEL2, CHANNEL3), ChannelsFilterOption.CHANNEL_WE_ARE_MENTIONED_THE_MOST.name)
        //then
        verify(view).showMentionsNrTitle()
    }

    @Test
    fun `should show messages number title when preparing view for user`() {
        //when
        sharePresenter.prepareView(USER1, listOf(USER1, USER2, USER3), MOCKED_USERS_FILTER_OPTION)
        //then
        verify(view).showMessagesNrTitle()
    }

    @Test
    fun `should show channel name when preparing view for user`() {
        //when
        sharePresenter.prepareView(USER1, listOf(USER1, USER2, USER3), MOCKED_USERS_FILTER_OPTION)
        //then
        verify(view).showChannelName(anyString())
    }

    @Test
    fun `should show selected channel most active text when selected channel is most active`() {
        //when
        sharePresenter.prepareView(CHANNEL_MOST_ACTIVE, listOf(CHANNEL_MOST_ACTIVE, CHANNEL2, CHANNEL3), MOCKED_CHANNELS_FILTER_OPTION)
        //then
        verify(view).showSelectedChannelMostActiveText()
    }

    @Test
    fun `should show selected channel talk more text when selected channel is not most active`() {
        //when
        sharePresenter.prepareView(CHANNEL2, listOf(CHANNEL_MOST_ACTIVE, CHANNEL2, CHANNEL3), MOCKED_CHANNELS_FILTER_OPTION)
        //then
        verify(view).showSelectedChannelTalkMoreText()
    }

    @Test
    fun `should show selected user most active text when selected user is most active`() {
        //when
        sharePresenter.prepareView(USER1, listOf(USER1, USER2, USER3), MOCKED_USERS_FILTER_OPTION)
        //then
        verify(view).showSelectedUserMostActiveText()
    }

    @Test
    fun `should show selected user talk more text when selected user is not most active`() {
        //when
        sharePresenter.prepareView(USER2, listOf(USER1, USER2, USER3), MOCKED_USERS_FILTER_OPTION)
        //then
        verify(view).showSelectedUserTalkMoreText()
    }

    @Test
    fun `should show extra item when selected channel position is greater than last channel position in list`() {
        //when
        sharePresenter.prepareView(CHANNEL4, listOf(CHANNEL_MOST_ACTIVE, CHANNEL2, CHANNEL3), MOCKED_CHANNELS_FILTER_OPTION)
        //then
        verify(view).showSelectedChannelOnLastPosition(anyObject(), anyObject())
    }

    @Test
    fun `should show extra item when selected user position is greater than last user position in list`() {
        //when
        sharePresenter.prepareView(USER4, listOf(USER1, USER2, USER3), MOCKED_USERS_FILTER_OPTION)
        //then
        verify(view).showSelectedUserOnLastPosition(anyObject(), anyObject())
    }

    @Test
    fun `should not show extra item when selected channel position is in channels list`() {
        //when
        sharePresenter.prepareView(CHANNEL3, listOf(CHANNEL_MOST_ACTIVE, CHANNEL2, CHANNEL3), MOCKED_CHANNELS_FILTER_OPTION)
        //then
        verify(view, never()).showSelectedChannelOnLastPosition(anyObject(), anyObject())
    }

    @Test
    fun `should not show extra item when selected user position is in list`() {
        //when
        sharePresenter.prepareView(USER1, listOf(USER1, USER2, USER3), MOCKED_USERS_FILTER_OPTION)
        //then
        verify(view, never()).showSelectedChannelOnLastPosition(anyObject(), anyObject())
    }

    @Test
    fun `should dismiss view when on close button click`() {
        //when
        sharePresenter.onCloseButtonClick()
        //then
        verify(view).dismissView()
    }

    @Test
    fun `should show loading view when on send button click`() {
        //given
        whenever(channelsController.uploadFileToChannel(anyObject(), anyObject())).thenReturn(Completable.complete())
        //when
        sharePresenter.onSendButtonClick(byteArrayOf())
        //then
        verify(view).showLoadingView()
    }

    @Test
    fun `should hide loading view when uploading file successful`() {
        //given
        whenever(channelsController.uploadFileToChannel(anyObject(), anyObject())).thenReturn(Completable.complete())
        //when
        sharePresenter.onSendButtonClick(byteArrayOf())
        //then
        verify(view).hideLoadingView()
    }

    @Test
    fun `should hide loading view when uploading file failed`() {
        //given
        whenever(channelsController.uploadFileToChannel(anyObject(), anyObject())).thenReturn(Completable.error(Throwable()))
        //when
        sharePresenter.onSendButtonClick(byteArrayOf())
        //then
        verify(view).hideLoadingView()
    }

    @Test
    fun `should show confirmation view when uploading file successful`() {
        //given
        whenever(channelsController.uploadFileToChannel(anyObject(), anyObject())).thenReturn(Completable.complete())
        //when
        sharePresenter.onSendButtonClick(byteArrayOf())
        //then
        verify(view).showShareConfirmationDialog(anyObject())
    }

    @Test
    fun `should show error when uploading file failed`() {
        //given
        whenever(channelsController.uploadFileToChannel(anyObject(), anyObject())).thenReturn(Completable.error(Throwable()))
        //when
        sharePresenter.onSendButtonClick(byteArrayOf())
        //then
        verify(view).showError()
    }

    @After
    fun tearDown() {
        sharePresenter.detachView(false)
    }
}