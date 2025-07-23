package cookie.backend.board.domain.auth

import cookie.backend.board.entity.UserInfo
import cookie.backend.board.enum.Status
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthRepository: JpaRepository<UserInfo, Long>
{
    fun findByIdAndStatus(id: Long, status: Status): UserInfo?
}