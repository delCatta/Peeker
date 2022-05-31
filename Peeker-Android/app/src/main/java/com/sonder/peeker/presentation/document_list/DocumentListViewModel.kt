package com.sonder.peeker.presentation.document_list

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonder.peeker.core.Constants.UNEXPECTER_ERROR
import com.sonder.peeker.core.Resource
import com.sonder.peeker.di.SessionManager
import com.sonder.peeker.domain.model.Document
import com.sonder.peeker.domain.use_case.get_document.GetDocumentUseCase
import com.sonder.peeker.domain.use_case.get_documents.GetDocumentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DocumentListViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val getDocumentsUseCase: GetDocumentsUseCase
) : ViewModel() {

    private val _state = mutableStateOf<DocumentListState>(DocumentListState())
    val state: State<DocumentListState> = _state

    private val _tagState = mutableStateOf<DocumentTagState>(DocumentTagState())
    val tagState: State<DocumentTagState> = _tagState

    init {
        // TODO: Get User Tags from DB
        // Log.d("Session",sessionManager.fetchAuthToken().toString())
        sessionManager.tags = listOf("Tag 1", "Tag 2", "Tag 3", "Tag 4", "Tag 5")
        getFavoriteDocuments()
        _tagState.value = DocumentTagState(isLoadingTags = false)
    }

     fun getFavoriteDocuments() {
         // TODO Diego: Implementar la el useCase con la request. (Habla con bruno para saber la URL)
         getDocumentsUseCase.fromFavorites().onEach { result ->
             when (result) {
                 is Resource.Success -> {
                     sessionManager.favoriteDocuments = result.data ?: emptyList()
                     _state.value = DocumentListState(isLoading = false, favoritesSelected = true)

                 }
                 is Resource.Error -> {
                     _state.value = DocumentListState(
                         allSelected = true,
                         error = result.message ?: UNEXPECTER_ERROR
                     )
                 }
                 is Resource.Loading -> {
                     _state.value = DocumentListState(isLoading = true, favoritesSelected = true)
                 }
             }
         }.launchIn(viewModelScope)
    }
    fun getExpiredDocuments() {
        // TODO Diego: Implementar la el useCase con la request. (Habla con bruno para saber la URL)
        _state.value = DocumentListState(
            expiredSelected = true,
            error="No implementado aún.")
    }
    fun getAllDocuments() {
        getDocumentsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    sessionManager.allDocuments = result.data ?: emptyList()
                    _state.value = DocumentListState(isLoading = false, allSelected = true)

                }
                is Resource.Error -> {
                    _state.value = DocumentListState(
                        allSelected = true,
                        error = result.message ?: UNEXPECTER_ERROR
                    )
                }
                is Resource.Loading -> {
                    _state.value = DocumentListState(isLoading = true, allSelected = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getDocumentsByTag(tagIndex: Int) {
        // TODO Diego: Implementar la el useCase con la request. (Habla con bruno para saber la URL)
        _state.value = DocumentListState(
            selectedTagIndex = tagIndex,
            error="No implementado aún.")
    }

    fun documents(): List<Document> {
        if(_state.value.isLoading) return emptyList<Document>()
        return when {
            _state.value.favoritesSelected -> sessionManager.favoriteDocuments
            _state.value.expiredSelected -> sessionManager.expiredDocuments
            _state.value.allSelected -> sessionManager.allDocuments
            _state.value.selectedTagIndex != null -> sessionManager.tagDocuments
            else -> emptyList<Document>()
        }
    }


    fun selectedTitle(): String {
        return when {
            _state.value.favoritesSelected -> "Favorites"
            _state.value.expiredSelected -> "Expired"
            _state.value.allSelected -> "All Documents (${documents()?.size})"
            _state.value.selectedTagIndex != null && state.value.selectedTagIndex != null -> sessionManager.tags[state.value.selectedTagIndex!!]
            else -> "Documents"
        }
    }
    fun getTags():List<String>{
        return sessionManager.tags
    }

}