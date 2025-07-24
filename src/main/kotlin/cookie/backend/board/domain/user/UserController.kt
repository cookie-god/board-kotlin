package cookie.backend.board.domain.user

import cookie.backend.board.common.code.ErrorCode
import cookie.backend.board.common.util.SecurityUtil
import cookie.backend.board.config.exception.BoardException
import cookie.backend.board.domain.user.dto.request.PatchUserPasswordRequest
import cookie.backend.board.entity.UserInfo
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/api/v1/users")
class UserController (
    private val userService: UserService
) {
    @PatchMapping(value = ["password"], produces = ["application/json"])
    fun patchPassword(@Valid @RequestBody request: PatchUserPasswordRequest): UserInfo {
        println("patchPassword")
        val userInfo: UserInfo = SecurityUtil.getUser() ?: throw BoardException(ErrorCode.NOT_EXIST_USER)
        return userInfo
    }
}
