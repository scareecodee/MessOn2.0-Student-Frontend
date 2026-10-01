package com.messon.student.app.core


sealed class AppError(open val message: String, open val cause: Throwable? = null) {
    data class Network(override val message: String, override val cause: Throwable? = null) : AppError(message, cause)
    data class Server(val code: Int, override val message: String, override val cause: Throwable? = null) : AppError(message, cause)
    data class Auth(override val message: String, override val cause: Throwable? = null) : AppError(message, cause)
    data class NotFound(override val message: String, override val cause: Throwable? = null) : AppError(message, cause)
    data class Validation(override val message: String, override val cause: Throwable? = null) : AppError(message, cause)
    data class RateLimited(val retryAfterSeconds: Int? = null, override val message: String, override val cause: Throwable? = null) : AppError(message, cause)
    data class Unknown(override val message: String, override val cause: Throwable? = null) : AppError(message, cause)
}
