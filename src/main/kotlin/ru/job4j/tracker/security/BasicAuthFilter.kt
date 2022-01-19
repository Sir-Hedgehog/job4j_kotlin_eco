package ru.job4j.tracker.security

import spark.Filter
import spark.Request
import spark.Response
import spark.Spark.halt
import java.util.*

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 1.0
 * @since 19.01.2022
 */
class BasicAuthFilter(private val authData: Map<String, String>) : Filter {

    /**
     * Метод проверяет пользователя через аутентификацию
     * @param request - запрос
     * @param response - ответ
     */
    override fun handle(request: Request, response: Response) {
        if (!request.headers().contains("Authorization") || !authenticated(request)) {
            response.header("WWW-Authenticate", BASIC_AUTHENTICATION_TYPE)
            halt(401, "Access is denied!")
        }
    }

    /**
     * Метод делает проверку корректности введенных данных с использованием кодировки
     * @param request - запрос
     * @return - аутентифицирован пользователь или нет
     */
    private fun authenticated(request: Request): Boolean {
        val encodedHeader = request.headers("Authorization").substringAfter("Basic").trim()
        val submittedCredentials = extractCredentials(encodedHeader)
        if (submittedCredentials != null && submittedCredentials.size == NUMBER_OF_AUTHENTICATION_FIELDS) {
            val submittedUsername = submittedCredentials[0]
            val submittedPassword = submittedCredentials[1]
            return authData.containsKey(submittedUsername) && authData.containsValue(submittedPassword)
        }
        return false
    }

    /**
     * Метод делает проверку корректности введенных данных с использованием кодировки
     * @param encodedHeader - данные кодировки
     * @return - декодированные данные
     */
    private fun extractCredentials(encodedHeader: String?): Array<String>? {
        return if (encodedHeader != null) {
            val decodedHeader = String(Base64.getDecoder().decode(encodedHeader))
            decodedHeader.split(":").toTypedArray()
        } else {
            null
        }
    }

    companion object {
        private const val BASIC_AUTHENTICATION_TYPE = "Basic"
        private const val NUMBER_OF_AUTHENTICATION_FIELDS = 2
    }
}