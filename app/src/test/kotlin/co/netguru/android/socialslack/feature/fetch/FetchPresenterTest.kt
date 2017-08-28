package co.netguru.android.socialslack.feature.fetch

import co.netguru.android.socialslack.RxSchedulersOverrideRule
import co.netguru.android.socialslack.TestHelper.whenever
import co.netguru.android.socialslack.data.channels.ChannelsController
import co.netguru.android.socialslack.data.channels.model.Channel
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import co.netguru.android.socialslack.data.direct.DirectChannelsController
import co.netguru.android.socialslack.data.direct.model.DirectChannel
import co.netguru.android.socialslack.data.direct.model.DirectChannelStatistics
import co.netguru.android.socialslack.data.session.SessionController
import co.netguru.android.socialslack.data.session.model.UserSession
import co.netguru.android.socialslack.data.team.TeamController
import co.netguru.android.socialslack.data.theme.ThemeController
import co.netguru.android.socialslack.data.user.UsersController
import co.netguru.android.socialslack.data.user.model.User
import co.netguru.android.socialslack.data.user.model.UserProfile
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.anyString
import org.mockito.Mockito.verify


@Suppress("IllegalIdentifier")
class FetchPresenterTest {

    companion object {
        private const val CREATED = 0L
        private const val USER_ID = "user"
        private const val CHANNEL_ID = "user"
        private const val LATEST = "100"
        private const val EMPTY_STRING = ""

        private val USER_SESSION = UserSession(EMPTY_STRING, EMPTY_STRING)

        private val USER_PROFILE = UserProfile(USER_ID, USER_ID, USER_ID, USER_ID, USER_ID, USER_ID, USER_ID, USER_ID, USER_ID, USER_ID)
        private val USER = User(USER_ID, USER_ID, USER_ID, USER_ID, USER_ID, profile = USER_PROFILE)

        private val CHANNEL1 = Channel("1", "", "", false, false, 1)
        private val CHANNEL2 = Channel("2", "", "", false, false, 1)
        private val CHANNEL3 = Channel("3", "", "", false, false, 1)
        private val CHANNEL4 = Channel("4", "", "", false, false, 1)
        private val CHANNEL5 = Channel("5", "", "", false, false, 1)

        private val DIRECT_CHANNEL_1 = DirectChannel("1", USER_ID, CREATED)
        private val DIRECT_CHANNEL_2 = DirectChannel("2", USER_ID, CREATED)
        private val DIRECT_CHANNEL_3 = DirectChannel("3", USER_ID, CREATED)
        private val DIRECT_CHANNEL_4 = DirectChannel("4", USER_ID, CREATED)

        private val CHANNEL_STATISTICS1 = ChannelStatistics("1", "", 10, 5, 5, 5)
        private val DIRECT_CHANNEL_STATISTICS = DirectChannelStatistics(CHANNEL_ID, USER_ID, 1, 1)
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
        whenever(channelsController.getChannelsList()).thenReturn(Single.just(listOf(CHANNEL1, CHANNEL2, CHANNEL3, CHANNEL4, CHANNEL5)))
        whenever(channelsController.countChannelStatistics(anyString(), anyString(), anyString())).thenReturn(Single.just(CHANNEL_STATISTICS1))
        whenever(directChannelsController.getDirectChannelsList()).thenReturn(Single.just(listOf(DIRECT_CHANNEL_1, DIRECT_CHANNEL_2, DIRECT_CHANNEL_3, DIRECT_CHANNEL_4)))
        whenever(directChannelsController.countDirectChannelStatistics(anyString(), anyString())).thenReturn(Single.just(DIRECT_CHANNEL_STATISTICS))
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
        verify(view).showErrorMessage()
    }

    @Test
    fun `should show error when get user and store returns error`() {
        // given
        whenever(usersController.getUserAndStore(anyString())).thenReturn(Single.error(Throwable()))

        // when
        fetchPresenter.attachView(view)

        // then
        verify(view).showErrorMessage()
    }

    @Test
    fun `should show error when fetch team info returns error`() {
        // given
        whenever(teamController.fetchTeamInfo()).thenReturn(Completable.error(Throwable()))

        // when
        fetchPresenter.attachView(view)

        // then
        verify(view).showErrorMessage()
    }

    @Test
    fun `should show error when channel list returns error`() {
        // given
        whenever(channelsController.getChannelsList()).thenReturn(Single.error(Throwable()))

        // when
        fetchPresenter.attachView(view)

        // then
        verify(view).showErrorMessage()
    }

    @Test
    fun `should show error when direct channel list returns error`() {
        // given
        whenever(directChannelsController.getDirectChannelsList()).thenReturn(Single.error(Throwable()))

        // when
        fetchPresenter.attachView(view)

        // then
        verify(view).showErrorMessage()
    }

    @Test
    fun `should show error when count direct channel statistics returns error`() {
        // given
        whenever(directChannelsController.countDirectChannelStatistics(anyString(), anyString())).thenReturn(Single.error(Throwable()))

        // when
        fetchPresenter.attachView(view)

        // then
        verify(view).showErrorMessage()
    }

    @After
    fun tearDown() {
        fetchPresenter.detachView(false)
    }
}