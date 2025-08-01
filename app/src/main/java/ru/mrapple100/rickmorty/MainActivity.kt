package ru.mrapple100.rickmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*

import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.mrapple100.rickmorty.ui.pages.characterdetails.CharacterDetailsPage
import ru.mrapple100.rickmorty.ui.pages.characterdetails.CharacterDetailsViewModel
import ru.mrapple100.rickmorty.ui.pages.characterdetails.CharacterSideEffect
import ru.mrapple100.rickmorty.ui.pages.characterlist.CharacterListPage
import ru.mrapple100.rickmorty.ui.pages.characterlist.CharacterListSideEffect
import ru.mrapple100.rickmorty.ui.pages.characterlist.CharacterListViewModel
import ru.mrapple100.rickmorty.ui.screen.Screen
import ru.mrapple100.rickmorty.ui.theme.RickAndMortyTheme

@AndroidEntryPoint()
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyTheme {
                window.statusBarColor = MaterialTheme.colorScheme.primaryContainer.toArgb()
                Box(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = Screen.List.route) {
                        addLibrary(navController = navController)
                    }
                }
            }
        }
    }
}



private fun NavGraphBuilder.addLibrary(navController: NavController) {
    composable(route = Screen.List.route) {
        val viewModel = hiltViewModel<CharacterListViewModel>()
        val state by viewModel.collectAsState()
        viewModel.collectSideEffect {
            when (it) {
                is CharacterListSideEffect.ShowDetails -> {

                    navController.navigate(route = Screen.Details.createRoute(it.id))
                }

                else -> {}
            }
        }
        CharacterListPage(
            state = state,
            onShowDetail = { id -> viewModel.showDetails(id) },
            onSearchCharacter = { text -> viewModel.searchCharacter(text) },
            onScrollDown = {  -> viewModel.scrollDown()},
            onRefresh = {-> viewModel.refreshCharacterPage()}
        )
    }
    composable(route = Screen.Details.route,
        arguments = listOf(
            navArgument(name = "characterId"){
                type = NavType.IntType
                defaultValue = 0
            }
        )
    ) {
        val viewModel = hiltViewModel<CharacterDetailsViewModel>()
        val state by viewModel.collectAsState()
        viewModel.collectSideEffect {
            when (it) {
                is CharacterSideEffect.backToStack -> {
                    navController.popBackStack()
                }

                else -> {}
            }
        }
//        val idCh = Screen.Details.getArgumentId(navController.currentBackStackEntry!!)
//        viewModel.fetchCharacterById(idCh)

        CharacterDetailsPage(
            state = state,
            onBackNavigate = { viewModel.backNavigate() }
        )
    }
}