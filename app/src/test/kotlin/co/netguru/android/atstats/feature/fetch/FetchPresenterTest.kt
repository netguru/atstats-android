package co.netguru.android.atstats.feature.fetch

import co.netguru.android.atstats.RxSchedulersOverrideRule
import co.netguru.android.atstats.TestHelper.anyObject
import co.netguru.android.atstats.TestHelper.whenever
import co.netguru.android.atstats.data.channels.ChannelsController
import co.netguru.android.atstats.data.channels.model.Channel
import co.netguru.android.atstats.data.channels.model.ChannelStatistics
import co.netguru.android.atstats.data.direct.DirectChannelsController
import co.netguru.android.atstats.data.direct.model.DirectChannel
import co.netguru.android.atstats.data.direct.model.DirectChannelStatistics
import co.netguru.android.atstats.data.session.SessionController
import co.netguru.android.atstats.data.session.model.UserSession
import co.netguru.android.atstats.data.team.TeamController
import co.netguru.android.atstats.data.theme.ThemeController
import co.netguru.android.atstats.data.user.UsersController
import co.netguru.android.atstats.data.user.model.User
import co.netguru.android.atstats.data.user.model.UserProfile
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*


@Suppress("IllegalIdentifier")
class FetchPresenterTest {

    companion object {
        private const val CREATED = 0L
        private const val USER_ID = "user"
        private const val CHANNEL_ID = "user"
        private const val EMPTY_STRING = ""

        private val USER_SESSION = UserSession(EMPTY_STRING, EMPTY_STRING)

        private val USER_PROFILE = UserProfile(USER_ID, USER_ID, USER_ID, USER_ID, USER_ID, USER_ID, USER_ID, USER_ID, USER_ID, USER_ID)
        private val USER = User(USER_ID, USER_ID, USER_ID, USER_ID, USER_ID, false, profile = USER_PROFILE)

        private val CHANNEL1 = Channel("1", "", "", false, true, 1)
        private val CHANNEL2 = Channel("2", "", "", false, true, 1)
        private val CHANNEL3 = Channel("3", "", "", false, false, 1)
        private val CHANNEL4 = Channel("4", "", "", false, false, 1)
        private val CHANNEL_ARCHIVED = Channel("5", "", "", true, true, 1)

        private val DIRECT_CHANNEL_1 = DirectChannel("1", USER_ID, CREATED, false)
        private val DIRECT_CHANNEL_2 = DirectChannel("2", USER_ID, CREATED, false)
        private val DIRECT_CHANNEL_3 = DirectChannel("3", USER_ID, CREATED, false)
        private val DIRECT_CHANNEL_DELETED = DirectChannel("4", USER_ID, CREATED, true)

        private val CHANNEL_STATISTICS1 = ChannelStatistics("1", "", 10, 5, 5, 5)
        private val DIRECT_CHANNEL_STATISTICS = DirectChannelStatistics(CHANNEL_ID, USER_ID, 1, 1, 0, false)
    }

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    val sessionController = mock<SessionController>()

    val usersController = mock<UsersController>()

    val channelsController = mock<ChannelsController>()

    val directChannelsController = mock<DirectChannelsController>()

    val teamController = mock<TeamController>()

    val themeController = mock<ThemeController>()

    val view = mock<FetchContract.View>()

    lateinit var fetchPresenter: FetchPresenter

    @Before
    fun setUp() {
        fetchPresenter = FetchPresenter(sessionController, usersController, channelsController, directChannelsController, teamController, themeController)
        whenever(sessionController.getUserSession()).thenReturn(Single.just(USER_SESSION))
        whenever(usersController.getUserAndStore(anyString())).thenReturn(Single.just(USER))
        whenever(teamController.fetchTeamInfo()).thenReturn(Completable.complete())
        whenever(channelsController.getChannelsList()).thenReturn(Single.just(listOf(CHANNEL1, CHANNEL2, CHANNEL3, CHANNEL4, CHANNEL_ARCHIVED)))
        whenever(channelsController.countChannelStatistics(anyString(), anyString(), anyString())).thenReturn(Single.just(CHANNEL_STATISTICS1))
        whenever(directChannelsController.getDirectChannelsList()).thenReturn(Single.just(listOf(DIRECT_CHANNEL_1, DIRECT_CHANNEL_2, DIRECT_CHANNEL_3, DIRECT_CHANNEL_DELETED)))
        whenever(directChannelsController.countDirectChannelStatistics(anyString(), anyString(), anyBoolean())).thenReturn(Single.just(DIRECT_CHANNEL_STATISTICS))
    }

