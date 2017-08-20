package co.netguru.android.socialslack.data.theme

import android.content.SharedPreferences
import co.netguru.android.socialslack.app.LocalRepositoryModule
import co.netguru.android.socialslack.app.scope.UserScope
import co.netguru.android.socialslack.common.extensions.edit
import io.reactivex.Completable
import javax.inject.Inject
import javax.inject.Named


@UserScope
class ThemeOptionRepository @Inject constructor(@Named(LocalRepositoryModule.THEME_OPTION_SHARED_PREFERENCES_NAME)
                                                private val sharedPreferences: SharedPreferences) {

    companion object {
        const val THEME_OPTIONS = "theme_options"
    }

    fun saveThemeOption(themeOption: ThemeOption): Completable =
            Completable.fromAction({
                sharedPreferences.edit {
                    putString(THEME_OPTIONS, themeOption.name)
                }
            })

    fun getThemeOption() = ThemeOption.valueOf(
            sharedPreferences.getString(THEME_OPTIONS, ThemeOption.COLOURFUL.name))
}