package com.sonder.peeker.domain.use_case.create_session

import android.util.Log
import com.sonder.peeker.core.Resource
import com.sonder.peeker.data.remote.dto.SessionDto
import com.sonder.peeker.data.remote.dto.toSession
import com.sonder.peeker.di.SessionManager
import com.sonder.peeker.domain.model.Session
import com.sonder.peeker.domain.repository.PeekerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class CreateSessionUseCase @Inject constructor(
    private val repository: PeekerRepository,
    private val sessionManager: SessionManager
){
    operator fun invoke(email:String, password:String): Flow<Resource<Session>> = flow {
        try {
            emit(Resource.Loading<Session>())
            val response = repository.createSession(email,password)
            val sessionDto = response.body()!!
            sessionManager.saveAuthToken(response.headers()["X-Session-Token"].toString(), sessionDto.id)
            emit(Resource.Success(sessionDto.toSession(response.headers()["X-Session-Token"].toString())))
        } catch (e: HttpException) {
            emit(Resource.Error<Session>(e.localizedMessage ?: "An unexpected error ocurred."))
        } catch (e: IOException) {
            emit(Resource.Error<Session>("Couldn't reach server. Check your internet connection."))
        }
    }
}
