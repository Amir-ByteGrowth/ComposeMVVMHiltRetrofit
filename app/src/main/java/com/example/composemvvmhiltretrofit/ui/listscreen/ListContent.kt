package com.example.composemvvmhiltretrofit.ui.listscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ListContent(modifier: Modifier = Modifier, viewModel: ListScreenViewModel = hiltViewModel()) {

    val uiState = viewModel.uiState.collectAsState()

    Content(uiState = uiState.value) {
        viewModel.searchData(it)
    }
}


@Composable
fun Content(
    modifier: Modifier = Modifier,
    uiState: ListScreenUiState = ListScreenUiState.Loading,
    searchData: (searchData: String) -> Unit
) {
    var searchText by remember { mutableStateOf("") }
    var lazyScrollState = rememberLazyListState()
    var showError by remember { mutableStateOf(false) }
    Scaffold(bottomBar = {
        Row(
            modifier = modifier
                .fillMaxWidth()

                .background(color = Color.LightGray), verticalAlignment = Alignment.CenterVertically
        ) {
//            OutlinedTextField(
//                value = searchText,
//                onValueChange = { searchText = it },
//                modifier = modifier
//                    .fillMaxWidth(0.85f)
//                    .padding(10.dp)
//            )
            SearchBar(searchText, onSearchTextChanged = {
                searchText = it
                showError = false
            }, {
                searchText = ""
                searchData.invoke("")
            }, showError)
            IconButton(onClick = {
                if (searchText.isBlank()) {
                    showError = true
                } else {
                    searchData.invoke(searchText)
                }
            }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
        }
    }

    ) {
        Box(
            modifier
                .fillMaxSize()
                .padding(it)
                .padding(10.dp)
                .background(Color.White), contentAlignment = Alignment.Center
        ) {

            when (uiState) {
                is ListScreenUiState.Initial -> {

                }

                is ListScreenUiState.Loading -> {
                    CircularProgressIndicator()
                }

                is ListScreenUiState.Success -> {
                    LazyColumn(state = lazyScrollState) {
                        items(uiState.list) { item ->
                            ListItemUi(motivationDataItem = item)
                        }
                    }
                }

                is ListScreenUiState.Error -> {
                    Text(text = uiState.error)
                }


            }
        }
    }
}


@Composable
fun SearchBar(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onClearClick: () -> Unit,
    showError: Boolean
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BasicTextField(
                value = searchText,
                onValueChange = onSearchTextChanged,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(Color.LightGray)
                            .padding(8.dp)
                    ) {
                        innerTextField()
                        if (searchText.isEmpty()) {
                            Text(
                                text = "Search...",
                                color = Color.Gray,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            )

            if (searchText.isNotEmpty()) {
                IconButton(onClick = onClearClick) {
                    Icon(Icons.Default.Close, contentDescription = "Clear search")
                }
            }
        }

        if (showError) {
            Text(
                text = "Search query cannot be empty",
                color = Color.Red,
                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
            )
        }
    }

}

