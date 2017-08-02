package co.netguru.android.socialslack.feature.users.profile

import co.netguru.android.socialslack.app.scope.FragmentScope
import co.netguru.android.socialslack.common.util.RxTransformers
import co.netguru.android.socialslack.data.user.model.UserStatistic
import co.netguru.android.socialslack.data.user.profile.UsersProfileController
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@FragmentScope
class UsersProfilePresenter @Inject constructor(private val usersProfileController: UsersProfileController)
    : MvpNullObjectBasePresenter<UsersProfileContract.View>(), UsersProfileContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        compositeDisposable.clear()
    }

    override fun prepareView(userStatisticsList: List<UserStatistic>, currentUserPosition: Int) {
        view.showLoadingView()
        compositeDisposable += Flowable.fromIterable(userStatisticsList)
                .flatMap {
                    usersProfileController.getUserWithPresence(it)
                            .subscribeOn(Schedulers.io())
                }
                .toList()
                .compose(RxTransformers.applySingleIoSchedulers())
                .subscribeBy(
                        onSuccess = {
                            view.initView(it)
                            view.hideLoadingView()
                            view.scrollToUserPosition(currentUserPosition)
                        },
                        onError = {
                            Timber.e(it, "Error while getting user presence")
                            view.hideLoadingView()
                        }
                )
    }
}