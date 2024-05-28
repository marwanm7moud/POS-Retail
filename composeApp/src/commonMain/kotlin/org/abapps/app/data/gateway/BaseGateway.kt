package org.abapps.app.data.gateway

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.utils.io.errors.IOException
import org.abapps.app.data.remote.model.ServerResponse
import org.abapps.app.domain.util.NetworkException
import org.abapps.app.domain.util.NotFoundException
import org.abapps.app.domain.util.PermissionDeniedException
import org.abapps.app.domain.util.ServerErrorException
import org.abapps.app.domain.util.UnAuthException
import org.abapps.app.domain.util.UnknownErrorException
import org.abapps.app.domain.util.ValidationNetworkException

open class BaseGateway(val client: HttpClient) {
    suspend inline fun <reified T> tryToExecute(method: HttpClient.() -> HttpResponse): T {
        try {
            return client.method().body()
        } catch (e: ClientRequestException) {
            val errorMessages = e.response.body<ServerResponse<T>>().status.messageError
            when (e.response.body<ServerResponse<T>>().status.code) {
                400 -> throw ValidationNetworkException(errorMessages)
                401 -> throw UnAuthException(errorMessages)
                404 -> throw NotFoundException(errorMessages)
                405 -> throw PermissionDeniedException(errorMessages)
                else -> throw UnknownErrorException(errorMessages)
            }
        } catch (e: RedirectResponseException) {
            val errorMessages = e.response.body<ServerResponse<T>>().status.messageError
            throw UnknownErrorException(errorMessages)
        } catch (e: ServerResponseException) {
            val errorMessages = e.response.body<ServerResponse<T>>().status.messageError
            throw ServerErrorException(errorMessages)
        } catch (e: IOException) {
            val errorMessages = e.message.toString()
            throw NetworkException(errorMessages)
        } catch (e: Exception) {
            val errorMessages = e.message.toString()
            throw UnknownErrorException(errorMessages)
        }
    }
}