package co.netguru.android.socialslack.data.session.model

import com.google.gson.annotations.SerializedName

data class TokenCheck(@SerializedName("ok") val isTokenValid: Boolean)