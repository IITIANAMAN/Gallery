package ar.com.amanmeena.gallery


import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun MyAppNavigation(modifier: Modifier = Modifier,viewModel: MainViewModel) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "main") {
        composable("folder/{folderName}") { backStackEntry ->
            val folder = backStackEntry.arguments?.getString("folderName") ?: "All"
            MediaFileShow(modifier = modifier, viewModel = viewModel, navController = navController, folderName = folder)
        }

        composable("imagePage/{index}") { backStackEntry ->
            val index = backStackEntry.arguments?.getString("index")?.toIntOrNull() ?: 0
            ImagePage(
                viewModel = viewModel, startIndex = index,

            )
        }

        composable ("main"){
            MainScreen(modifier,  navController = navController,viewModel = viewModel)
        }

    }
}