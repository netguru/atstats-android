package co.netguru.android.atstats.common.util

import android.graphics.Bitmap
import android.view.View
import java.io.ByteArrayOutputStream

object ScreenShotUtils {

    private const val SCREEN_SHOT_QUALITY = 100

    fun takeScreenShotByteArray(view: View): ByteArray {
        with(view) {
            isDrawingCacheEnabled = true
            buildDrawingCache(true)

            val bitmap = Bitmap.createBitmap(view.drawingCache)

            isDrawingCacheEnabled = false

            return getByteArrayFromBitmap(bitmap)
        }
    }

    private fun getByteArrayFromBitmap(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, SCREEN_SHOT_QUALITY, stream)

        return stream.toByteArray()
    }
}