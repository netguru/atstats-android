package co.netguru.android.socialslack.feature.profile

import co.netguru.android.socialslack.app.scope.FragmentScope
import co.netguru.android.socialslack.common.util.RxTransformers
import co.netguru.android.socialslack.data.theme.ThemeController
import co.netguru.android.socialslack.data.theme.ThemeOption
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

@FragmentScope
class ProfilePresenter @Inject constructor(private val themeController: ThemeController) :
        MvpNullObjectBasePresenter<ProfileContract.View>(), ProfileContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        compositeDisposable.clear()
    }

    override fun changeTheme() {
        compositeDisposable+=themeController.getThemeOption()
                .flatMapCompletable {
                    // TODO Remove the variable
                    var theme = if(it==ThemeOption.COLOURFUL) ThemeOption.NETGURU else ThemeOption.COLOURFUL
                    Timber.d("Change of theme to ${theme.name}")
                    themeController.saveThemeOption(theme)
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

}