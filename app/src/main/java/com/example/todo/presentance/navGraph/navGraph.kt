package com.example.todo.presentance.navGraph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todo.presentance.screen.homeScreen.screen.HomeScreen
import com.example.todo.presentance.screen.homeScreen.screen.MainScreen
import com.example.todo.presentance.screen.homeScreen.viewModel.HomeViewModel
import com.example.todo.presentance.screen.signIn.screen.SignInScreen
import com.example.todo.presentance.screen.signIn.viewModel.SignInViewModel
import com.example.todo.presentance.screen.signUp.screen.SignUpScreen
import com.example.todo.presentance.screen.signUp.viewModel.SignUpViewModel
import com.example.todo.presentance.screen.writeScreen.screen.WriteScreen
import com.example.todo.presentance.screen.writeScreen.viewModel.WriteViewModel

@Composable
fun NavGraph(
    navHostController: NavHostController,
    startDestination: NavRoot
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {

        composable<NavRoot.SignInRoot>(){

            val viewModel :SignInViewModel= hiltViewModel()
            val state by viewModel.signInState.collectAsStateWithLifecycle()
            SignInScreen(
                signInState = state,
                signInEvent = viewModel::onEvent,
                onNavHome = {
                    navHostController.navigate(NavRoot.HomeScreenRoot)
                },
                onNavSignUp = {
                    navHostController.navigate(NavRoot.SignUpRoot)
                }
            )
        }

        composable<NavRoot.SignUpRoot>(){

            val viewModel : SignUpViewModel = hiltViewModel()
            val state by viewModel.signUpState.collectAsStateWithLifecycle()


            SignUpScreen(
                signUpState = state,
                signUpEvent = viewModel::onEvent,
                onNavHome = {
                    navHostController.navigate(NavRoot.HomeScreenRoot)
                }
            )

        }


        composable<NavRoot.HomeScreenRoot>(){

            val viewModel : HomeViewModel = hiltViewModel()
            val state by viewModel.homeState.collectAsStateWithLifecycle()

            MainScreen(
                onSignIn = {
                    navHostController.navigate(NavRoot.SignInRoot)
                    navHostController.navigateUp()
                },
                onWriteScreen = {
                    navHostController.navigate(NavRoot.WriteScreenRoot())
                },
                onWriteScreenId = {
                    navHostController.navigate(NavRoot.WriteScreenRoot(it))
                },
                homeState = state,
                homeEvent = viewModel::onEvent

            )

        }

        composable<NavRoot.WriteScreenRoot>(){
            val viewModel : WriteViewModel = hiltViewModel()
            val state by viewModel.writeState.collectAsStateWithLifecycle()
            WriteScreen(
                writeState = state,
                writeEvent = viewModel::onEvent,
                onBack = {navHostController.popBackStack()}
            )
        }

    }
}