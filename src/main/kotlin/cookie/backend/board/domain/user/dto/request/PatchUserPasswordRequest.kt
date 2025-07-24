package cookie.backend.board.domain.user.dto.request

import cookie.backend.board.config.RegExpression
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.*

data class PatchUserPasswordRequest (
    @field:NotBlank(message = "PASSWORD_001")
    @field:Pattern(regexp = RegExpression.PASSWORD_PATTERN, message = "PASSWORD_002")
    @Schema(description = "비밀번호", example = "qwer1234!")
    val password: String,

    @field:NotBlank(message = "PASSWORD_004")
    @field:Pattern(regexp = RegExpression.PASSWORD_PATTERN, message = "PASSWORD_005")
    @Schema(description = "비밀번호", example = "qwer5678!")
    val newPassword: String,
)