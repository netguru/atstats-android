package co.netguru.android.socialslack.common.extensions

import android.content.Context
import android.support.annotation.AttrRes
import android.util.TypedValue

fun Context.getAttributeColor(@AttrRes attrColor: Int): Int {
    val typedValue = TypedValue()
    this.theme.resolveAttribute(attrColor, typedValue, true)
    return typedValue.data
}

fun Context.getAttributeDrawable(@AttrRes attrDrawableRes: Int): Int {
    val typedValue = TypedValue()
    this.theme.resolveAttribute(attrDrawableRes, typedValue, true)
    return typedValue.resourceId
}