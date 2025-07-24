package cookie.backend.board.domain.auth

import cookie.backend.board.common.response.BasicResponse
import cookie.backend.board.domain.auth.dto.request.PostSignInRequest
import cookie.backend.board.domain.auth.dto.request.PostSignUpRequest
import cookie.backend.board.domain.auth.dto.response.PostSignInResponse
import cookie.backend.board.domain.auth.dto.response.PostSignUpResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping(value = ["sign-up"], produces = ["application/json"])
    fun postSignUp(@Valid @RequestBody request: PostSignUpRequest): BasicResponse<PostSignUpResponse> {
        return BasicResponse.of(authService.signUp(request))
    }

    @PostMapping(value = ["sign-in"], produces = ["application/json"])
    fun postSignIn(@Valid @RequestBody request: PostSignInRequest): BasicResponse<PostSignInResponse> {
        return BasicResponse.of(authService.signIn(request))
    }
}