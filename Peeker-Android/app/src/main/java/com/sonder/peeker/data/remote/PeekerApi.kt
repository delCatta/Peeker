package com.sonder.peeker.data.remote

import android.database.Observable
import com.google.gson.JsonElement
import com.sonder.peeker.data.remote.dto.*
import com.sonder.peeker.domain.model.Document
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import javax.annotation.Nullable

interface PeekerApi {
    //    Sorting Example: https://www.youtube.com/watch?v=8YPXv7xKh2w
    //    Follow https://www.youtube.com/watch?v=EF33KmyprEQ

    //    Documents
    @GET("/documents.json")
    suspend fun getDocuments(): List<DocumentDto>

    @GET("/documents/about_to_expire.json")
    suspend fun getExpiredDocuments(): List<DocumentDto>

    @GET("/documents/favorites.json")
    suspend fun getFavoriteDocuments(): List<DocumentDto>

    @GET("/tags/{tagId}/documents")
    suspend fun getDocumentsByTag(@Path("tagId") tagId: String): List<DocumentDto>

    @POST("/documents.json")
    suspend fun createDocument(@Body document: DocumentCreateDto): DocumentDto

    @POST("/documents.json")
    suspend fun createEmptyDocument(@Body data: Map<String, String>): DocumentDto

    @GET("/documents/{documentId}.json")
    suspend fun getDocumentById(@Path("documentId") documentId: String): DocumentDto

    @PUT("/documents/{documentId}.json")
    suspend fun updateDocumentById(
        @Path("documentId") documentId: String,
        @Body data: Map<String, String>
    ): DocumentDto

    @DELETE("/documents/{documentId}")
    suspend fun deleteDocumentById(@Path("documentId") documentId: String): Response<Unit>

    @Multipart
    @POST("/documents.json")
    fun createDocumentFromFile(
        @Part file: MultipartBody.Part
    ): Call<DocumentDto>

    //    Tags
    @GET("/tags")
    suspend fun getTags(): List<TagDto>

    @POST("/tags.json")
    suspend fun createTag(@Body tag: TagCreateDto): TagDto

    @PUT("/tags/{tagId}.json")
    suspend fun updateTagById(
        @Path("tagId") tagId: String,
        @Body data: Map<String, String>
    ): TagDto

    @PUT("/documents/{documentId}/tags/{tagId}")
    suspend fun setTagToDocument(
        @Path("documentId") documentId: String,
        @Path("tagId") tagId: String
    ): Response<Unit>

    @DELETE("/tags/{tagId}")
    suspend fun deleteTagById(@Path("tagId") tagId: String): Response<Unit>

    @POST("/documents.json")
    suspend fun createDocumentFromFile(@Body data: MultipartBody): DocumentDto

    //    Authentication
    @POST("/sign_up")
    suspend fun signUp(@Body registrationDto: RegistrationDto): UserDto

    @POST("/sign_in")
    suspend fun signIn(@Body loginDto: LoginDto): Response<SessionDto>

    @GET("/users/me.json")
    suspend fun getUser(): UserDto

    @PUT("/users/me.json")
    suspend fun updateUser(@Body data: Map<String, String>): UserDto

    //    Notifications
    @GET("/notifications.json")
    suspend fun getNotifications(): List<NotificationDto>

    @GET("/notifications/{notificationId}.json")
    suspend fun getNotificationById(@Path("notificationId") documentId: String): NotificationDto

}