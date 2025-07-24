package cookie.backend.board.entity

import cookie.backend.board.entity.base.BaseEntity
import cookie.backend.board.enums.Role
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.security.crypto.password.PasswordEncoder

@Entity
@Table(name = "USER_INFO")
class UserInfo (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    @Column(name="email")
    var email: String,

    @Column(name="password")
    var password: String?,

    @Column(name="nickname")
    var nickname: String,

    @Column(name="role")
    @Enumerated(value = EnumType.STRING)
    var role: Role,
) : BaseEntity() {

    companion object {
        fun of(email: String, password: String, nickname: String): UserInfo = UserInfo(email = email, password = password, nickname = nickname, role = Role.USER)
    }

    fun setInitPassword() {
        password = null
    }

    fun hashPassword(passwordEncoder: PasswordEncoder) {
        password = passwordEncoder.encode(password)
    }

    fun changePassword(passwordEncoder: PasswordEncoder, newPassword: String) {
        password = passwordEncoder.encode(newPassword)
    }

    fun checkPassword(passwordEncoder: PasswordEncoder, plainPassword: String): Boolean {
        return passwordEncoder.matches(plainPassword, password)
    }
}