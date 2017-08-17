package co.netguru.android.socialslack.data.theme

import co.netguru.android.socialslack.app.scope.UserScope
import io.reactivex.Single
import javax.inject.Inject

@UserScope
class ThemeController @Inject constructor(private val themeOptionRepository: ThemeOptionRepository) {

    fun getThemeOption() = Single.just(themeOptionRepository.getThemeOption())

    fun saveThemeOption(themeOption: ThemeOption) = themeOptionRepository.saveThemeOption(themeOption)
}