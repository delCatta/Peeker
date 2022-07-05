package com.sonder.peeker.presentation.document_list.document_screen

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.sonder.peeker.core.Constants
import com.sonder.peeker.core.Constants.PARAM_DOCUMENT_ID
import com.sonder.peeker.core.Resource
import com.sonder.peeker.data.remote.dto.DocumentDto
import com.sonder.peeker.data.remote.dto.TagDto
import com.sonder.peeker.data.remote.dto.toTag
import com.sonder.peeker.di.SessionManager
import com.sonder.peeker.domain.model.Document
import com.sonder.peeker.domain.model.Tag
import com.sonder.peeker.domain.use_case.get_document.GetDocumentUseCase
import com.sonder.peeker.domain.use_case.update_document.UpdateDocumentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DocumentViewModel @Inject constructor(
    private val getDocumentUseCase: GetDocumentUseCase,
    private val updateDocumentUseCase: UpdateDocumentUseCase,
    private val sessionManager: SessionManager,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf<DocumentState>(DocumentState())
    val state: State<DocumentState> = _state


    init {
        savedStateHandle.get<String>(PARAM_DOCUMENT_ID)
            ?.let { documentId -> getDocument(documentId) }
    }

    fun getDocument(id: String) {
        Log.d("Get Document", id)
        getDocumentUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val document = result.data
                    _state.value = state.value.copy(document = document, isLoading = false)
                }
                is Resource.Error -> {
                    Log.d("Get Document", "Error ${result.message ?: ""}")
                    _state.value = state.value.copy(
                        isLoading = false,
                        error = result.message ?: Constants.UNEXPECTER_ERROR
                    )
                }
                is Resource.Loading -> {
                    clearError()
                    _state.value = state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)

    }

    fun clearError() {
        _state.value = state.value.copy(error = null)
    }

    fun toggleFavorite(document: Document) {
        val document = state.value.document ?: document
        updateDocumentUseCase(
            document.id,
            mapOf("favorite" to (!document.favorite).toString())
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val new_document = result.data
                    _state.value = state.value.copy(document = new_document, isLoading = false)
                }
                is Resource.Error -> {
                    Log.d("Toggle Favorite Error", "Error ${result.message ?: ""}")
                    _state.value = state.value.copy(
                        isLoading = false,
                        error = result.message ?: Constants.UNEXPECTER_ERROR
                    )
                }
                is Resource.Loading -> {
                    clearError()
                    _state.value = state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getTags(): List<Tag> {
        return sessionManager.tags
    }
    fun tagInDcoument(tagId:String): Boolean{
        return _state.value.document?.tags?.map{ it.id }?.contains(tagId)?:false
    }

    fun toggleTagToDocument(navController: NavController, tagId: String) {
        val document = state.value.document
        if (document != null) {
            updateDocumentUseCase.setTagToDocument(
                document.id,
                tagId
            ).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        getDocument(document.id)
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            error = result.message ?: Constants.UNEXPECTER_ERROR
                        )
                    }
                    is Resource.Loading -> {
                        clearError()
                        _state.value = state.value.copy(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}


data class DocumentState(
    val document: Document? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)