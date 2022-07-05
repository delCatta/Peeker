package com.sonder.peeker.domain.use_case.get_tags

import com.sonder.peeker.core.Resource
import com.sonder.peeker.data.remote.dto.toDocument
import com.sonder.peeker.data.remote.dto.toTag
import com.sonder.peeker.domain.model.Document
import com.sonder.peeker.domain.model.Tag
import com.sonder.peeker.domain.repository.PeekerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
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
            emit(Resource.Error<List<Tag>>(e.localizedMessage?:"An unexpected error ocurred."))
        } catch (e: IOException) {
            emit(Resource.Error<List<Tag>>("Couldn't reach server. Check your internet connection."))

        }
    }
}