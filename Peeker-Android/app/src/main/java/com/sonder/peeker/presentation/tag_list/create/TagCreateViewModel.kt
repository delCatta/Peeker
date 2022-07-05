package com.sonder.peeker.presentation.tag_list.create

import com.sonder.peeker.presentation.document_update.DocumentUpdateState

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.sonder.peeker.core.Constants
import com.sonder.peeker.core.Resource
import com.sonder.peeker.data.remote.dto.DocumentCreateDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import androidx.lifecycle.viewModelScope
import com.sonder.peeker.core.Constants.DOCUMENT_TYPES
import com.sonder.peeker.di.SessionManager
import com.sonder.peeker.domain.model.Document
import com.sonder.peeker.domain.use_case.create_document.CreateDocumentUseCase
import com.sonder.peeker.domain.use_case.get_document.GetDocumentUseCase
import com.sonder.peeker.domain.use_case.get_tags.GetTagsUseCase
import com.sonder.peeker.domain.use_case.update_document.UpdateDocumentUseCase

@HiltViewModel
class TagCreateViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val getTagsUseCase: GetTagsUseCase
) : ViewModel() {

    private val _state = mutableStateOf<TagCreateState>(TagCreateState())
    val state: State<TagCreateState> = _state


    fun createTag(on_success: () -> (Unit)) {
        clearError()
        if (_state.value.tagName.isNullOrEmpty()) {
            _state.value = state.value.copy(
                isLoading = false,
                error = "Debe ingresar un nombre"
            )
            return
        }
        getTagsUseCase.createTag(_state.value.tagName!!).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    Log.d("Success", result.data.toString())
                    _state.value = state.value.copy(isLoading = false)
                    on_success();
                }
                is Resource.Loading -> {
                    _state.value = state.value.copy(isLoading = true)
                }
                is Resource.Error -> {
                    Log.d("Error", result.message.toString())
                    _state.value = state.value.copy(
                        isLoading = false,
                        error = result.message ?: Constants.UNEXPECTER_ERROR
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun setTagName(value: String) {
        _state.value = state.value.copy(tagName = value)
        clearError()
    }

    fun clearError() {
        _state.value = state.value.copy(error = null)
    }

/*    fun deleteDocument(onSuccess: ()->( Unit ), onError: ()->( Unit )){
        Log.d("Delete", "Running delete document. ${state.value.document?.id}")
        val documentId = state.value.document?.id
        updateDocumentUseCase.deleteDocument(documentId)
            .onEach {
                    result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = state.value.copy(isLoading = false)
                        onSuccess();
                    }
                    is Resource.Loading -> {
                        _state.value = state.value.copy(isLoading = true)
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            error = result.message ?: Constants.UNEXPECTER_ERROR
                        )
                        onError();
                    }
                }
            }.launchIn(viewModelScope)

    }*/

}

data class TagCreateState(
    val tagName: String? = null,
    val error: String? = null,
    val isLoading: Boolean = false
)
