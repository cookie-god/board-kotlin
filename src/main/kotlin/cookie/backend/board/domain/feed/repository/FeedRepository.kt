package cookie.backend.board.domain.feed.repository

import cookie.backend.board.entity.Feed
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FeedRepository : JpaRepository<Feed, Long>, CustomFeedRepository {
}