package com.sonder.peeker.domain.repository

import com.sonder.peeker.data.remote.PeekerApi
import com.sonder.peeker.data.remote.dto.DocumentCreateDto
import com.sonder.peeker.data.remote.dto.DocumentDto
import com.sonder.peeker.data.remote.dto.RegistrationDto
import com.sonder.peeker.data.remote.dto.UserDto
import com.sonder.peeker.domain.model.Document
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

}