package cookie.backend.board.domain.user

import cookie.backend.board.common.code.ErrorCode.NOT_EXIST_USER
import cookie.backend.board.common.code.ErrorCode.WRONG_PASSWORD
import cookie.backend.board.config.exception.BoardException
import cookie.backend.board.domain.user.dto.request.PatchUserPasswordRequest
import cookie.backend.board.domain.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val bcryptPasswordEncoder: BCryptPasswordEncoder
) {
    @Transactional
    fun editPassword(userId: Long, req: PatchUserPasswordRequest){
        val userInfo = userRepository.findUserInfoById(userId) ?: throw BoardException(NOT_EXIST_USER) // 사용자 조회
        if (!userInfo.checkPassword(bcryptPasswordEncoder, req.nowPassword)) {
            // 현재 비밀번호가 일치하지 않는 경우 예외 발생
            throw BoardException(WRONG_PASSWORD)
        }
        userInfo.changePassword(bcryptPasswordEncoder, req.newPassword) // 비밀번호 변경
    }
}
