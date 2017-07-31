package co.netguru.android.socialslack.data.channels.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Channel(@PrimaryKey var id: String = "",
                   var name: String = "",
                   @SerializedName("creator") var creatorId: String = "",
                   @SerializedName("is_archived") var isArchived: Boolean = false,
                   @SerializedName("is_member") var isCurrentUserMember: Boolean = false,
                   @SerializedName("num_members") var membersNumber: Int = 0,
                   @Ignore var currentPositionInList: Int = 0)