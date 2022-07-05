package com.sonder.peeker.domain.repository

import android.util.Log
import com.sonder.peeker.domain.model.Document
import com.sonder.peeker.domain.model.User
import retrofit2.Callback
import retrofit2.Retrofit
import com.sonder.peeker.core.Constants.API_URL
import com.sonder.peeker.data.remote.PeekerApi
import com.sonder.peeker.data.remote.dto.*
import com.sonder.peeker.di.SessionManager
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject


class PeekerRepositoryImpl @Inject constructor(
    private val api: PeekerApi
) : PeekerRepository {
    //    Authentication
    override suspend fun getCurrentUser(): UserDto {
        return api.getUser()
    }
    override suspend fun udpateCurrentUser(data: Map<String, String>): UserDto{
        return api.updateUser(data)

    }

    override suspend fun createUser(
        name: String,
        last_name: String,
        email: String,
        password: String,
        confirm_password: String
    ): UserDto {
        return api.signUp(RegistrationDto(name, last_name, email, password, confirm_password))
    }

    override suspend fun createSession(email: String, password: String): Response<SessionDto> {
        return api.signIn(LoginDto(email, password))

    }

    //    Tags
    override suspend fun getTags(): List<TagDto> {
        return api.getTags()
    }

    override suspend fun createTag(tag: TagCreateDto): TagDto {
        return api.createTag(tag)
    }

    override suspend fun updateTag(
        tagId: String,
        data: Map<String, String>
    ): TagDto {
        return api.updateTagById(tagId, data)
    }

    override suspend fun deleteTag(tagId: String): Response<Unit> {
        return api.deleteTagById(tagId)
    }

    override suspend fun setTagToDocument(documentId: String, tagId: String): Response<Unit> {
        return api.setTagToDocument(documentId,tagId)
    }

    //    Notifications
    override suspend fun getNotifications(): List<NotificationDto> {
        return api.getNotifications()
    }

    override suspend fun getNotification(notificationId: String): NotificationDto {
        return api.getNotificationById(notificationId)
    }

    //    Documents
    override suspend fun getDocuments(): List<DocumentDto> {
        return api.getDocuments()
    }

    override suspend fun getExpiredDocuments(): List<DocumentDto> {
        return api.getExpiredDocuments()
    }

    override suspend fun getDocumentsByTag(tagId: String): List<DocumentDto> {
        return api.getDocumentsByTag(tagId)
    }

    override suspend fun getFavoriteDocuments(): List<DocumentDto> {
        return api.getFavoriteDocuments()
    }

    override suspend fun getDocument(documentId: String): DocumentDto {
        return api.getDocumentById(documentId)
    }

    override suspend fun updateDocument(
        documentId: String,
        data: Map<String, String>
    ): DocumentDto {
        return api.updateDocumentById(documentId, data)
    }

    override suspend fun deleteDocument(documentId: String): Response<Unit> {
        return api.deleteDocumentById(documentId)
    }

    override suspend fun createDocument(document: DocumentCreateDto): DocumentDto {
        return api.createDocument(document)
    }

    override suspend fun createEmptyDocument(): DocumentDto {
        // TODO no critico : Hacer los maps en el Document.kt para que no quede la cagada.
        val current = LocalDateTime.now()
        val twoMonths = LocalDateTime.now().plusDays(60)
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val today = current.format(formatter)
        val inTwoMonths = twoMonths.format(formatter)
        return api.createEmptyDocument(
            mapOf(
                "emission_date" to today,
                "expiration_date" to inTwoMonths
            )
        )
    }
    override suspend fun createDocumentFromFile(file:File): DocumentDto {
        return api.createDocumentFromFile(
            MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("document[file]", file.name, file.asRequestBody())
                .build()
        )
    }
}