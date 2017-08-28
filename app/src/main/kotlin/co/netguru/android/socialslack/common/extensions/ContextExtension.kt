package co.netguru.android.socialslack.common.extensions

import android.content.Context
import android.content.res.TypedArray
import android.support.annotation.AttrRes
import android.support.annotation.DrawableRes
import android.util.TypedValue


fun Context.getAttributeColor(@AttrRes attrColor: Int): Int {
    var typedValue = TypedValue()
    this.theme.resolveAttribute(attrColor, typedValue, true)
    return typedValue.data
}

fun Context.getAttributeDrawable(@AttrRes attrDrawableRes: Int): Int {
    var typedValue = TypedValue()
    this.theme.resolveAttribute(attrDrawableRes, typedValue, true)
    return typedValue.resourceId
}