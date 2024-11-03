package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.todo.presentance.navGraph.NavGraph
import com.example.todo.presentance.navGraph.NavRoot
import com.example.todo.ui.theme.TODOTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TODOTheme {
                val navHostController = rememberNavController()

                NavGraph(
                    navHostController = navHostController,
                    startDestination = startDestination()
                )
            }
        }
    }

}

private fun startDestination(): NavRoot {

    val user = Firebase.auth.currentUser

    return if (user != null) {
        NavRoot.HomeScreenRoot
    } else {
        NavRoot.SignInRoot
    }

}

