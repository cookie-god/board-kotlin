package cookie.backend.board.domain.auth

import cookie.backend.board.common.response.BasicResponse
import cookie.backend.board.domain.auth.dto.response.PostSignUpResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping(value = ["sign-up"], produces = ["application/json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun postSignUp(): BasicResponse<PostSignUpResponse> {
        return BasicResponse(
            result = PostSignUpResponse(
                userId = 1L,
            ),
            code = HttpStatus.CREATED.value(),
        )
    }
}