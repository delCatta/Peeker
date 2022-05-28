package com.sonder.peeker.domain.use_case.get_documents

import android.util.Log
import com.sonder.peeker.core.Resource
import com.sonder.peeker.data.remote.dto.toDocument
import com.sonder.peeker.domain.model.Document
import com.sonder.peeker.domain.repository.PeekerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetDocumentsUseCase @Inject constructor(
    private val repository: PeekerRepository
) {
    operator fun invoke(): Flow<Resource<List<Document>>> = flow {
        try {
            emit(Resource.Loading<List<Document>>())
            val documents = repository.getDocuments()
            emit(Resource.Success<List<Document>>(documents.map { it.toDocument() }))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Document>>(e.localizedMessage?:"An unexpected error ocurred."))
        } catch (e: IOException) {
            emit(Resource.Error<List<Document>>("Couldn't reach server. Check your internet connection."))

        }
    }
}