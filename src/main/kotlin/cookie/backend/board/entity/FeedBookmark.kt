package cookie.backend.board.entity

import cookie.backend.board.entity.base.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "FEED_BOOKMARK")
class FeedBookmark (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var userInfo: UserInfo,

    @ManyToOne
    @JoinColumn(name = "feed_id")
    var feed: Feed,
) : BaseEntity(){

    companion object {
        fun of(feed: Feed, userInfo: UserInfo): FeedBookmark {
            return FeedBookmark(
                feed = feed,
                userInfo = userInfo,
            )
        }
    }
}