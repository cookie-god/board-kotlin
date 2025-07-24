package cookie.backend.board.domain.user.repository

import cookie.backend.board.entity.UserInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<UserInfo, Long>, CustomUserRepository {
}