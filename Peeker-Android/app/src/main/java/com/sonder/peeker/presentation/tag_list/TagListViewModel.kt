package com.sonder.peeker.presentation.tag_list
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonder.peeker.core.Constants
import com.sonder.peeker.core.Constants.UNEXPECTER_ERROR
import com.sonder.peeker.core.Resource
import com.sonder.peeker.di.SessionManager
import com.sonder.peeker.domain.model.Tag
import com.sonder.peeker.domain.use_case.get_tags.GetTagsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TagListViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val getTagsUseCase: GetTagsUseCase
) : ViewModel() {

    private val _state = mutableStateOf<TagListState>(TagListState())
    val state: State<TagListState> = _state

    init {
        getAllTags()
    }

    fun getAllTags() {
        getTagsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    sessionManager.tags = result.data ?: emptyList()
                    _state.value = TagListState(isLoading = false)

                }
                is Resource.Error -> {
                    _state.value = TagListState(
                        error = result.message ?: UNEXPECTER_ERROR
                    )
                }
                is Resource.Loading -> {
                    _state.value = TagListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun deleteTag(tagId: String){
        getTagsUseCase.deleteTag(tagId)
            .onEach {
                    result ->
                when (result) {
                    is Resource.Success -> {
                        getAllTags()
                    }
                    is Resource.Loading -> {
                        _state.value = state.value.copy(isLoading = true)
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            error = result.message ?: Constants.UNEXPECTER_ERROR
                        )
                    }
                }
            }.launchIn(viewModelScope)

    }


    fun tags(): List<Tag> {
        if(_state.value.isLoading) return emptyList<Tag>()
        return sessionManager.tags
    }

}
data class TagListState(
    val isLoading: Boolean = false,
    val error: String = ""
)