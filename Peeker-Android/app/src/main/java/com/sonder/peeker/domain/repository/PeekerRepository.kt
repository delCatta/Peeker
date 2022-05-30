package com.sonder.peeker.domain.repository

import android.database.Observable
import com.sonder.peeker.data.remote.dto.DocumentCreateDto
import com.sonder.peeker.data.remote.dto.DocumentDto
import com.sonder.peeker.data.remote.dto.SessionDto
import com.sonder.peeker.data.remote.dto.UserDto
import com.sonder.peeker.domain.model.Document
import retrofit2.Response

interface PeekerRepository {

    suspend fun getCurrentUser(): UserDto
    suspend fun getDocuments(): List<DocumentDto>
    suspend fun getDocument(documentId: String): DocumentDto
    suspend fun createDocument(document: DocumentCreateDto): DocumentDto
    suspend fun createUser(name:String,last_name:String,email: String,password: String,confirm_password: String): UserDto
    suspend fun createSession(email: String,password: String): Response<SessionDto>

}