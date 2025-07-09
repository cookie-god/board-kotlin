package cookie.backend.board.domain.user

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/api/v1/users")
class UserController (
    private val userService: UserService
) {
    @GetMapping("")
    fun getUsers(): List<String> {
        return userService.retrieveUsers()
    }
}
