package cookie.backend.board.config.filter

import com.fasterxml.jackson.databind.ObjectMapper
import cookie.backend.board.common.code.ErrorCode
import cookie.backend.board.common.response.ErrorResponse
import cookie.backend.board.config.exception.BoardException
import cookie.backend.board.config.jwt.JwtTokenProvider
import cookie.backend.board.domain.auth.AuthService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider,
    private val authService: AuthService
) : OncePerRequestFilter() {

    /**
     * 필터를 적용하지 않아도 되는 api 체크
     */
    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val path = request.requestURI
        return SECURE_URLS.any { path.startsWith(it) }
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            var token = request.getHeader(AUTHORIZATION_HEADER)
            if (token != null && token.startsWith(TOKEN_PREFIX)) {
                token = token.substring(TOKEN_PREFIX.length)
                jwtTokenProvider.validateToken(token)
                val userInfo =
                    authService.retrieveUserInfoById(
                        jwtTokenProvider.getUserId(token) ?: throw BoardException(
                            ErrorCode.NOT_EXIST_USER_ID
                        )
                    )
                if (userInfo == null) {
                    throw BoardException(ErrorCode.NOT_EXIST_USER)
                }
                userInfo.setInitPassword()
                val authorities = mutableListOf(SimpleGrantedAuthority(DEFAULT_ROLE_PREFIX + userInfo.role))
                val authentication = UsernamePasswordAuthenticationToken(userInfo, null, authorities)
                SecurityContextHolder.getContext().authentication = authentication
            } else {
                throw BoardException(ErrorCode.NOT_EXIST_JWT_TOKEN)
            }
            filterChain.doFilter(request, response)
        } catch (e: BoardException) {
            logger.error("BoardException: ${e.message}")
            setErrorResponse(response, ErrorCode.NOT_EXIST_USER)
        } catch (e: AccessDeniedException) {
            logger.error("Access Denied: ${e.message}")
            setErrorResponse(response, ErrorCode.INVALID_JWT_TOKEN)
        } catch (e: Exception) {
            logger.error("Internal Server Error: ${e.message}", e)
            setErrorResponse(response, ErrorCode.INTERNAL_SERVER_ERROR)
        }
    }

    private fun setErrorResponse(response: HttpServletResponse, errorCode: ErrorCode) {
        val errorResponse = ErrorResponse.of(errorCode)
        val objectMapper = ObjectMapper()
        response.contentType = "application/json"
        response.writer.write(objectMapper.writeValueAsString(errorResponse))
    }

    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val TOKEN_PREFIX = "Bearer "
        private const val DEFAULT_ROLE_PREFIX = "ROLE_"
        private val SECURE_URLS = listOf(
            "/api/v1/auth",
            "/swagger",
            "/swagger-ui",
            "/swagger-ui.html",
            "/swagger-ui/",
            "/api-docs",
            "/api-docs/",
            "/v3/api-docs",
            "/v3/api-docs/"
        )
    }
}