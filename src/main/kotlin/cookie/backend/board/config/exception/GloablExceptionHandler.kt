package cookie.backend.board.config.exception

import cookie.backend.board.common.code.ErrorCode
import cookie.backend.board.common.response.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    private val log = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(BoardException::class)
    fun handleBoardException(exception: BoardException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse.of(exception.errorCode)
        log.error("BoardException: ${exception.message}")
        return ResponseEntity.status(errorResponse.status).body(errorResponse)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR)
        log.error("Internal Server Error", exception)
        return ResponseEntity.status(errorResponse.status).body(errorResponse)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val firstErrorCode = ex.bindingResult.fieldErrors.firstOrNull()?.defaultMessage
        val errorCode = ErrorCode.from(firstErrorCode)

        val errorResponse = ErrorResponse.of(errorCode)
        log.error("Validation failed: $firstErrorCode")
        return ResponseEntity.status(errorResponse.status).body(errorResponse)
    }

}