package cookie.backend.board.domain.feed.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

@Repository
class FeedRepositoryImpl (
    private val jpaQuertyFactory: JPAQueryFactory
): CustomFeedRepository {
    private val logger: Logger = LoggerFactory.getLogger(FeedRepositoryImpl::class.java)
}