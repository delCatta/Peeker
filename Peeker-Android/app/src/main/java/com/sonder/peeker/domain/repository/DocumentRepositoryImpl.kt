package com.sonder.peeker.domain.repository

import com.sonder.peeker.data.remote.PeekerApi
import com.sonder.peeker.data.remote.dto.DocumentDto
import javax.inject.Inject

class DocumentRepositoryImpl @Inject constructor(
    private val api: PeekerApi
) : DocumentRepository {
    override suspend fun getDocuments(): List<DocumentDto> {
        return api.getDocuments()
    }

    override suspend fun getDocument(documentId: String): DocumentDto {
        return api.getDocumentById(documentId)
    }

}