package com.sonder.peeker.data.remote

import com.sonder.peeker.data.remote.dto.DocumentDto
import retrofit2.http.GET
import retrofit2.http.Path

interface PeekerApi {
//    Sorting Example: https://www.youtube.com/watch?v=8YPXv7xKh2w
//    Follow https://www.youtube.com/watch?v=EF33KmyprEQ

    @GET("/documents.json")
    suspend fun getDocuments(): List<DocumentDto>

    @GET("/documents/{documentId}.json")
    suspend fun getDocumentById(@Path("documentId") documentId: String): DocumentDto


}