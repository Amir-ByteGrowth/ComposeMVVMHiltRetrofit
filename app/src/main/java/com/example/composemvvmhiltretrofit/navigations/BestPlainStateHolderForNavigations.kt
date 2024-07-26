package com.example.composemvvmhiltretrofit.navigations

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.composemvvmhiltretrofit.ui.listscreen.SharedViewModel
import com.example.composemvvmhiltretrofit.ui.suggestions.SuggestionsScreen
import com.example.composemvvmhiltretrofit.ui.suggestions.SuggestionsViewModel

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


@Composable
fun MainNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    val sharedViewModel : SharedViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = "Home") {
        composable("Home"){
//            val suggestionsViewModel : SuggestionsViewModel = hiltViewModel()
            SuggestionsScreen(sharedViewModel){navigationType ->
                when(navigationType){
                    DashboardNavigation.NavigateToCarList -> {
                        navController.navigate("detail",)
                    }
                    DashboardNavigation.NavigateToSetting -> TODO()
                    DashboardNavigation.NavigateToCarDetail -> TODO()
                }
            }
        }

        composable("Detail"){

        }
    }
}

enum class DashboardNavigation{
    NavigateToCarList,
    NavigateToSetting,
    NavigateToCarDetail
}