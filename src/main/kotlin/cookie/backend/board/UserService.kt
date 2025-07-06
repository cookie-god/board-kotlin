package cookie.backend.board

import org.springframework.stereotype.Service

@Service
class UserService {
    fun retrieveUsers(): List<String> {
        println("사용자 목록을 조회합니다.")
        return listOf("user1", "user2", "user3")
    }
}
