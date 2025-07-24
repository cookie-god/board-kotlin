package cookie.backend.board.common.util

import cookie.backend.board.entity.UserInfo
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder

class SecurityUtil private constructor () {
    companion object {
        fun getUser(): UserInfo? {
            val authentication = SecurityContextHolder.getContext().authentication
            return if (authentication == null || authentication is AnonymousAuthenticationToken) {
                null
            } else {
                authentication.principal as UserInfo
            }
        }
    }
}