    @Test
    fun `should show main activity when no error`() {
        // when
        fetchPresenter.attachView(view)

        // then
        verify(view).showMainActivity()
    }

    @Test
    fun `should show error when user session returns error`() {
        // given
        whenever(sessionController.getUserSession()).thenReturn(Single.error(Throwable()))

        // when
        fetchPresenter.attachView(view)

        // then
        verify(view).showError()
    }

    @Test
    fun `should show error when get user and store returns error`() {
        // given
        whenever(usersController.getUserAndStore(anyString())).thenReturn(Single.error(Throwable()))

        // when
        fetchPresenter.attachView(view)

        // then
        verify(view).showError()
    }

    @Test
    fun `should show error when fetch team info returns error`() {
        // given
        whenever(teamController.fetchTeamInfo()).thenReturn(Completable.error(Throwable()))

        // when
        fetchPresenter.attachView(view)

        // then
        verify(view).showError()
    }

    @Test
    fun `should show error when channel list returns error`() {
        // given
        whenever(channelsController.getChannelsList()).thenReturn(Single.error(Throwable()))

        // when
        fetchPresenter.attachView(view)

        // then
        verify(view).showError()
    }

    @Test
    fun `should show error when direct channel list returns error`() {
        // given
        whenever(directChannelsController.getDirectChannelsList()).thenReturn(Single.error(Throwable()))

        // when
        fetchPresenter.attachView(view)

        // then
        verify(view).showError()
    }

    @Test
    fun `should show error when count direct channel statistics returns error`() {
        // given
        whenever(directChannelsController.countDirectChannelStatistics(anyString(), anyString(), anyBoolean())).thenReturn(Single.error(Throwable()))

        // when
        fetchPresenter.attachView(view)

        // then
        verify(view).showError()
    }

    @Test
    fun `should skip channels in which current user is not a member`() {
        //given
        whenever(channelsController.getChannelsList()).thenReturn(Single.just(listOf(CHANNEL1, CHANNEL2, CHANNEL3, CHANNEL4)))
        //when
        fetchPresenter.attachView(view)
        //then
        //times 2, because list contains 4 channels, but in two of them current user is not a member
        verify(channelsController, times(2)).countChannelStatistics(anyObject(), anyObject(), anyObject())
    }

    @Test
    fun `should skip archived channels when counting channels statistics`() {
        //given
        whenever(channelsController.getChannelsList()).thenReturn(Single.just(listOf(CHANNEL1, CHANNEL2, CHANNEL_ARCHIVED)))
        //when
        fetchPresenter.attachView(view)
        //then
        //times 2, because list contains 3 channels, but one is archived
        verify(channelsController, times(2)).countChannelStatistics(anyObject(), anyObject(), anyObject())
    }

    @Test
    fun `should skip direct channels with deleted users when counting direct channels statistics`() {
        //given
        whenever(directChannelsController.getDirectChannelsList()).thenReturn(Single.just(listOf(DIRECT_CHANNEL_1, DIRECT_CHANNEL_2, DIRECT_CHANNEL_DELETED)))
        //when
        fetchPresenter.attachView(view)
        //then
        //times 2, because list contains 3 direct channels, but one in one user is deleted
        verify(directChannelsController, times(2)).countDirectChannelStatistics(anyObject(), anyObject(), anyBoolean())
    }

    @After
    fun tearDown() {
        fetchPresenter.detachView(false)
    }
}