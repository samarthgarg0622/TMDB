package com.example.tmdb.utils

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController

    fun NavController.navigateSafe(
        @IdRes action: Int? = null,
        args: Bundle? = null,
        isNavigateUP: Boolean = false
    ) = try {
        if (isNavigateUP) {
            navigateUp()
        } else if (action != null) {
            navigate(action, args)
        }
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }