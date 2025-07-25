package cookie.backend.board.entity.base

import cookie.backend.board.enums.Status
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseEntity
{
    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status: Status = Status.ACTIVE

    protected fun changeUpdatedAt() {
        updatedAt = LocalDateTime.now()
    }

    protected fun changeStatus(status: Status) {
        this.status = status
    }
}