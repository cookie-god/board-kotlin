package cookie.backend.board.domain.auth.dto.common

import cookie.backend.board.enums.Role
import io.swagger.v3.oas.annotations.media.Schema

data class UserBasicInfo (
    @Schema(description = "유저 아이디", example = "1")
    val userId: Long,
    @Schema(description = "이메일", example = "lion0193@gmail.com")
    val email: String,
    @Schema(description = "닉네임", example = "쿠키갓")
    val nickname: String,
    @Schema(description = "권한", example = "ROLE_USER")
    val role: Role
) {
    companion object {
        fun of(userId: Long, email: String, nickname: String, role: Role): UserBasicInfo = UserBasicInfo(userId, email, nickname, role)
    }
}