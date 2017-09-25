package co.netguru.android.atstats.data.user

import co.netguru.android.atstats.RxSchedulersOverrideRule
import co.netguru.android.atstats.TestHelper.whenever
import co.netguru.android.atstats.data.direct.DirectChannelsDao
import co.netguru.android.atstats.data.direct.model.DirectChannelStatistics
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import co.netguru.android.atstats.TestHelper.anyObject
import co.netguru.android.atstats.data.user.model.*
import org.mockito.Mockito.*

@Suppress("IllegalIdentifier")
class UsersControllerTest {

    companion object {
        val USER_PROFILE = UserProfile("", "", "", "", "", "", "", "", "", "")
        val USER = User("1", "User", "User", "User", "User", false, USER_PROFILE)
        val USER_RESPONSE = UserResponse(true, USER)
        val USER_PROFILE_2 = UserProfile("", "", "", "", "", "", "", "", "", "")
        val USER_2 = User("2", "User2", "User2", "User", "User", false, USER_PROFILE_2)
        val USER_RESPONSE_2 = UserResponse(true, USER_2)
        val USER_PROFILE_3 = UserProfile("", "", "", "", "", "", "", "", "", "")
        val USER_3 = User("3", "User3", "User3", "User", "User", false, USER_PROFILE_3)
        val USER_RESPONSE_3 = UserResponse(true, USER_3)

        val DIRECT_CHANNEL_STATISTICS_1 = DirectChannelStatistics("ch1", "1", 1, 1, 0, false)
        val DIRECT_CHANNEL_STATISTICS_2 = DirectChannelStatistics("ch2", "2", 2, 2, 0, false)
        val DIRECT_CHANNEL_STATISTICS_3 = DirectChannelStatistics("ch3", "3", 3, 3, 0, false)
        private val USER_LIST = UserList(true, listOf(USER))
    }

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    val usersApi = mock<UsersApi>()

    val directChannelsDao = mock<DirectChannelsDao>()

    val usersDao = mock<UsersDao>()

    lateinit var usersController: UsersController

    @Before
    fun setUp() {
        usersController = UsersController(usersApi, usersDao, directChannelsDao)
    }

    @Test
    fun `should get user list from api when getting users list`() {
        //given
        whenever(usersApi.getUserList()).thenReturn(Single.just(USER_LIST))
        //when
        val testObserver = usersController.getUsersList().test()
        //then
        verify(usersApi).getUserList()
        testObserver.assertNoErrors()
    }

    @Test
    fun `should get a user info when getting user's info`() {
        // given
        whenever(usersApi.getUserInfo(anyString())).thenReturn(Single.just(USER_RESPONSE))
        // when
        val testObserver = usersController.getUserInfo("").test()
        // then
        testObserver
                .assertNoErrors()
                .assertValue(USER)
    }

    @Test
    fun `should return a list of direct channels statistics when getting all channels statistics`() {
        // given

        whenever(directChannelsDao.getAllDirectChannels()).thenReturn(Single.just(listOf(DIRECT_CHANNEL_STATISTICS_1, DIRECT_CHANNEL_STATISTICS_2, DIRECT_CHANNEL_STATISTICS_3)))
        // when
        val testObserver = usersController.getAllDirectChannelsStatistics().test()
        // then
        testObserver
                .assertNoErrors()
                .assertValue(listOf(DIRECT_CHANNEL_STATISTICS_1, DIRECT_CHANNEL_STATISTICS_2, DIRECT_CHANNEL_STATISTICS_3))
    }

    @Test
    fun `should return a list of user statistics when getting users statistics from a list of direct channels statistics`() {
        // given
        whenever(usersApi.getUserInfo("1")).thenReturn(Single.just(USER_RESPONSE))
        whenever(usersApi.getUserInfo("2")).thenReturn(Single.just(USER_RESPONSE_2))
        whenever(usersApi.getUserInfo("3")).thenReturn(Single.just(USER_RESPONSE_3))

        // when
        val testObserver = usersController.getAllUsersInfo(listOf(DIRECT_CHANNEL_STATISTICS_1,
                DIRECT_CHANNEL_STATISTICS_2, DIRECT_CHANNEL_STATISTICS_3)).test()

        // then
        testObserver
                .assertNoErrors()
                .assertValue {
                    (it[0].id == USER.id && it[1].id == USER_2.id && it[2].id == USER_3.id)
                }
    }

    @Test
    fun `should get an user and store it when getting user from the api and store`() {
        // given
        whenever(usersApi.getUserInfo("1")).thenReturn(Single.just(USER_RESPONSE))
        // when
        val testObserver = usersController.getUserAndStore("1").test()
        // then
        verify(usersDao).insertUser(anyObject())
        testObserver.assertNoErrors()
    }
}