package com.sonder.peeker.domain.repository

import com.sonder.peeker.data.remote.dto.DocumentDto

interface DocumentRepository {

    suspend fun getDocuments(): List<DocumentDto>
    suspend fun getDocument(documentId: String): DocumentDto

}