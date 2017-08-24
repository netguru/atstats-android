package co.netguru.android.socialslack.data.team.model

import com.google.gson.annotations.SerializedName


data class TeamResponse(@SerializedName("ok") val isSuccessful: Boolean,
                        val team: Team)