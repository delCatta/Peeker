package com.sonder.peeker.domain.repository

import com.sonder.peeker.data.remote.dto.DocumentCreateDto
import com.sonder.peeker.data.remote.dto.DocumentDto
import com.sonder.peeker.data.remote.dto.UserDto
import com.sonder.peeker.domain.model.Document

interface PeekerRepository {

    suspend fun getDocuments(): List<DocumentDto>
    suspend fun getDocument(documentId: String): DocumentDto
    suspend fun createDocument(document: DocumentCreateDto): DocumentDto
    suspend fun createUser(name:String,last_name:String,email: String,password: String,confirm_password: String): UserDto

}