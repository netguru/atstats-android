package co.netguru.android.socialslack.feature.users.profile

import co.netguru.android.socialslack.app.scope.FragmentScope
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import javax.inject.Inject

@FragmentScope
class UsersProfilePresenter @Inject constructor() : MvpNullObjectBasePresenter<UsersProfileContract.View>(),
        UsersProfileContract.Presenter {
}