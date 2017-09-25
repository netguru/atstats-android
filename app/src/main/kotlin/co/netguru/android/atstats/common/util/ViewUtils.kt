package co.netguru.android.atstats.common.util

import android.graphics.drawable.BitmapDrawable
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.view.Gravity
import android.widget.ImageView
import co.netguru.android.atstats.common.util.UnitUtils.dp

object ViewUtils {
    val ROUNDED_IMAGE_VIEW_CORNERS = 20.dp()

    fun ImageView.roundImageView() {
        val drawable = RoundedBitmapDrawableFactory.create(this.context.resources, (this.drawable as? BitmapDrawable)?.bitmap)
        drawable.cornerRadius = ROUNDED_IMAGE_VIEW_CORNERS
        drawable.gravity = Gravity.FILL
        this.setImageDrawable(drawable)
    }
}