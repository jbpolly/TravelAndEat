package com.mysticraccoon.travelandeat.core.utils

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions

fun NavController.safeNavigate(@IdRes resId: Int, args: Bundle? = null, navOptions: NavOptions? = null) {
    try {
        navigate(resId, args, navOptions)
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
    }
}

fun NavController.safeNavigate(directions: NavDirections, navOptions: NavOptions? = null) {
    try {
        navigate(directions, navOptions)
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
    }
}