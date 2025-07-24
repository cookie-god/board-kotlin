package cookie.backend.board.common.response

import cookie.backend.board.common.code.SuccessCode
import io.swagger.v3.oas.annotations.media.Schema

class BasicResponse<T>(
    @Schema(description = "결과값")
    val result: T,
    @Schema(description = "http status 코드", example = "200")
    val code: Int = 200,
    @Schema(description = "성공 메시지", example = "OK")
    val message: String = "OK"
    ) {

    companion object {
        fun <T> of(result: T, successCode: SuccessCode): BasicResponse<T> = BasicResponse(result, successCode.status, successCode.message)
    }
}