package com.example.composemvvmhiltretrofit

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.composemvvmhiltretrofit.data.remote.ApiService
import com.example.composemvvmhiltretrofit.ui.listscreen.ListContent
import com.example.composemvvmhiltretrofit.ui.suggestions.SuggestionsScreen
import com.example.composemvvmhiltretrofit.ui.theme.ComposeMVVMHiltRetrofitTheme
import com.example.composemvvmhiltretrofit.ui.userlistscreen.UserListScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        GlobalScope.launch {
//           Log.d("response",apiService.getMotivationList().body().toString())
//        }

        setContent {
            ComposeMVVMHiltRetrofitTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Greeting("Android")
//                    ListContent()
//                    SuggestionsScreen()
                    UserListScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeMVVMHiltRetrofitTheme {
        Greeting("Android")
    }
}