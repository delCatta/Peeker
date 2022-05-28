package com.sonder.peeker.presentation.document_list

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.sonder.peeker.presentation.document_create.DocumentCreateState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DocumentCreateViewModel @Inject constructor(
//    private val getDocumentListUseCase: GetDocumentUseCase
): ViewModel(){

    private val _state = mutableStateOf<DocumentCreateState>(DocumentCreateState())
    val state: State<DocumentCreateState> = _state

    init {}

    fun createDocument(){
        clearError()
        Log.d("A", state.value.documentDateOfIssue.toString())
        Log.d("A", state.value.documentExpirationDate.toString())
        _state.value = state.value.copy(isLoading = true)
        // create document ...
        // result = ...
        // when (result) {
        // is Resource.Success -> {
        //     _state.value = state.value.copy(isLoading = false)
        //     return to main page
        //     }
        // is Resource.Error -> {
        //     _state.value = state.value.copy(error = result.message? : "Ha ocurrido un error inesperado.", isLoading = false)
        //     }
        // is Resource.Loading -> {
        //     ...
        //     }
        // }
        if(state.value.documentName.isNullOrBlank())
        // manejar success, error, loading
            _state.value = state.value.copy(error = "El nombre del documento no puede estar vacío", isLoading = false)
    }

    fun setDocumentName(value:String){
        _state.value = state.value.copy(documentName = value)
        clearError()
    }

    fun setDocumentDescription(value: String){
        _state.value = state.value.copy(documentDescription = value)
        clearError()
    }

    fun setDocumentDateOfIssue(value: String){
        _state.value = state.value.copy(documentDateOfIssue = value)
        clearError()
    }

    fun setDocumentExpirationDate(value: String){
        _state.value = state.value.copy(documentExpirationDate = value)
        clearError()
    }

    fun setDocumentTags(value: List<String>){
        _state.value = state.value.copy(documentTags = value)
        clearError()
    }

    fun setDocumentType(index: Int, value: String){
        _state.value = state.value.copy(documentType = index)
        clearError()
    }

    fun clearError(){
        _state.value = state.value.copy(error = null)
    }


}