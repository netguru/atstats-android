package co.netguru.android.socialslack.data.channels.model

import android.os.Parcel
import android.os.Parcelable
import co.netguru.android.socialslack.data.share.Sharable
import com.google.gson.annotations.SerializedName
import paperparcel.PaperParcel

@PaperParcel
data class Channel(val id: String,
                   val name: String,
                   @SerializedName("creator") val creatorId: String,
                   @SerializedName("is_archived") val isArchived: Boolean,
                   @SerializedName("is_member") val isCurrentUserMember: Boolean,
                   @SerializedName("num_members") val membersNumber: Int,
                   var currentPositionInList: Int) : Parcelable, Sharable {

    companion object {
        @JvmField val CREATOR = PaperParcelChannel.CREATOR
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        PaperParcelChannel.writeToParcel(this, dest, flags)
    }

    override fun describeContents() = 0
}