package co.netguru.android.socialslack.feature.share

import co.netguru.android.socialslack.app.scope.FragmentScope
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import javax.inject.Inject

@FragmentScope
class SharePresenter @Inject constructor() : MvpNullObjectBasePresenter<ShareContract.View>(),
        ShareContract.Presenter {

    override fun onSendButtonClick() {
        view.showShareConfirmationDialog()
    }
}