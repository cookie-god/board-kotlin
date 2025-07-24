package cookie.backend.board.domain.auth

import cookie.backend.board.common.code.ErrorCode
import cookie.backend.board.config.exception.BoardException
import cookie.backend.board.config.jwt.JwtTokenProvider
import cookie.backend.board.domain.auth.dto.request.PostSignInRequest
import cookie.backend.board.domain.auth.dto.request.PostSignUpRequest
import cookie.backend.board.domain.auth.dto.response.PostSignInResponse
import cookie.backend.board.domain.auth.dto.response.PostSignUpResponse
import cookie.backend.board.domain.auth.dto.common.UserBasicInfo
import cookie.backend.board.entity.UserInfo
import cookie.backend.board.enums.Status
import jakarta.transaction.Transactional
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authRepository: AuthRepository,
    private val bcryptPasswordEncoder: BCryptPasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
) {
    private val logger: Logger = LoggerFactory.getLogger(AuthService::class.java)

    fun retrieveUserInfoById(id: Long): UserInfo? {
        return authRepository.findByIdAndStatus(id, Status.ACTIVE)
    }

    @Transactional
    fun signUp(req: PostSignUpRequest): PostSignUpResponse {
        validate(req)
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

    fun signIn(req: PostSignInRequest): PostSignInResponse {
        val userInfo = authRepository.findUserInfoByEmail(req.email) ?: throw BoardException(ErrorCode.NOT_EXIST_USER)
        if (!userInfo.checkPassword(bcryptPasswordEncoder, req.password)) {
            throw BoardException(ErrorCode.WRONG_PASSWORD)
        }
        val accessToken: String =
            jwtTokenProvider.createAccessToken(UserBasicInfo.of(userInfo.id, userInfo.email, userInfo.nickname, userInfo.role))

        return PostSignInResponse(
            token = accessToken,
            userId = userInfo.id,
            email = userInfo.email,
            nickname = userInfo.nickname,
            role = userInfo.role
        )
    }


    fun validate(req: PostSignUpRequest) {
        if (authRepository.existByEmail(req.email)) {
            logger.info("Email already exists: ${req.email}")
            throw BoardException(ErrorCode.DUPLICATE_EMAIL)
        }
        if (authRepository.existByNickname(req.nickname)) {
            logger.info("Nickname already exists: ${req.nickname}")
            throw BoardException(ErrorCode.DUPLICATE_NICKNAME)
        }
    }
}