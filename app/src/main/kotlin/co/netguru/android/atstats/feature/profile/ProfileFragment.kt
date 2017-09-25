package co.netguru.android.atstats.feature.profile

import android.content.ActivityNotFoundException
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import co.netguru.android.atstats.R
import co.netguru.android.atstats.app.App
import co.netguru.android.atstats.common.extensions.inflate
import co.netguru.android.atstats.common.extensions.startActivity
import co.netguru.android.atstats.data.team.model.Team
import co.netguru.android.atstats.data.theme.ThemeOption
import co.netguru.android.atstats.data.user.profile.model.Presence
import co.netguru.android.atstats.data.user.profile.model.UserWithPresence
import co.netguru.android.atstats.feature.login.LoginActivity
import co.netguru.android.atstats.feature.main.MainActivity
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import android.content.Intent
import android.net.Uri
import co.netguru.android.atstats.app.GlideApp
import co.netguru.android.atstats.common.extensions.getAttributeDrawable
import co.netguru.android.atstats.data.shared.RandomMessageProvider
import timber.log.Timber

class ProfileFragment : MvpFragment<ProfileContract.View, ProfileContract.Presenter>(), ProfileContract.View {

    companion object {
        fun newInstance() = ProfileFragment()
        const val COLOURFUL_THEME_POSITION = 0
        const val NETGURU_THEME_POSITION = 1
        const val MAIL_TO = "mailto:"
    }

    private val component by lazy { App.getUserComponent(context).plusProfileComponent() }

    private val themeController by lazy { component.getThemeController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_profile)
    }

    override fun createPresenter(): ProfileContract.Presenter = component.getPresenter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        themeToggleSwitch.setOnToggleSwitchChangeListener { _, _ ->
            presenter.changeTheme()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

    override fun showUserAndTeamInfo(user: UserWithPresence, team: Team) {
        with(user) {
            firstLastNameTextView.text = getString(R.string.first_name_last_name, firstName, lastName)
            firstLastNameTextView.isActivated = Presence.ACTIVE == user.presence
            nameTextView.text = getString(R.string.username, username)
        }
        teamNameTextView.text = team.name
        teamPageTextView.text = getString(R.string.slack_domain, team.domain)

        GlideApp.with(this)
                .load(user.avatarUrl)
                .placeholder(context.getAttributeDrawable(R.attr.userPlaceholderDrawable))
                .error(context.getAttributeDrawable(R.attr.userPlaceholderDrawable))
                .centerCrop()
                .into(profilePictureImageView)
    }

    override fun changeTheme() {
        activity.finish()
        MainActivity.startActivityWithRequest(activity, MainActivity.REQUEST_SHOW_PROFILE_VIEW)
    }

    override fun hideChangeThemeButton() {
        colourSchemeTextView.visibility = View.GONE
        themeToggleSwitch.visibility = View.GONE
    }

    override fun showChangeThemeButton() {
        colourSchemeTextView.visibility = View.VISIBLE
        themeToggleSwitch.visibility = View.VISIBLE
    }

    override fun logOut() {
        activity.finish()
        activity.startActivity<LoginActivity>()
    }

    override fun showChangeThemeError() {
        val errorMsg = RandomMessageProvider.getRandomMessageFromArray(resources.getStringArray(R.array.errorMessages))
        Snackbar.make(sendUsFeedBackButton, errorMsg, Snackbar.LENGTH_LONG).show()
    }

    override fun showInfoError() {
        val errorMsg = RandomMessageProvider.getRandomMessageFromArray(resources.getStringArray(R.array.errorMessages))
        Snackbar.make(sendUsFeedBackButton, errorMsg, Snackbar.LENGTH_SHORT).show()
    }

    override fun showLogoutError() {
        val errorMsg = RandomMessageProvider.getRandomMessageFromArray(resources.getStringArray(R.array.errorMessages))
        Snackbar.make(sendUsFeedBackButton, errorMsg, Snackbar.LENGTH_SHORT).show()
        logOut()
    }

    private fun sendFeedback() {
        val intent = Intent(Intent.ACTION_SENDTO)
                .setData(Uri.parse(MAIL_TO))
                .putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.feedback_email)))
                .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.feedback_email_subject))

        try {
            activity.startActivity(Intent.createChooser(intent, getString(R.string.send_us_feedback)))
        } catch (ex: ActivityNotFoundException) {
            Timber.e(ex, "There is no email client")
            Snackbar.make(sendUsFeedBackButton, R.string.error_send_feedback, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun initView() {
        themeToggleSwitch.checkedTogglePosition = if (themeController.getThemeSync() == ThemeOption.COLOURFUL)
            COLOURFUL_THEME_POSITION else NETGURU_THEME_POSITION

        logOutTextView.setOnClickListener {
            presenter.logOut()
        }
        sendUsFeedBackButton.setOnClickListener {
            sendFeedback()
        }
    }
}