package cookie.backend.board.common.response

import cookie.backend.board.common.code.ErrorCode
import io.swagger.v3.oas.annotations.media.Schema

class ErrorResponse(
    @Schema(description = "http status 코드")
    val status: Int,
    @Schema(description = "게시판 서비스 내부 코드")
    val code: String,
    @Schema(description = "에러 메시지")
    val message: String) {

    constructor(errorCode: ErrorCode) : this(
        status = errorCode.status,
        code = errorCode.code,
        message = errorCode.message
    )

    companion object {
        fun of(errorCode: ErrorCode): ErrorResponse = ErrorResponse(errorCode)
    }
}