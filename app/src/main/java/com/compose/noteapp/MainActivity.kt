package com.compose.noteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.compose.noteapp.home.HomeScreen
import com.compose.noteapp.home.NoteScreen
import com.compose.noteapp.ui.theme.NoteAppTheme
import com.compose.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val homeViewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()
            NoteAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavHost(navController = navController,
                        startDestination = Screen.HOME.name, builder = {
                            composable(Screen.HOME.name) {
                                HomeScreen(
                                    homeViewModel = homeViewModel,
                                    onClickNote = {
                                        navController.navigate(Screen.NOTE.name)
                                    },
                                    onClickAddNote = {
                                        navController.navigate(Screen.NOTE.name)
                                    }
                                )
                            }

                            composable(Screen.NOTE.name) {

                               NoteScreen(viewModel = homeViewModel,
                               onClickClose = {
                                   navController.popBackStack()
                               })
                            }
                        })
                }
            }
        }
    }
}

enum class Screen {
    HOME, NOTE
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NoteAppTheme {
        Greeting("Android")
    }
}