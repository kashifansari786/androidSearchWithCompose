package com.kashif.searchcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*

/**
 * Created by Mohammad Kashif Ansari on 21,March,2023
 */
class MainViewModel:ViewModel() {

    private val _searchtext= MutableStateFlow("")
    val searchText=_searchtext.asStateFlow()

    private val _isSearching= MutableStateFlow(false)
    val isSearching =_isSearching.asStateFlow()

    private val _person= MutableStateFlow(allPerson)
    val persons=searchText.combine(_person){
        text,person->
        if(text.isBlank()){
            person
        }else{
            person.filter {
                it.doesMatchSearchQuery(text)
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _person.value
    )
    fun onSerachTextChange(text:String){
        _searchtext.value=text
    }
}

data class Person(
    val firstname:String,
    val lastName:String
){
    fun doesMatchSearchQuery(query:String):Boolean{
        val matchingCombination= listOf(
            "$firstname$lastName",
            "$firstname $lastName",
            "${firstname.first()} ${lastName.first()}"
        )
        return matchingCombination.any{
            it.contains(query, ignoreCase = true)
        }
    }
}

private val allPerson= listOf(
    Person(firstname = "Kashif",
    lastName = "Ansari"),
    Person(firstname = "Salman",
        lastName = "Ansari"),
    Person(firstname = "Kashif",
        lastName = "Khan"),
    Person(firstname = "Salman",
        lastName = "Khan"),
    Person(firstname = "Kashif",
        lastName = "Salman"),
)