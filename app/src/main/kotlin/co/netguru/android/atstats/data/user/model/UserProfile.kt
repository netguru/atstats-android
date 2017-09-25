package co.netguru.android.atstats.data.user.model

import com.google.gson.annotations.SerializedName

data class UserProfile(@SerializedName("first_name") val firstName: String,
                       @SerializedName("last_name") val lastName: String,
                       @SerializedName("image_24") val image24: String,
                       @SerializedName("image_32") val image32: String,
                       @SerializedName("image_48") val image48: String,
                       @SerializedName("image_72") val image72: String,
                       @SerializedName("image_192") val image192: String,
                       @SerializedName("image_512") val image512: String,
                       @SerializedName("image_1024") val image1024: String,
                       @SerializedName("image_original") val imageOriginal: String)