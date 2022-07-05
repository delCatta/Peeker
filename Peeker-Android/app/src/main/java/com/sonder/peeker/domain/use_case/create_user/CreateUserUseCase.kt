package com.sonder.peeker.domain.use_case.create_user

import com.sonder.peeker.domain.repository.PeekerRepository

import com.sonder.peeker.core.Resource
import com.sonder.peeker.data.remote.dto.toUser
import com.sonder.peeker.domain.model.Document
import com.sonder.peeker.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val repository: PeekerRepository

) {
    operator fun invoke(name:String,last_name:String,email: String,password: String,confirm_password: String): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading<User>())
            val userDto = repository.createUser(name,last_name,email,password,confirm_password)
            emit(Resource.Success(userDto.toUser()))
        } catch (e: HttpException) {
            emit(Resource.Error<User>(e.localizedMessage?:"An unexpected error ocurred."))
        } catch (e: IOException) {
            emit(Resource.Error<User>("Couldn't reach server. Check your internet connection."))

        }
    }
    fun updateCurrentUser(notifications_enabled: Boolean, days_about_to_expire: Int): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading<User>())
            val userDto = repository.udpateCurrentUser(mutableMapOf("notifications_enabled" to notifications_enabled.toString(),"days_about_to_expire" to days_about_to_expire.toString() ))
            emit(Resource.Success(userDto.toUser()))
        } catch (e: HttpException) {
            emit(Resource.Error<User>(e.localizedMessage?:"An unexpected error ocurred."))
        } catch (e: IOException) {
            emit(Resource.Error<User>("Couldn't reach server. Check your internet connection."))

        }
    }

}




