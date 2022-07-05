package com.sonder.peeker.presentation.document_create

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.sonder.peeker.core.Constants
import com.sonder.peeker.core.Resource
import com.sonder.peeker.domain.use_case.create_document.CreateDocumentUseCase
import com.sonder.peeker.presentation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.File
import javax.inject.Inject


@HiltViewModel
class DocumentCreateViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val createDocumentUseCase: CreateDocumentUseCase,
) : ViewModel() {
    private val _state = mutableStateOf<DocumentCreateState>(DocumentCreateState())
    val state: State<DocumentCreateState> = _state

    fun createEmptyDocument(navController: NavController, onSuccess: () -> (Unit)) {
        clearErrors()
        createDocumentUseCase.fromEmptyDocument().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    Log.d("Success", result.data.toString())
                    onSuccess()
                    navController.navigate(Screen.UpdateDocumentScreen.route + "/${result.data!!.id}")
                    _state.value = state.value.copy(isLoading = false)

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

    fun clearErrors() {
        _state.value = state.value.copy(error = "")
    }

    fun uploadFile(fileUri: Uri, navController: NavController) {
        clearErrors()
        createDocumentUseCase.fromDocumentFile(fileUri).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    Log.d("Success", result.data.toString())
                     navController.navigate(Screen.UpdateDocumentScreen.route + "/${result.data!!.id}")
                    Log.d("URL",result.data!!.url.toString())
                    _state.value = state.value.copy(isLoading = false)

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
}

data class DocumentCreateState(
    val isLoading: Boolean = false,
    val error: String = "",
)
