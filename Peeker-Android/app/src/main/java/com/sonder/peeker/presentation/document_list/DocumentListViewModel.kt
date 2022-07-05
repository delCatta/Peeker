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
import com.sonder.peeker.domain.model.Tag
import com.sonder.peeker.domain.use_case.get_document.GetDocumentUseCase
import com.sonder.peeker.domain.use_case.get_documents.GetDocumentsUseCase
import com.sonder.peeker.domain.use_case.get_tags.GetTagsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DocumentListViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val getDocumentsUseCase: GetDocumentsUseCase,
    private val getTagsUseCase: GetTagsUseCase
) : ViewModel() {

    private val _state = mutableStateOf<DocumentListState>(DocumentListState())
    val state: State<DocumentListState> = _state

    private val _tagState = mutableStateOf<DocumentTagState>(DocumentTagState())
    val tagState: State<DocumentTagState> = _tagState

    init {
        // TODO: Get User Tags from DB
        // Log.d("Session",sessionManager.fetchAuthToken().toString())

        loadTags()
        getFavoriteDocuments()

    }

    fun loadTags() {
        getTagsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    sessionManager.tags = result.data ?: emptyList()
                    _tagState.value = DocumentTagState(isLoadingTags = false)

                }
                is Resource.Error -> {
                    _tagState.value = DocumentTagState(
                        isLoadingTags = false
                    )
                }
                is Resource.Loading -> {
                    _tagState.value = DocumentTagState(isLoadingTags = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getFavoriteDocuments() {
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
        getDocumentsUseCase.fromExpired().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    sessionManager.expiredDocuments = result.data ?: emptyList()
                    _state.value = DocumentListState(isLoading = false, expiredSelected = true)

                }
                is Resource.Error -> {
                    _state.value = DocumentListState(
                        allSelected = true,
                        error = result.message ?: UNEXPECTER_ERROR
                    )
                }
                is Resource.Loading -> {
                    _state.value = DocumentListState(isLoading = true, expiredSelected = true)
                }
            }
        }.launchIn(viewModelScope)
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

        getDocumentsUseCase.fromTag(sessionManager.tags[tagIndex].id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    sessionManager.tagDocuments = result.data ?: emptyList()
                    Log.d("Documents",result.data.toString())
                    _state.value = DocumentListState(isLoading = false, selectedTagIndex = tagIndex)

                }
                is Resource.Error -> {
                    _state.value = DocumentListState(
                        selectedTagIndex = tagIndex,
                        error = result.message ?: UNEXPECTER_ERROR
                    )
                }
                is Resource.Loading -> {
                    _state.value = DocumentListState(isLoading = true, selectedTagIndex = tagIndex)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun documents(): List<Document> {
        if (_state.value.isLoading) return emptyList<Document>()
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
            _state.value.selectedTagIndex != null && state.value.selectedTagIndex != null -> sessionManager.tags[state.value.selectedTagIndex!!].toString()
            else -> "Documents"
        }
    }

    fun getTags(): List<Tag> {
        return sessionManager.tags
    }

}