package com.lduboscq.introkmm.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lduboscq.introkmm.Api
import com.lduboscq.introkmm.AppDatabase
import com.lduboscq.introkmm.Cache
import com.lduboscq.introkmm.DatabaseDriverFactory
import com.lduboscq.introkmm.RocketLaunch
import com.lduboscq.introkmm.RocketsService
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // evolution of this is to use koin for multiplatform dependency injection
        val databaseDriverFactory = DatabaseDriverFactory(this)
        val database = AppDatabase(databaseDriverFactory)
        val rocketsService = RocketsService(
            cache = Cache(database.rocketLaunchQueries),
            api = Api()
        )

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val scope = rememberCoroutineScope()
                    var rockets: List<RocketLaunch> by remember { mutableStateOf(emptyList()) }
                    var error: String? by remember { mutableStateOf("") }

                    LaunchedEffect(true) {
                        scope.launch {
                            rockets = try {
                                rocketsService.get()
                            } catch (e: Exception) {
                                error = e.localizedMessage
                                emptyList()
                            }
                        }
                    }

                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        item {
                            if (error?.isNotEmpty() == true) {
                                Text("Error found : $error", color = Color.Red)
                            }
                        }
                        items(rockets) {
                            Card(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 32.dp)
                            ) {
                                Column(Modifier.padding(8.dp)) {
                                    Text(it.missionName)
                                    Text(it.launchDateUTC)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
