package com.sonder.peeker.domain.repository

import android.database.Observable
import com.sonder.peeker.data.remote.dto.*
import com.sonder.peeker.domain.model.Document
import retrofit2.Call
import retrofit2.Response
import java.io.File
import javax.annotation.Nullable

interface   PeekerRepository {
    // Authentication
    suspend fun getCurrentUser(): UserDto
    suspend fun createUser(
        name: String,
        last_name: String,
        email: String,
        password: String,
        confirm_password: String
    ): UserDto

    suspend fun createSession(email: String, password: String): Response<SessionDto>

    //    Documents
    suspend fun getDocuments(): List<DocumentDto>
    suspend fun getExpiredDocuments(): List<DocumentDto>
    suspend fun getDocument(documentId: String): DocumentDto
    suspend fun getFavoriteDocuments(): List<DocumentDto>
    suspend fun updateDocument(documentId: String, data: Map<String, String>): DocumentDto
    suspend fun deleteDocument(documentId: String): Response<Unit>
    suspend fun createDocument(document: DocumentCreateDto): DocumentDto
    suspend fun createEmptyDocument(): DocumentDto
    suspend fun createDocumentWithFile(file: File): Call<DocumentDto>
    suspend fun getDocumentsByTag(tagId: String): List<DocumentDto>

    //    Tags
    suspend fun getTags(): List<TagDto>
    suspend fun createTag(tag: TagCreateDto): TagDto
    suspend fun updateTag(tagId: String, data: Map<String, String>): TagDto
    suspend fun deleteTag(tagId: String): Response<Unit>

    // Notifications
    suspend fun getNotifications(): List<NotificationDto>
    suspend fun getNotification(notificationId: String): NotificationDto
}