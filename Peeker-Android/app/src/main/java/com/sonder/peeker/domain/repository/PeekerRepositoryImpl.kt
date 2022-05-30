package com.sonder.peeker.domain.repository

import android.database.Observable
import com.sonder.peeker.data.remote.PeekerApi
import com.sonder.peeker.data.remote.dto.*
import retrofit2.Response
import javax.inject.Inject

class PeekerRepositoryImpl @Inject constructor(
    private val api: PeekerApi
) : PeekerRepository {
    override suspend fun getCurrentUser(): UserDto {
        return api.getUser()
    }

    override suspend fun getDocuments(): List<DocumentDto> {
        return api.getDocuments()
    }

    override suspend fun getDocument(documentId: String): DocumentDto {
        return api.getDocumentById(documentId)
    }
    override suspend fun updateDocument(documentId:String,data:Map<String,String>): DocumentDto {
        return api.updateDocumentById(documentId,data)
    }

    override suspend fun createDocument(document: DocumentCreateDto): DocumentDto {
        return api.createDocument(document)
    }

    override suspend fun createUser(
        name:String,
        last_name:String,
        email: String,
        password: String,
        confirm_password: String
    ): UserDto {
        return api.signUp(RegistrationDto(name,last_name,email,password,confirm_password))
    }

    override suspend fun createSession(email: String, password: String): Response<SessionDto> {
        return api.signIn(LoginDto(email,password))

    }

}