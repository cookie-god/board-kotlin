package cookie.backend.board.domain.auth.dto.request

import cookie.backend.board.config.RegExpression
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.*

data class PostSignUpRequest (
    @field:NotBlank(message = "EMAIL_001")
    @field:Pattern(regexp = RegExpression.EMAIL_PATTERN, message = "EMAIL_002")
    @Schema(description = "이메일", example = "lion0193@gmail.com")
    val email: String,

    @field:NotBlank(message = "PASSWORD_001")
    @field:Pattern(regexp = RegExpression.PASSWORD_PATTERN, message = "PASSWORD_002")
    @Schema(description = "비밀번호", example = "qwer1234!")
    val password: String,

    @field:NotBlank(message = "NICKNAME_001")
    @field:Size(min=0, max= 10, message = "NICKNAME_002")
    @Schema(description = "닉네임", example = "쿠키갓")
    val nickname: String
)