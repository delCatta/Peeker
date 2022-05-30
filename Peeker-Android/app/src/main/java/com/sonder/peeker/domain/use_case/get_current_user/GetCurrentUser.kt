package com.sonder.peeker.domain.use_case.get_current_user

import com.sonder.peeker.core.Resource
import com.sonder.peeker.data.remote.dto.toUser
import com.sonder.peeker.domain.model.User
import com.sonder.peeker.domain.repository.PeekerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class GetCurrentUserUseCase @Inject constructor(
    private val repository: PeekerRepository

) {
    operator fun invoke(): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading<User>())
            val userDto = repository.getCurrentUser()
            emit(Resource.Success(userDto.toUser()))
        } catch (e: HttpException) {
            emit(Resource.Error<User>(e.localizedMessage?:"An unexpected error ocurred."))
        } catch (e: IOException) {
            emit(Resource.Error<User>("Couldn't reach server. Check your internet connection."))

        }
    }
}
