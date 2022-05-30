package com.sonder.peeker.presentation.document_create

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.sonder.peeker.core.Constants
import com.sonder.peeker.core.Resource
import com.sonder.peeker.data.remote.dto.DocumentCreateDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import androidx.lifecycle.viewModelScope
import com.sonder.peeker.di.SessionManager
import com.sonder.peeker.domain.use_case.create_document.CreateDocumentUseCase

@HiltViewModel
class DocumentCreateViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val createDocumentUseCase: CreateDocumentUseCase
) : ViewModel() {

    private val _state = mutableStateOf<DocumentCreateState>(DocumentCreateState())
    val state: State<DocumentCreateState> = _state

    init {}

    fun createDocument(on_success: () -> (Unit)) {
        clearError()
        if (!verifyFields())
        return
        createDocumentUseCase(
            DocumentCreateDto(
                description = state.value.documentDescription ?: "",
                document_type = state.value.documentType?: 0,
                emission_date = state.value.documentDateOfIssue ?: "",
                expiration_date = state.value.documentExpirationDate ?: "",
                name = state.value.documentName ?: "",
                //url = state.value.documentUrl?: "",
                user_id = sessionManager.getUser()?.id ?: ""
            )
        ).onEach { result ->
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

    fun setDocumentName(value: String) {
        _state.value = state.value.copy(documentName = value)
        clearError()
    }

    fun setDocumentDescription(value: String) {
        _state.value = state.value.copy(documentDescription = value)
        clearError()
    }

    fun setDocumentDateOfIssue(value: String) {
        _state.value = state.value.copy(documentDateOfIssue = value)
        clearError()
    }

    fun setDocumentExpirationDate(value: String) {
        _state.value = state.value.copy(documentExpirationDate = value)
        clearError()
    }

    fun setDocumentTags(value: List<String>) {
        _state.value = state.value.copy(documentTags = value)
        clearError()
    }

    fun setDocumentType(index: Int, value: String) {
        _state.value = state.value.copy(documentType = index)
        clearError()
    }

    fun clearError() {
        _state.value = state.value.copy(error = null)
    }

    fun verifyFields(): Boolean {
        if (state.value.documentName.isNullOrBlank()) {
            _state.value = state.value.copy(
                error = "El nombre del documento no puede estar vacío",
                isLoading = false
            )
            return false
        }
        return true
    }
}