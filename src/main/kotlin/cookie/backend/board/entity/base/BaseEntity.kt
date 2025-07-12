package cookie.backend.board.entity.base

import cookie.backend.board.enum.Status
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.time.LocalDateTime

abstract class BaseEntity
{
    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status: Status = Status.Active

    protected fun changeUpdatedAt() {
        updatedAt = LocalDateTime.now()
    }

    protected fun changeStatus(status: Status) {
        this.status = status
    }
}