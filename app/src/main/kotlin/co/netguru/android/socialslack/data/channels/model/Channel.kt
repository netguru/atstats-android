package co.netguru.android.socialslack.data.channels.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import co.netguru.android.socialslack.data.share.Sharable
import com.google.gson.annotations.SerializedName
import paperparcel.PaperParcel


@PaperParcel @Entity
data class Channel(@PrimaryKey var id: String = "",
                   var name: String = "",
                   @SerializedName("creator") var creatorId: String = "",
                   @SerializedName("is_archived") var isArchived: Boolean = false,
                   @SerializedName("is_member") var isCurrentUserMember: Boolean = false,
                   @SerializedName("num_members") var membersNumber: Int = 0,
                   @Ignore var currentPositionInList: Int = 0): Parcelable, Sharable {

    companion object {
        @JvmField val CREATOR = PaperParcelChannel.CREATOR
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        PaperParcelChannel.writeToParcel(this, dest, flags)
    }

    override fun describeContents() = 0
}