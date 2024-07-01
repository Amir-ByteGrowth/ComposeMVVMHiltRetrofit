package com.example.composemvvmhiltretrofit.ui.suggestions

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composemvvmhiltretrofit.data.models.Suggestion


@Composable
fun SuggestionsScreen(suggestionsViewModel: SuggestionsViewModel = hiltViewModel()) {
    val suggestionsListState = suggestionsViewModel.uiState.collectAsState()
    Column {
        TopBarContent {
            Log.d("RecordInserted", "true")
            suggestionsViewModel.insertData(Suggestion(handle = it))
        }
        Spacer(Modifier.height(10.dp))
        SuggestionsList(suggestionsListState.value)
    }
}

@Composable
fun TopBarContent(onAddSuggestionClick: (String) -> Unit) {
    var suggestion by remember {
        mutableStateOf("")
    }
    Row(
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = suggestion,
            onValueChange = { suggestion = it },
            modifier = Modifier.weight(1f), placeholder = { Text(text = "Enter suggestion") }
        )
        Spacer(modifier = Modifier.width(10.dp))
        Button(onClick = {
            onAddSuggestionClick.invoke(suggestion)
            suggestion = ""
        }) {
            Text(text = "Add")
        }
    }
}

@Composable
fun SuggestionsList(uiState: UiState<List<Suggestion>> = UiState.Loading) {

    when (uiState) {
        is UiState.Loading -> {
            CircularProgressIndicator()
        }

        is UiState.Error -> {
            Text(text = uiState.error)
        }

        is UiState.Initial -> {}
        is UiState.Success -> {
            LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                items(uiState.data) { item ->
                    Text(text = item.handle)

                }
            }
        }
    }


}