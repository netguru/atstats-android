package co.netguru.android.atstats.data.theme

import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeController @Inject constructor(private val themeOptionRepository: ThemeOptionRepository) {

    fun getThemeOption(): Single<ThemeOption> = Single.just(themeOptionRepository.getThemeOption())

    fun saveThemeOption(themeOption: ThemeOption) = themeOptionRepository.saveThemeOption(themeOption)

    fun getThemeSync(): ThemeOption = themeOptionRepository.getThemeOption()
}