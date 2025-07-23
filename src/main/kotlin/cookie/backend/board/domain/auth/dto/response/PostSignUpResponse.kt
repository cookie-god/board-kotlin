package cookie.backend.board.domain.auth.dto.response

import io.swagger.v3.oas.annotations.media.Schema

data class PostSignUpResponse(
    @Schema(description = "유저 아이디", example = "1")
    val userId: Long,
)