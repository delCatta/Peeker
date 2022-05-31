package com.sonder.peeker.domain.use_case.update_document

import com.sonder.peeker.core.Resource
import com.sonder.peeker.data.remote.dto.DocumentCreateDto
import com.sonder.peeker.data.remote.dto.DocumentDto
import com.sonder.peeker.data.remote.dto.toDocument
import com.sonder.peeker.domain.model.Document
import com.sonder.peeker.domain.repository.PeekerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.annotation.Nullable
import javax.inject.Inject


class UpdateDocumentUseCase @Inject constructor(
    private val repository: PeekerRepository
) {
    operator fun invoke(documentId:String, data: Map<String,String>): Flow<Resource<Document>> = flow {
        try {
            emit(Resource.Loading<Document>())
            val document = repository.updateDocument(documentId,data)
            emit(Resource.Success(document.toDocument()))
        } catch (e: HttpException) {
            emit(Resource.Error<Document>(e.localizedMessage ?: "An unexpected error ocurred."))
        } catch (e: IOException) {
            emit(Resource.Error<Document>("Couldn't reach server. Check your internet connection."))

        }
    }
    fun deleteDocument(documentId:String): Flow<Resource<Map<String,String>>> = flow {
        try {
            repository.deleteDocument(documentId)
            emit(Resource.Success(emptyMap()))
        } catch (e: HttpException) {
            emit(Resource.Error<Map<String,String>>(e.localizedMessage ?: "An unexpected error ocurred."))
        } catch (e: IOException) {
            emit(Resource.Error<Map<String,String>>("Couldn't reach server. Check your internet connection."))

        }
    }

}