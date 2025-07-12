package cookie.backend.board.config.filter

import com.fasterxml.jackson.databind.ObjectMapper
import cookie.backend.board.common.code.ErrorCode
import cookie.backend.board.common.response.ErrorResponse
import cookie.backend.board.config.exception.BoardException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter: OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            println("filter")
            filterChain.doFilter(request, response)
        } catch (e: BoardException) {

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
        private const val BEARER_PREFIX = "Bearer "
        private const val DEFAULT_ROLE_PREFIX = "ROLE_"
    }
}