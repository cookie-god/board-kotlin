package cookie.backend.board.entity

import cookie.backend.board.entity.base.BaseEntity
import cookie.backend.board.enums.Status
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "FEED")
class Feed (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    @Column(name="content", columnDefinition = "TEXT")
    var content: String,

    @Column(name="view_cnt")
    var viewCnt: Long = 0L,

    @Column(name="comment_cnt")
    var commentCnt: Long = 0L,

    @Column(name="bookmark_cnt")
    var bookmarkCnt: Long = 0L,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var userInfo: UserInfo,

) : BaseEntity() {

    companion object {
        fun of(content: String, userInfo: UserInfo): Feed {
            val now: LocalDateTime = LocalDateTime.now()
            return Feed(
                content = content,
                userInfo = userInfo,
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

    fun increaseViewCnt() {
        viewCnt += 1
    }

    fun increaseCommentCnt() {
        commentCnt += 1
    }

    fun decreaseCommentCnt() {
        if (commentCnt > 0) {
            commentCnt -= 1
        }
    }

    fun increaseBookmarkCnt() {
        bookmarkCnt += 1
    }

    fun decreaseBookmarkCnt() {
        if (bookmarkCnt > 0) {
            bookmarkCnt -= 1
        }
    }
}