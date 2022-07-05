package com.sonder.peeker.domain.use_case.get_tags

import android.util.Log
import com.sonder.peeker.core.Resource
import com.sonder.peeker.data.remote.dto.TagCreateDto
import com.sonder.peeker.data.remote.dto.toDocument
import com.sonder.peeker.data.remote.dto.toTag
import com.sonder.peeker.domain.model.Document
import com.sonder.peeker.domain.model.Tag
import com.sonder.peeker.domain.repository.PeekerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class GetTagsUseCase @Inject constructor(
    private val repository: PeekerRepository
) {
    operator fun invoke(): Flow<Resource<List<Tag>>> = flow {
        try {
            emit(Resource.Loading<List<Tag>>())
            val tags = repository.getTags()
            emit(Resource.Success<List<Tag>>(tags.map { it.toTag() }))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Tag>>(e.localizedMessage ?: "An unexpected error ocurred."))
        } catch (e: IOException) {
            emit(Resource.Error<List<Tag>>("Couldn't reach server. Check your internet connection."))

        }
    }

    fun createTag(tagName: String): Flow<Resource<Tag>> = flow {
        try {
            emit(Resource.Loading<Tag>())
            val tag = repository.createTag(TagCreateDto(tagName))
            emit(Resource.Success<Tag>(tag.toTag()))
        } catch (e: HttpException) {
            emit(Resource.Error<Tag>(e.localizedMessage ?: "An unexpected error ocurred."))
        } catch (e: IOException) {
            emit(Resource.Error<Tag>("Couldn't reach server. Check your internet connection."))

        }
    }

    fun deleteTag(tagId: String): Flow<Resource<Response<Unit>>> = flow {
        try {
            emit(Resource.Loading<Response<Unit>>())

            emit(Resource.Success<Response<Unit>>(repository.deleteTag(tagId)))

        } catch (e: HttpException) {
            emit(
                Resource.Error<Response<Unit>>(
                    e.localizedMessage ?: "An unexpected error ocurred."
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<Response<Unit>>("Couldn't reach server. Check your internet connection."))

        }
    }
}

