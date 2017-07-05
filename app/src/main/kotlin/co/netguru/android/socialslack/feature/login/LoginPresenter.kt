package co.netguru.android.socialslack.feature.login

import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import javax.inject.Inject

class LoginPresenter @Inject constructor(): MvpNullObjectBasePresenter<LoginContract.View>(),
        LoginContract.Presenter {


    override fun loginButtonClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
