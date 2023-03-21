package com.kashif.searchcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kashif.searchcompose.ui.theme.SearchComposeTheme
/**
 * Created by Mohammad Kashif Ansari on 21,March,2023
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchComposeTheme {
                val viewModel = viewModel<MainViewModel>()
                val serachText by viewModel.searchText.collectAsState()
                val person by viewModel.persons.collectAsState()
                val isSearching by viewModel.isSearching.collectAsState()
               Column(
                   modifier = Modifier
                       .fillMaxSize()
                       .padding(16.dp)
               ) {
                    TextField(value = serachText, onValueChange = viewModel::onSerachTextChange,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = "Search")})
                   Spacer(modifier = Modifier.height(16.dp))
                   if(isSearching)
                   {
                        Box(modifier = Modifier.fillMaxSize()){
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                   }else
                   {
                       LazyColumn(modifier = Modifier
                           .fillMaxWidth()
                           .weight(1f)){
                           items(person){person->
                               Text(text = "${person.firstname} ${person.lastName}",
                                   modifier = Modifier
                                       .fillMaxWidth()
                                       .padding(vertical = 16.dp))
                           }
                       }
                   }
                  
               }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SearchComposeTheme {
        Greeting("Android")
    }
}