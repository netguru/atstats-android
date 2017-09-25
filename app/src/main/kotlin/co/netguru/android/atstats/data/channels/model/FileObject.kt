package co.netguru.android.atstats.data.channels.model

import com.google.gson.annotations.SerializedName

data class FileObject(val id: String,
                      val name: String,
                      val title: String,
                      @SerializedName("filetype") val fileType: String)