package cookie.backend.board.entity

import cookie.backend.board.entity.base.BaseEntity
import cookie.backend.board.enum.Status
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "COMMENT")
class Comment (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    @Column(name="content", length = 300)
    var content: String,

    @ManyToOne
    @JoinColumn(name = "parent_comment_id", nullable = true)
    var parentComment: Comment? = null,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var userInfo: UserInfo,

    @ManyToOne
    @JoinColumn(name = "feed_id")
    var feed: Feed,
) : BaseEntity(){

    companion object {
        fun of(feed: Feed, userInfo: UserInfo, parentComment: Comment?, content: String): Comment {
            return Comment(
                feed = feed,
                userInfo = userInfo,
                parentComment = parentComment,
                content = content
            )
        }
    }

    fun edit(content: String) {
        this.content = content
        this.changeUpdatedAt()
    }

    fun delete() {
        this.changeStatus(Status.INACTIVE)
        this.changeUpdatedAt()
    }
}