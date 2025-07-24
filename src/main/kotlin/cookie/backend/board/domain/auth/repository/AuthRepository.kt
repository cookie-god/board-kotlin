package cookie.backend.board.domain.auth.repository

import cookie.backend.board.entity.UserInfo
import cookie.backend.board.enums.Status
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthRepository: JpaRepository<UserInfo, Long>, CustomAuthRepository
{
    fun findByIdAndStatus(id: Long, status: Status): UserInfo?
}