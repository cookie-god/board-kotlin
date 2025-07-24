package cookie.backend.board.domain.user.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import cookie.backend.board.entity.QUserInfo.userInfo
import cookie.backend.board.entity.UserInfo
import cookie.backend.board.enums.Status
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
): CustomUserRepository {
    private val logger: Logger = LoggerFactory.getLogger(UserRepositoryImpl::class.java)
    override fun findUserInfoById(id: Long): UserInfo? {
        return jpaQueryFactory
            .select(userInfo)
            .from(userInfo)
            .where(
                userInfo.id.eq(id),
                userInfo.status.eq(Status.ACTIVE)
            )
            .fetchFirst()
    }
}