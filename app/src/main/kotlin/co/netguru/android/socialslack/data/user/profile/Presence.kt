package co.netguru.android.socialslack.data.user.profile

enum class Presence constructor(val value: String) {

    ACTIVE("active"),
    AWAY("away");

    companion object {

        fun getEnumForValue(value: String): Presence {
            Presence.values().forEach {
                if (it.value == value) {
                    return it
                }
            }

            throw IllegalArgumentException("There is no enum for $value")
        }
    }
}