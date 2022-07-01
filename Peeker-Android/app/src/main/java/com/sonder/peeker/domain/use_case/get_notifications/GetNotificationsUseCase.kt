package com.sonder.peeker.domain.use_case.get_notifications

import com.sonder.peeker.core.Resource
import com.sonder.peeker.data.remote.dto.toNotification
import com.sonder.peeker.domain.model.Notification
import com.sonder.peeker.domain.repository.PeekerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(
    private val repository: PeekerRepository
) {
    operator fun invoke(): Flow<Resource<List<Notification>>> = flow {
        try {
            emit(Resource.Loading<List<Notification>>())
            val notifications = repository.getNotifications()
            emit(Resource.Success<List<Notification>>(notifications.map { it.toNotification() }))
        } catch (e: HttpException) {
            emit(
                Resource.Error<List<Notification>>(
                    e.localizedMessage ?: "An unexpected error ocurred."
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<List<Notification>>("Couldn't reach server. Check your internet connection."))

        }
    }
}