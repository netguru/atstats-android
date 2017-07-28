package co.netguru.android.socialslack.data.channels.model

import com.google.gson.annotations.SerializedName

data class FileUploadResponse(@SerializedName("ok") val isSuccessful: Boolean,
                              val error: String,
                              val file: FileObject)