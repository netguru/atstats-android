package co.netguru.android.socialslack.data.user


import co.netguru.android.socialslack.RxSchedulersOverrideRule
import org.junit.Rule
import co.netguru.android.socialslack.TestHelper.whenever
import co.netguru.android.socialslack.data.direct.DirectChannelsDao
import co.netguru.android.socialslack.data.direct.model.DirectChannelStatistics
import co.netguru.android.socialslack.data.user.model.User
import co.netguru.android.socialslack.data.user.model.UserProfile
import co.netguru.android.socialslack.data.user.model.UserResponse
import co.netguru.android.socialslack.data.user.model.UserStatistic
import com.nhaarman.mockito_kotlin.mock

import io.reactivex.Single
import io.reactivex.observers.TestObserver

import org.junit.Before
import org.junit.Test

import org.mockito.Mockito.anyString


@Suppress("IllegalIdentifier")
class UsersControllerTest {

    companion object {
        val USER_PROFILE = UserProfile("", "", "", "", "", "", "", "", "", "")
        val USER = User("1", "User", "User", "User", "User", USER_PROFILE)
        val USER_RESPONSE = UserResponse(true, USER)
        val USER_PROFILE_2 = UserProfile("", "", "", "", "", "", "", "", "", "")
        val USER_2 = User("2", "User2", "User2", "User", "User", USER_PROFILE_2)
        val USER_RESPONSE_2 = UserResponse(true, USER_2)
        val USER_PROFILE_3 = UserProfile("", "", "", "", "", "", "", "", "", "")
        val USER_3 = User("3", "User3", "User3", "User", "User", USER_PROFILE_3)
        val USER_RESPONSE_3 = UserResponse(true, USER_3)

        val DIRECT_CHANNEL_STATISTICS_1 = DirectChannelStatistics("ch1", "1", 1, 1)
        val DIRECT_CHANNEL_STATISTICS_2 = DirectChannelStatistics("ch2", "2", 2, 2)
        val DIRECT_CHANNEL_STATISTICS_3 = DirectChannelStatistics("ch3", "3", 3, 3)
    }

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    val usersApi = mock<UsersApi>()

    val directChannelsDao = mock<DirectChannelsDao>()

    lateinit var usersController: UsersController

    @Before
    fun setUp() {
        usersController = UsersController(usersApi, directChannelsDao)
    }

    @Test
    fun `should get a user info when getting user's info`() {
        // given
        val testObserver = TestObserver<User>()
        whenever(usersApi.getUserInfo(anyString())).thenReturn(Single.just(USER_RESPONSE))
        // when
        usersController.getUserInfo("").subscribe(testObserver)
        // then
        testObserver
                .assertNoErrors()
                .assertValue(USER)
    }

    @Test
    fun `should return a list of direct channels statistics when getting all channels statistics`() {
        // given
        val testObserver = TestObserver<List<DirectChannelStatistics>>()
        whenever(directChannelsDao.getAllDirectChannels()).thenReturn(Single.just(listOf(DIRECT_CHANNEL_STATISTICS_1, DIRECT_CHANNEL_STATISTICS_2, DIRECT_CHANNEL_STATISTICS_3)))
        // when
        usersController.getAllDirectChannelsStatistics().subscribe(testObserver)
        // then
        testObserver
                .assertNoErrors()
                .assertValue(listOf(DIRECT_CHANNEL_STATISTICS_1, DIRECT_CHANNEL_STATISTICS_2, DIRECT_CHANNEL_STATISTICS_3))
    }

    @Test
    fun `should return a list of user statistics when getting users statistics from a list of direct channels statistics`() {
        // given
        val testObserver = TestObserver<List<UserStatistic>>()
        whenever(usersApi.getUserInfo("1")).thenReturn(Single.just(USER_RESPONSE))
        whenever(usersApi.getUserInfo("2")).thenReturn(Single.just(USER_RESPONSE_2))
        whenever(usersApi.getUserInfo("3")).thenReturn(Single.just(USER_RESPONSE_3))

        // when
        usersController.getAllUsersInfo(listOf(DIRECT_CHANNEL_STATISTICS_1, DIRECT_CHANNEL_STATISTICS_2, DIRECT_CHANNEL_STATISTICS_3)).subscribe(testObserver)

        // then
        testObserver
                .assertNoErrors()
                .assertValue {
                    (it[0].id == USER.id && it[1].id == USER_2.id && it[2].id == USER_3.id)
                }
    }
}