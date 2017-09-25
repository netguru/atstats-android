package co.netguru.android.atstats.common.extensions

import android.content.SharedPreferences

inline fun SharedPreferences.edit(action: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    editor.action()
    editor.apply()
}