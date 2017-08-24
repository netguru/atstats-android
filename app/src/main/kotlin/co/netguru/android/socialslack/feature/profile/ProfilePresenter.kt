package co.netguru.android.socialslack.feature.profile

import co.netguru.android.socialslack.app.scope.FragmentScope
import co.netguru.android.socialslack.common.util.RxTransformers
import co.netguru.android.socialslack.data.session.SessionController
import co.netguru.android.socialslack.data.team.TeamDao
import co.netguru.android.socialslack.data.theme.ThemeController
import co.netguru.android.socialslack.data.theme.ThemeOption
import co.netguru.android.socialslack.data.user.UsersDao
import co.netguru.android.socialslack.data.user.profile.UsersProfileController
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.zipWith
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@FragmentScope
class ProfilePresenter @Inject constructor(private val themeController: ThemeController,
                                           private val sessionController: SessionController,
                                           private val usersDao: UsersDao,
                                           private val usersProfileController: UsersProfileController,
                                           private val teamDao: TeamDao) :
        MvpNullObjectBasePresenter<ProfileContract.View>(), ProfileContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun attachView(view: ProfileContract.View) {
        super.attachView(view)
        compositeDisposable += sessionController.getUserSession()
                .flatMap {
                    usersDao.getUser(it.userId)
                            .flatMap { usersProfileController.getUserWithPresence(it) }
                            .subscribeOn(Schedulers.io())
                }
                .zipWith(teamDao.getTeam().map { it.first() })
                { userWithPresence, team -> Pair(userWithPresence, team) }
                .compose(RxTransformers.applySingleIoSchedulers())
                .subscribeBy(
                        onSuccess = { view.showUserAndTeamInfo(it.first, it.second) },
                        onError = {
                            Timber.e(it)
                            view.showInfoError()
                        }
                )
    }

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        compositeDisposable.clear()
    }

    override fun changeTheme() {
        compositeDisposable += themeController.getThemeOption()
                .flatMapCompletable {
                    themeController.saveThemeOption(if (it == ThemeOption.COLOURFUL)
                        ThemeOption.NETGURU else ThemeOption.COLOURFUL)
                }
                .compose(RxTransformers.applyCompletableIoSchedulers())
                .subscribeBy(
                        onComplete = view::changeTheme,
                        onError = {
                            Timber.e(it)
                            view.showChangeThemeError()
                        }
                )
    }

    override fun logOut() {

    }
}