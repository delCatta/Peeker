package com.sonder.peeker.data.remote

import android.database.Observable
import com.sonder.peeker.data.remote.dto.*
import com.sonder.peeker.domain.model.Document
import retrofit2.Response
import retrofit2.http.*

interface PeekerApi {
//    Sorting Example: https://www.youtube.com/watch?v=8YPXv7xKh2w
//    Follow https://www.youtube.com/watch?v=EF33KmyprEQ

    @GET("/documents.json")
    suspend fun getDocuments(): List<DocumentDto>

    @GET("/documents/favorites.json")
    suspend fun getFavoriteDocuments(): List<DocumentDto>

    @POST("/documents.json")
    suspend fun createDocument(@Body document: DocumentCreateDto): DocumentDto

    @POST("/documents.json")
    suspend fun createEmptyDocument(@Body data: Map<String,String>): DocumentDto

    @GET("/documents/{documentId}.json")
    suspend fun getDocumentById(@Path("documentId") documentId: String): DocumentDto
    @PUT("/documents/{documentId}.json")
    suspend fun updateDocumentById(@Path("documentId") documentId: String, @Body data: Map<String,String>): DocumentDto

    @POST("/sign_up")
    suspend fun signUp(@Body registrationDto: RegistrationDto): UserDto

    @POST("/sign_in")
    suspend fun signIn(@Body loginDto: LoginDto): Response<SessionDto>

    @GET("/users/me.json")
    suspend fun getUser(): UserDto
}