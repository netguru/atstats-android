package co.netguru.android.socialslack.feature.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.app.App
import co.netguru.android.socialslack.common.extensions.inflate
import com.hannesdorfmann.mosby3.mvp.MvpFragment


class ProfileFragment : MvpFragment<ProfileContract.View, ProfileContract.Presenter>(), ProfileContract.View {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private val component by lazy { App.getUserComponent(context).plusProfileComponent() }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_profile)
    }

    override fun createPresenter(): ProfileContract.Presenter = component.getPresenter()

    //TODO 09.08.2017 Workaround for clearing menu options
    override fun onAttach(context: Context) {
        super.onAttach(context)
        setHasOptionsMenu(true)
    }

    //TODO 09.08.2017 Workaround for clearing menu options
    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }
}