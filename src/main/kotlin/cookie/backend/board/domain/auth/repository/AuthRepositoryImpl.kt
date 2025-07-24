package cookie.backend.board.domain.auth.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import cookie.backend.board.entity.QUserInfo.userInfo
import cookie.backend.board.entity.UserInfo
import cookie.backend.board.enums.Status
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

@Repository
class AuthRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : CustomAuthRepository {

    private val logger: Logger = LoggerFactory.getLogger(AuthRepositoryImpl::class.java)

    override fun existByEmail(email: String): Boolean {
        logger.info("Checking existence of email: $email")
        val fetchOne = jpaQueryFactory
            .selectOne()
            .from(userInfo)
            .where(userInfo.email.eq(email), userInfo.status.eq(Status.ACTIVE))
            .fetchOne()
        return fetchOne != null
    }

    override fun existByNickname(nickname: String): Boolean {
        logger.info("Checking existence of nickname: $nickname")
        val fetchOne = jpaQueryFactory
            .selectOne()
            .from(userInfo)
            .where(userInfo.nickname.eq(nickname), userInfo.status.eq(Status.ACTIVE))
            .fetchOne()
        return fetchOne != null
    }

    override fun findUserInfoByEmail(email: String): UserInfo? {
        return jpaQueryFactory
            .select(userInfo)
            .from(userInfo)
            .where(
                userInfo.email.eq(email),
                userInfo.status.eq(Status.ACTIVE)
            )
            .fetchFirst()
    }
}