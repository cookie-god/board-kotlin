package cookie.backend.board.domain.auth

interface CustomAuthRepository {
    fun existByEmail(email: String): Boolean
    fun existByNickname(nickname: String): Boolean
}