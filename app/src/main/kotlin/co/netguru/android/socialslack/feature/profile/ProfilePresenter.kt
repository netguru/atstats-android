package co.netguru.android.socialslack.feature.profile

import co.netguru.android.socialslack.app.scope.FragmentScope
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import javax.inject.Inject

@FragmentScope
class ProfilePresenter @Inject constructor() : MvpNullObjectBasePresenter<ProfileContract.View>(), ProfileContract.Presenter {

}