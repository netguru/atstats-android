package co.netguru.android.socialslack.data.user.profile

import co.netguru.android.socialslack.RxSchedulersOverrideRule

import co.netguru.android.socialslack.TestHelper.whenever
import co.netguru.android.socialslack.data.user.UsersApi
import co.netguru.android.socialslack.data.user.model.UserStatistic
import co.netguru.android.socialslack.data.user.profile.model.Presence
import co.netguru.android.socialslack.data.user.profile.model.UserPresence
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@Suppress("IllegalIdentifier")
class UsersProfileControllerTest {

    companion object {
        private val USER_STATS = UserStatistic("", "", "", "", "",
                20, 20, 20, 20, "")
    }


    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    val usersApi = mock<UsersApi>()

    lateinit var usersProfileController: UsersProfileController

    @Before
    fun setUp() {
        usersProfileController = UsersProfileController(usersApi)

        whenever(usersApi.getUserPresence(anyString())).thenReturn(Single.just(UserPresence(true, Presence.AWAY)))
    }

    @Test
    fun `should get user presence from api when getting user presence`() {
        //when
        usersProfileController.getUserWithPresence(USER_STATS)
        //then
        verify(usersApi).getUserPresence(anyString())
    }
}