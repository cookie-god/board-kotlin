package cookie.backend.board.domain.user.repository

import cookie.backend.board.entity.UserInfo

interface CustomUserRepository {
    fun findUserInfoById(id: Long): UserInfo?
}