package co.netguru.android.socialslack.feature.home.homedashboard

import co.netguru.android.socialslack.RxSchedulersOverrideRule
import co.netguru.android.socialslack.TestHelper.anyObject
import co.netguru.android.socialslack.TestHelper.whenever
import co.netguru.android.socialslack.data.channels.ChannelsDao
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import co.netguru.android.socialslack.data.direct.DirectChannelsDao
import co.netguru.android.socialslack.data.direct.model.DirectChannelStatistics
import co.netguru.android.socialslack.data.session.SessionController
import co.netguru.android.socialslack.data.session.model.UserSession
import co.netguru.android.socialslack.data.user.UsersController
import co.netguru.android.socialslack.data.user.model.User
import co.netguru.android.socialslack.data.user.model.UserProfile
import co.netguru.android.socialslack.feature.home.dashboard.HomeDashboardContract
import co.netguru.android.socialslack.feature.home.dashboard.HomeDashboardPresenter
import co.netguru.android.socialslack.feature.home.dashboard.model.ChannelsCount
import co.netguru.android.socialslack.feature.home.dashboard.model.DirectChannelsCount
import com.nhaarman.mockito_kotlin.capture
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.anyString

@Suppress("IllegalIdentifier")
class HomeDashboardPresenterTest {

    companion object {
        private const val EMPTY_STRING = ""

        private val USER_SESSION = UserSession(EMPTY_STRING, EMPTY_STRING)

        private val USER_PROFILE = UserProfile(EMPTY_STRING,
                EMPTY_STRING, EMPTY_STRING, EMPTY_STRING,
                EMPTY_STRING, EMPTY_STRING, EMPTY_STRING,
                EMPTY_STRING, EMPTY_STRING, EMPTY_STRING)
        private val USER = User(EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, false, USER_PROFILE)

        private val DIRECT_CHANNEL_STATISTICS_1 = DirectChannelStatistics(EMPTY_STRING, EMPTY_STRING, 1, 1, 0, false)
        private val DIRECT_CHANNEL_STATISTICS_2 = DirectChannelStatistics(EMPTY_STRING, EMPTY_STRING, 2, 2, 0, false)
        private val DIRECT_CHANNEL_STATISTICS_3 = DirectChannelStatistics(EMPTY_STRING, EMPTY_STRING, 3, 3, 0, false)

        private val CHANNEL_STATISTICS_1 = ChannelStatistics(EMPTY_STRING, EMPTY_STRING, 3, 1, 1, 1)
        private val CHANNEL_STATISTICS_2 = ChannelStatistics(EMPTY_STRING, EMPTY_STRING, 4, 1, 1, 1)
        private val CHANNEL_STATISTICS_3 = ChannelStatistics(EMPTY_STRING, EMPTY_STRING, 5, 1, 1, 1)

    }

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    val sessionController = mock<SessionController>()

    val usersController = mock<UsersController>()

    val channelsDao = mock<ChannelsDao>()

    val directChannelsDao = mock<DirectChannelsDao>()

    val view = mock<HomeDashboardContract.View>()

    lateinit var presenter: HomeDashboardPresenter

    @Before
    fun setup() {
        whenever(sessionController.getUserSession()).thenReturn(Single.just(USER_SESSION))
        whenever(usersController.getUserInfo(anyString())).thenReturn(Single.just(USER))
        whenever(channelsDao.getAllChannels()).thenReturn(
                Single.just(listOf(CHANNEL_STATISTICS_1, CHANNEL_STATISTICS_2, CHANNEL_STATISTICS_3))
        )
        whenever(directChannelsDao.getAllDirectChannels()).thenReturn(
                Single.just(listOf(DIRECT_CHANNEL_STATISTICS_1, DIRECT_CHANNEL_STATISTICS_2, DIRECT_CHANNEL_STATISTICS_3))
        )
        presenter = HomeDashboardPresenter(sessionController, usersController, channelsDao, directChannelsDao)
        presenter.attachView(view)
    }

    @Test
    fun `should return profile details when the view is attached`() {
        //then
        verify(view).showProfile(anyString(), anyString())
    }

    @Test
    fun `should return count for channels and directchannels when the view is attached`() {
        // then
        verify(view).showCounts(anyObject(), anyObject())
    }

    @Test
    fun `should show the correct count for channels and direct channels when the view is attached`() {
        // then
        var channelsCountArg: ArgumentCaptor<ChannelsCount> = ArgumentCaptor.forClass(ChannelsCount::class.java)
        var directChannelsCountArg: ArgumentCaptor<DirectChannelsCount> = ArgumentCaptor.forClass(DirectChannelsCount::class.java)
        verify(view).showCounts(capture(channelsCountArg), capture(directChannelsCountArg))
        channelsCountArg.apply {
            assertEquals(value.totalMessagesReceived, 9)
            assertEquals(value.totalMessageSent, 3)
            assertEquals(value.totalMentions, 3)
        }
        directChannelsCountArg.apply {
            assertEquals(value.receivedMessages, 6)
            assertEquals(value.sentMessages, 6)
        }
    }

    @Test
    fun `should return error when session controller returns error`() {
        //given
        whenever(sessionController.getUserSession()).thenReturn(Single.error(Throwable()))
        //when
        presenter.attachView(view)
        //then
        verify(view).showProfileError()
    }

    @Test
    fun `should return error when users controller returns error`() {
        //given
        whenever(usersController.getUserInfo(anyString())).thenReturn(Single.error(Throwable()))
        //when
        presenter.attachView(view)
        //then
        verify(view).showProfileError()
    }

    @Test
    fun `should show error message when channels count returns error`() {
        //given
        whenever(channelsDao.getAllChannels()).thenReturn(Single.error(Throwable()))
        //when
        presenter.attachView(view)
        //then
        verify(view).showCountError()
    }

    @Test
    fun `should show error message when direct channels count returns error`() {
        //given
        whenever(directChannelsDao.getAllDirectChannels()).thenReturn(Single.error(Throwable()))
        //when
        presenter.attachView(view)
        //then
        verify(view).showCountError()
    }

    @After
    fun tearDown() {
        presenter.detachView(false)
    }
}