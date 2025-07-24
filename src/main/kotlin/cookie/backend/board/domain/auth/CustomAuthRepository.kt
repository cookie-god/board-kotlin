package cookie.backend.board.domain.auth

import cookie.backend.board.entity.UserInfo

interface CustomAuthRepository {
    fun existByEmail(email: String): Boolean
    fun existByNickname(nickname: String): Boolean
    fun findUserInfoByEmail(email: String): UserInfo?
}