package com.sonder.peeker.presentation.document_list

import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.sonder.peeker.presentation.document_create.DocumentCreateState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DocumentCreateViewModel @Inject constructor(
//    private val getDocumentListUseCase: GetDocumentUseCase
): ViewModel(){

    private val _state = mutableStateOf<DocumentCreateState>(DocumentCreateState())
    val state: State<DocumentCreateState> = _state

    init {}

    fun createDocument(){

    }
    fun setDocumentName(value:String){
        _state.value = state.value.copy(documentName = value)
    }

}