package cookie.backend.board.common.response

import io.swagger.v3.oas.annotations.media.Schema

class BasicResponse<T>(
    @Schema(description = "결과값")
    result: T,
    @Schema(description = "http status 코드", example = "200")
    code: Int,
    @Schema(description = "성공 메시지", example = "OK")
    message: String
    ) {

    companion object {
        fun <T> of(result: T): BasicResponse<T> = BasicResponse(result, 200, "OK")
    }
}