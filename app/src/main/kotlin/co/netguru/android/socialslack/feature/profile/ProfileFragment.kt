package co.netguru.android.socialslack.feature.profile

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.app.App
import co.netguru.android.socialslack.common.extensions.inflate
import co.netguru.android.socialslack.data.theme.ThemeOption
import co.netguru.android.socialslack.feature.main.MainActivity
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : MvpFragment<ProfileContract.View, ProfileContract.Presenter>(), ProfileContract.View {

    companion object {
        fun newInstance() = ProfileFragment()
        const val COLOURFUL_THEME_POSITION = 0
        const val NETGURU_THEME_POSITION = 1
    }

    private val component by lazy { App.getUserComponent(context).plusProfileComponent() }

    private val themeController by lazy { component.getThemeController() }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_profile)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        themeToggleSwitch.setOnToggleSwitchChangeListener { _: Int, _: Boolean ->
            presenter.changeTheme()
        }
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

    override fun changeTheme() {
        activity.finish()
        MainActivity.startActivityOnProfile(activity)
    }

    override fun showChangeThemeError() {
        Snackbar.make(sendUsFeedBackButton, "Error while changing the theme", Snackbar.LENGTH_LONG).show()
    }

    private fun initView() {
        themeToggleSwitch.checkedTogglePosition = if (themeController.getThemeSync() == ThemeOption.COLOURFUL)
            COLOURFUL_THEME_POSITION else NETGURU_THEME_POSITION
    }
}