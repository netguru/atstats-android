package co.netguru.android.socialslack.feature.splash

import co.netguru.android.socialslack.app.scope.ActivityScope
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import javax.inject.Inject

@ActivityScope
class SplashPresenter @Inject constructor() : MvpNullObjectBasePresenter<SplashContract.View>(),
        SplashContract.Presenter {

    override fun attachView(view: SplashContract.View) {
        super.attachView(view)
    }
}