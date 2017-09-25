package co.netguru.android.atstats.data.team.model

import com.google.gson.annotations.SerializedName


data class TeamResponse(@SerializedName("ok") val isSuccessful: Boolean,
                        val team: Team)