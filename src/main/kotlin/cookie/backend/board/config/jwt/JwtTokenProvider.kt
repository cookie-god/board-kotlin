package cookie.backend.board.config.jwt

import cookie.backend.board.domain.user.dto.UserBasicInfo
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.util.Date

@Component
class JwtTokenProvider (
    @Value("\${jwt.secret}") secretKey: String,
    @Value("\${jwt.expiration_time}") val accessTokenExpirationTime: Long
) {
    val key: Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))

    companion object {
        private const val TOKEN_USER_ID = "userId"
        private const val TOKEN_EMAIL = "email"
        private const val TOKEN_NICKNAME = "nickname"
        private const val TOKEN_ROLE = "role"
    }

    /**
     * 외부에서 접근 가능한 Access Token 생성 함수
     */
    fun createAccessToken(userBasicInfo: UserBasicInfo): String = createToken(userBasicInfo, accessTokenExpirationTime)

    /**
     * 내부에서 동작하는 Access Token 생성 함수
     */
    private fun createToken(userBasicInfo: UserBasicInfo, expirationTime: Long): String {
        val claims = Jwts.claims().apply {
            put(TOKEN_USER_ID, userBasicInfo.userId)
            put(TOKEN_EMAIL, userBasicInfo.email)
            put(TOKEN_NICKNAME, userBasicInfo.nickname)
            put(TOKEN_ROLE, userBasicInfo.role)
        }
        val now = Date()
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + expirationTime))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    /**
     * Access Token에서 사용자 아이디를 추출하는 함수
     */
    fun getUserId(token: String): Long? = parseClaims(token)?.get(TOKEN_USER_ID, Long::class.java)

    /**
     * Access Token에서 사용자 정보를 추출하는 함수
     */
    private fun parseClaims(accessToken: String): Claims? {
        return try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).body
        } catch (e: ExpiredJwtException) {
            null
        } catch (e: SignatureException) {
            null
        }
    }

    /**
     * Access Token의 유효성을 검증하는 함수
     */
    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            true
        } catch (e: SignatureException) {
            false
        } catch (e: ExpiredJwtException) {
            false
        } catch (e: UnsupportedJwtException) {
            false
        } catch (e: IllegalArgumentException) {
            false
        } catch (e: Exception) {
            false
        }
    }
}