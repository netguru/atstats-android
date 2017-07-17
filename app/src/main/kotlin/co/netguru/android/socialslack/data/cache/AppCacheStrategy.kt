package co.netguru.android.socialslack.data.cache

object AppCacheStrategy {

    const val HEADER_CACHE_CONTROL = "Cache-Control"
    const val CACHE_CONTROL_NO_CACHE = "no-cache"
    const val CACHE_CONTROL_MAX_AGE = "max-age="
    const val CACHE_TIME_SECONDS = 60 * 60 * 24

    fun cache(): String {
        return CACHE_CONTROL_MAX_AGE + CACHE_TIME_SECONDS
    }

    fun noCache(): String {
        return CACHE_CONTROL_NO_CACHE
    }
}
