package com.sonder.peeker.data.remote

import com.sonder.peeker.data.remote.dto.*
import com.sonder.peeker.domain.model.Document
import retrofit2.http.*

interface PeekerApi {
//    Sorting Example: https://www.youtube.com/watch?v=8YPXv7xKh2w
//    Follow https://www.youtube.com/watch?v=EF33KmyprEQ

    @GET("/documents.json")
    suspend fun getDocuments(): List<DocumentDto>

    @POST("/documents.json")
    suspend fun createDocument(@Body document: DocumentCreateDto): DocumentDto

    @GET("/documents/{documentId}.json")
    suspend fun getDocumentById(@Path("documentId") documentId: String): DocumentDto

    @POST("/sign_up")
    suspend fun signUp(@Body registrationDto: RegistrationDto): UserDto

    @POST("/sign_in")
    suspend fun signIn(@Body loginDto: LoginDto): UserDto
}