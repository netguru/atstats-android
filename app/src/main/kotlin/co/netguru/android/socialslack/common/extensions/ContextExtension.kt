package co.netguru.android.socialslack.common.extensions

import android.content.Context
import android.support.annotation.AttrRes
import android.util.TypedValue


fun Context.getAttributeColor(@AttrRes attrColor: Int): Int {
    var typedValue = TypedValue()
    this.theme.resolveAttribute(attrColor, typedValue, true)
    return typedValue.data
}