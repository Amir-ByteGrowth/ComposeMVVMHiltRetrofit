package com.example.composemvvmhiltretrofit.navigations

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Stable
class NiaAppState(
    val navController: NavHostController,
    val windowSizeClass: WindowSizeClass,
) {

    // UI logic
    val shouldShowBottomBar: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact ||
                windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact

    // UI logic
    val shouldShowNavRail: Boolean
        get() = !shouldShowBottomBar

    // UI State
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    // UI logic
    fun navigate(destination: NiaNavigationDestination, route: String? = null) { /* ... */
    }

    /* ... */

    sealed interface NiaNavigationDestination {

    }
}