package com.sonder.peeker.domain.use_case.create_document

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import com.sonder.peeker.core.Resource
import com.sonder.peeker.data.remote.dto.DocumentCreateDto
import com.sonder.peeker.data.remote.dto.toDocument
import com.sonder.peeker.domain.model.Document
import com.sonder.peeker.domain.repository.PeekerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.*
import java.io.File
import java.io.IOException
import javax.inject.Inject


class CreateDocumentUseCase @Inject constructor(
    private val repository: PeekerRepository
) {
    operator fun invoke(documentCreateDto: DocumentCreateDto): Flow<Resource<Document>> = flow {
        try {
            emit(Resource.Loading<Document>())
            val document = repository.createDocument(documentCreateDto)
            emit(Resource.Success(document.toDocument()))
        } catch (e: HttpException) {
            emit(Resource.Error<Document>(e.localizedMessage ?: "An unexpected error ocurred."))
        } catch (e: IOException) {
            emit(Resource.Error<Document>("Couldn't reach server. Check your internet connection."))

        }
    }

    fun fromEmptyDocument(): Flow<Resource<Document>> = flow {
        try {
            emit(Resource.Loading<Document>())
            val document = repository.createEmptyDocument()
            emit(Resource.Success(document.toDocument()))
        } catch (e: HttpException) {
            emit(Resource.Error<Document>(e.localizedMessage ?: "An unexpected error ocurred."))
        } catch (e: IOException) {
            emit(Resource.Error<Document>("Couldn't reach server. Check your internet connection."))

        }
    }

    fun fromDocumentFile(file: File): Flow<Resource<Document>> =

        flow {
            try {
                emit(Resource.Loading<Document>())
                val document = repository.createDocumentFromFile(file)
                emit(Resource.Success(document.toDocument()))


            } catch (e: HttpException) {
                emit(
                    Resource.Error<Document>(
                        e.localizedMessage ?: "An unexpected error ocurred."
                    )
                )
            } catch (e: IOException) {
                Log.d("Error", e.localizedMessage)
                emit(Resource.Error<Document>("Couldn't reach server. Check your internet connection."))

            }

        }


}
