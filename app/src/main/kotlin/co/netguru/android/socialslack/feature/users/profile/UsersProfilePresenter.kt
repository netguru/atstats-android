package co.netguru.android.socialslack.feature.users.profile

import co.netguru.android.socialslack.app.scope.FragmentScope
import co.netguru.android.socialslack.data.user.model.UserStatistic
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@FragmentScope
class UsersProfilePresenter @Inject constructor() : MvpNullObjectBasePresenter<UsersProfileContract.View>(),
        UsersProfileContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        compositeDisposable.clear()
    }

    override fun prepareView(userStatisticsList: List<UserStatistic>, currentUserPosition: Int) {
        view.initView(userStatisticsList)
        view.scrollToUserPosition(currentUserPosition)
    }
}