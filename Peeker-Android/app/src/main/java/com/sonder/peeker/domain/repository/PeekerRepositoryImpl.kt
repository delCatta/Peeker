package com.sonder.peeker.domain.repository

import com.sonder.peeker.data.remote.PeekerApi
import com.sonder.peeker.data.remote.dto.DocumentDto
import com.sonder.peeker.data.remote.dto.RegistrationDto
import com.sonder.peeker.data.remote.dto.UserDto
import javax.inject.Inject

class PeekerRepositoryImpl @Inject constructor(
    private val api: PeekerApi
) : PeekerRepository {
    override suspend fun getDocuments(): List<DocumentDto> {
        return api.getDocuments()
    }

    override suspend fun getDocument(documentId: String): DocumentDto {
        return api.getDocumentById(documentId)
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

}