package com.sonder.peeker.presentation.document_update

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
import com.sonder.peeker.domain.use_case.update_document.UpdateDocumentUseCase

@HiltViewModel
class DocumentUpdateViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val getDocumentUseCase: GetDocumentUseCase,
    private val updateDocumentUseCase: UpdateDocumentUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf<DocumentUpdateState>(DocumentUpdateState())
    val state: State<DocumentUpdateState> = _state

    init {
        savedStateHandle.get<String>(Constants.PARAM_DOCUMENT_ID)
            ?.let { documentId -> getDocument(documentId) }
    }

    fun getDocument(id: String) {
        getDocumentUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val document = result.data!!
                    _state.value = state.value.copy(
                        document = document,
                        documentName = document.name,
                        documentDescription = document.description,
                        documentType = document.document_type,
                        documentDateOfIssue = document.emission_date,
                        documentExpirationDate = document.expiration_date,
                        isLoading = false
                    )
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

    fun updateDocument(on_success: () -> (Unit)) {
        clearError()
        val document = state.value.document
        if (document == null) {
            _state.value = state.value.copy(
                isLoading = false,
                error = "No se ha encontrado el documento."
            )
            return
        }
        if (!verifyFields())
            return
        // TODO: "" to document.documentTags,
        updateDocumentUseCase(
            document.id,
            mapOf(
                "name" to state.value.documentName.toString(),
                "description" to state.value.documentDescription.toString(),
                "document_type" to state.value.documentType.toString(),
                "emission_date" to state.value.documentDateOfIssue.toString(),
                "expiration_date" to state.value.documentExpirationDate.toString()
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
    fun deleteDocument(onSuccess: ()->( Unit ), onError: ()->( Unit )){
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

    }
}