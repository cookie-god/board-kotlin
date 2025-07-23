package cookie.backend.board.domain.auth

import cookie.backend.board.entity.UserInfo
import cookie.backend.board.enum.Status
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authRepository: AuthRepository
) {
    fun retrieveUserInfoById(id: Long): UserInfo? {
        return authRepository.findByIdAndStatus(id, Status.ACTIVE)
    }
}