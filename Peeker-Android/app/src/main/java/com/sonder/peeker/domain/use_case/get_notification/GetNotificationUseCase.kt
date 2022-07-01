package com.sonder.peeker.domain.use_case.get_notification

import com.sonder.peeker.core.Resource
import com.sonder.peeker.data.remote.dto.toNotification
import com.sonder.peeker.domain.model.Notification
import com.sonder.peeker.domain.repository.PeekerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetNotificationUseCase @Inject constructor(
    private val repository: PeekerRepository
) {
    operator fun invoke(notificationId: String): Flow<Resource<Notification>> = flow {
        try {
            emit(Resource.Loading<Notification>())
            val notification = repository.getNotification(notificationId)
            emit(Resource.Success(notification.toNotification()))
        } catch (e: HttpException) {
            emit(Resource.Error<Notification>(e.localizedMessage?:"An unexpected error ocurred."))
        } catch (e: IOException) {
            emit(Resource.Error<Notification>("Couldn't reach server. Check your internet connection."))

        }
    }
}