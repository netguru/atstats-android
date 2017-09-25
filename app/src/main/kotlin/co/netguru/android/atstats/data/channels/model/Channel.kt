package co.netguru.android.atstats.data.channels.model

import com.google.gson.annotations.SerializedName


data class Channel(val id: String,
                   val name: String,
                   @SerializedName("creator") val creatorId: String,
                   @SerializedName("is_archived") val isArchived: Boolean,
                   @SerializedName("is_member") val isCurrentUserMember: Boolean,
                   @SerializedName("num_members") val membersNumber: Int)