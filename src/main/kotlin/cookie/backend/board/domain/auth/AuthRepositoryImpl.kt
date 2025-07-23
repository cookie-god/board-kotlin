package cookie.backend.board.domain.auth

import com.querydsl.jpa.impl.JPAQueryFactory
import cookie.backend.board.entity.QUserInfo.userInfo
import cookie.backend.board.enum.Status
import org.springframework.stereotype.Repository

@Repository
class AuthRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : CustomAuthRepository {

    override fun existByEmail(email: String): Boolean {
        val fetchOne = jpaQueryFactory
            .selectOne()
            .from(userInfo)
//            .where(userInfo.email.eq(email), userInfo.status.eq(Status.ACTIVE))
            .fetchOne()
        return fetchOne != null
    }

    override fun existByNickname(nickname: String): Boolean {
        TODO("Not yet implemented")
    }
}