package cookie.backend.board.domain.auth

import cookie.backend.board.config.jwt.JwtTokenProvider
import cookie.backend.board.domain.auth.dto.request.PostSignUpRequest
import cookie.backend.board.domain.auth.dto.response.PostSignUpResponse
import cookie.backend.board.entity.UserInfo
import cookie.backend.board.enum.Status
import jakarta.transaction.Transactional
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authRepository: AuthRepository,
    private val bcryptPasswordEncoder: BCryptPasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
) {
    fun retrieveUserInfoById(id: Long): UserInfo? {
        return authRepository.findByIdAndStatus(id, Status.ACTIVE)
    }

    @Transactional
    fun createUser(req: PostSignUpRequest): PostSignUpResponse {
        var userInfo = UserInfo.of(
            email = req.email,
            password = req.password,
            nickname = req.nickname,
        )
        userInfo.hashPassword(bcryptPasswordEncoder)
        userInfo = authRepository.save(userInfo)
        return PostSignUpResponse(
            userId = userInfo.id,
        )
    }


    fun validate(req: PostSignUpRequest) {
//        if (authRepository.existsByEmail(req.email)) {
//            throw IllegalArgumentException("이미 존재하는 이메일입니다.")
//        }
//        if (authRepository.existsByNickname(req.nickname)) {
//            throw IllegalArgumentException("이미 존재하는 닉네임입니다.")
//        }
    }
}