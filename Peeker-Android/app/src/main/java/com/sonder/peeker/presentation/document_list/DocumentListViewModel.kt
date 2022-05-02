package com.sonder.peeker.presentation.document_list

import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DocumentListViewModel @Inject constructor(
//    private val getDocumentListUseCase: GetDocumentUseCase
): ViewModel(){

    private val _state = mutableStateOf<DocumentListState>(DocumentListState())
    val state: State<DocumentListState> = _state

    init {
        // TODO: Get User Tags from DB
        Handler(Looper.getMainLooper()).postDelayed(
            {
                val tagsList = listOf("Tag 1", "Tag 2", "Tag 3", "Tag 4", "Tag 5")
                _state.value = DocumentListState(isLoadingTags = false, tags = tagsList)
            },
            5000 // value in milliseconds
        )
    }

    // TODO: Implement Methods.
    private fun getFavoriteDocuments(){}
    private fun getExpiredDocuments(){}
    private fun getAllDocuments(){}
    private fun getDocumentsByTag(tagIndex:Int){}

    // TODO: Change to List<Document>
    fun documents(): List<String> {
        return listOf("Document 1","Document 2","Document 3","Document 4")
        return when {
            _state.value.favoritesSelected -> _state.value.favoriteDocuments
            _state.value.expiredSelected -> _state.value.expiredDocuments
            _state.value.allSelected -> _state.value.allDocuments
            _state.value.selectedTagIndex != null -> _state.value.tagDocuments
            else -> emptyList()
        }
    }


    fun selectedTitle():String{
        return when{
            _state.value.favoritesSelected-> "Favorites"
            _state.value.expiredSelected-> "Expired"
            _state.value.allSelected-> "All Documents"
            _state.value.selectedTagIndex != null && state.value.selectedTagIndex!=null-> state.value.tags[state.value.selectedTagIndex!!]
            else -> "Documents"
        }
    }

     fun selectFavorites(){
        // TODO: Get favorite Documents
        _state.value = DocumentListState(isLoadingTags = false, tags = state.value.tags, favoritesSelected = true)
    }
     fun selectExpired(){
        // TODO: Get expired Documents
        _state.value = DocumentListState(isLoadingTags = false, tags = state.value.tags, favoritesSelected = false, expiredSelected = true)

    }
     fun selectAll(){
        // TODO: Get All Documents
        _state.value = DocumentListState(isLoadingTags = false, tags = state.value.tags, favoritesSelected = false, allSelected = true)
    }
     fun selectTag(index:Int){
        // TODO: Get tag Documents
        _state.value = DocumentListState(isLoadingTags = false, tags = state.value.tags, favoritesSelected = false, selectedTagIndex = index)

    }

}